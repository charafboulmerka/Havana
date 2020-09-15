package com.my.havana

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.NetworkOnMainThreadException
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBtnDelete.setOnClickListener {
            if (myTV.text.toString() != ""){
                myTV.setText(myTV.text.toString().removeRange(myTV.text.length-1,myTV.text.length))
            }
        }

        btn_on.setOnClickListener {
            if (!myTV.text.toString().equals(""))
            sendRequest("N")
        }

        btn_off.setOnClickListener {
            if (!myTV.text.toString().equals(""))
            sendRequest("F")
        }
    }


        fun sendRequest(type:String){
            viewsSate(false)
    // Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(this)
    val url = "http://192.168.4.1/$type"+myTV.text.toString()

// Request a string response from the provided URL.
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        object : Response.Listener<String?> {
            override fun onResponse(response: String?) {
                setEffects(type)
                viewsSate(true)
                myTV.setText("")
            }

        },
        Response.ErrorListener {
            setEffects("ERROR")
            Toast.makeText(this@MainActivity,"Please make sure you're connected to the wifi",Toast.LENGTH_LONG).show()
            viewsSate(true)
        })

// Add the request to the RequestQueue.
    queue.add(stringRequest)
}

    fun viewsSate(state:Boolean){
        var view = arrayListOf<Button>(btn_off,btn_on,btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9)
        for (i in view){
            i.isEnabled = state
            if (!state){
                i.alpha = 0.6F
            }
            else{
                i.alpha = 1F
            }
        }
        mBtnDelete.isEnabled = state
    }



    @SuppressLint("SetTextI18n")
    fun btnClick(view: View){
        var mBtn = findViewById<Button>(view.id)
        var oldText = myTV.text.toString()
        if (myTV.text.length<3){
            myTV.text = oldText+mBtn.text.toString()
        }
    }

    fun setEffects(type:String){
        if (type.equals("N")){
            mCircleGreen.visibility = View.VISIBLE
        }
        else if(type.equals("F")){
            mCircleRed.visibility = View.VISIBLE
        }
        else if(type.equals("ERROR")){
            mError.visibility = View.VISIBLE
        }
        Handler().postDelayed({
            mCircleGreen.visibility = View.GONE
            mCircleRed.visibility = View.GONE
            mError.visibility = View.GONE
        },1200)
    }
}