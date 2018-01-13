package io.github.amalhanaja.reactiveconnectivity.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.github.amalhanaja.reactiveconnectivity.ReactiveConnectivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    private val disposable by lazy {
        ReactiveConnectivity.buildObserver(this)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Toast.makeText(this, "MAIN 2 ACTIVITY : ${it.name}", Toast.LENGTH_SHORT).show()
                        },
                        {
                            it.printStackTrace()
                        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button.setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        disposable
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }

}
