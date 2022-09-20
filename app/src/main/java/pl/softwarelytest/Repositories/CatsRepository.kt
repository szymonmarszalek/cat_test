package pl.softwarelytest.Repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Maybe
import pl.softwarelytest.dto.CatAgeModelDto
import pl.softwarelytest.dto.CatBasicDataDto
import pl.softwarelytest.dto.CatDescriptionDto
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset

class CatsRepository {
    fun getCats(context: Context): Maybe<List<CatBasicDataDto>> {
        val type: Type = object : TypeToken<List<CatBasicDataDto>>() {}.type
        return Maybe.just(Gson().fromJson(getAssetJsonData(context, "cats.json"), type))
    }

    fun getCatsDescription(context: Context): Maybe<List<CatDescriptionDto>> {
        val type: Type = object : TypeToken<List<CatDescriptionDto>>() {}.type
        return Maybe.just(Gson().fromJson(getAssetJsonData(context, "catsDescription.json"), type))
    }

    fun getCatsAges(context: Context): Maybe<List<CatAgeModelDto>> {
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
}