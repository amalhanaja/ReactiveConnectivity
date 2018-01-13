package io.github.amalhanaja.reactiveconnectivity.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.github.amalhanaja.reactiveconnectivity.ReactiveConnectivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val connectionObserver by lazy {
        ReactiveConnectivity(
                this,
                onError = { it.printStackTrace() },
                onChange = {
                    Toast.makeText(this, "MAIN ACTIVITY : ${it.name}", Toast.LENGTH_SHORT).show()
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_test.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
//            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        connectionObserver.subscribe()
    }

    override fun onStop() {
        super.onStop()
        connectionObserver.dispose()
    }
}
