package pl.softwarelytest

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Maybe
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.softwarelytest.databinding.ActivityMainBinding
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    // TODO IMPORTANT! READ "Readme" FILE IN THE ASSETS FOLDER!

    var catDisposable: Disposable? = null

    data class CatBasicDataDto(
        val id: Int,
        val name: String,
        val imageUrl: String
    )

    data class CatDescriptionDto(
        val id: Int,
        val description: String
    )

    data class CatAgeModelDto(
        val id: Int,
        val age: String
    )

    // TODO use this class as a target, merged model
    data class CatModel(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val description: String,
        val age: String
    )

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(viewBinding.root)

        loadCatList()
    }

    /**
     * Loads some cat list in a very bad way :(
     */
    private fun loadCatList() {
        catDisposable = getCats(this)
            .subscribeOn(Schedulers.io())
            .subscribe { catList ->
                val catDescriptions = getCatsDescription(this).blockingGet()
                val catAges = getCatsAges(this).blockingGet()

                Log.d("cats", "cats: $catList")
                Log.d("cats", "descriptions: $catDescriptions")
                Log.d("cats", "ages: $catAges")

                // TODO this should be refactored into proper RecyclerView
                with(viewBinding) {
                    // TODO this data should be merged automatically with rxJava
                    itemText1.text =
                        "name: ${catList[0].name} age: ${catAges[0].age} description: ${catDescriptions[0].description}"
                    itemText2.text =
                        "name: ${catList[1].name} age: ${catAges[1].age} description: ${catDescriptions[1].description}"
                    itemText3.text =
                        "name: ${catList[2].name} age: ${catAges[2].age} description: ${catDescriptions[2].description}"
                    itemImage1.setImageURI(Uri.parse(catList[0].imageUrl))
                    itemImage2.setImageURI(Uri.parse(catList[1].imageUrl))
                    itemImage3.setImageURI(Uri.parse(catList[2].imageUrl))
                }
            }
    }

    private fun getCats(context: Context): Maybe<List<CatBasicDataDto>> {
        val type: Type = object : TypeToken<List<CatBasicDataDto>>() {}.type
        return Maybe.just(Gson().fromJson(getAssetJsonData(context, "cats.json"), type))
    }

    private fun getCatsDescription(context: Context): Maybe<List<CatDescriptionDto>> {
        val type: Type = object : TypeToken<List<CatDescriptionDto>>() {}.type
        return Maybe.just(Gson().fromJson(getAssetJsonData(context, "catsDescription.json"), type))
    }

    private fun getCatsAges(context: Context): Maybe<List<CatAgeModelDto>> {
        val type: Type = object : TypeToken<List<CatAgeModelDto>>() {}.type
        return Maybe.just(Gson().fromJson(getAssetJsonData(context, "catsAge.json"), type))
    }

    private fun getAssetJsonData(context: Context, filename: String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open(filename)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    override fun onDestroy() {
        super.onDestroy()
        catDisposable?.dispose()
        catDisposable = null
    }
}

