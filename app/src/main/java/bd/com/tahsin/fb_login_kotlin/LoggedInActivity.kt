package bd.com.tahsin.fb_login_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_logged_in.*

class LoggedInActivity : AppCompatActivity() {

    private var name : String ?= null
    private var firstName : String ?= null
    private var lastName : String ?= null
    private var gender : String ?= null
    private var url : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val intent = intent
        val extras = intent.extras
        name = extras.getString("name")
        firstName = extras.getString("name")
        gender = extras.getString("name")
        url = extras.getString("name")

        settingUpUI()

    }


    private fun settingUpUI(){

        Picasso.with(applicationContext).load(url).into(imageViewProPic)
        textViewName.text = name
        textViewFirstName.text = firstName
        textViewLastName.text = lastName
        textViewGender.text = gender
    }
}
