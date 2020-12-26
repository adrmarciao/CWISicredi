package br.com.cwisicredi.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cwisicredi.R
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val context: Context by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_activity)
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                delay(TimeUnit.SECONDS.toMillis(3))
            }
            startActivity(Intent(context, HomeActivity::class.java))
            finish()
        }
    }
}