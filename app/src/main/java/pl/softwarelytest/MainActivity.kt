package pl.softwarelytest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import io.reactivex.Observable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.softwarelytest.dto.CatModel



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)
    }
}

