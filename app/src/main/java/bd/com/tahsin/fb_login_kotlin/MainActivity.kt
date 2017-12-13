package bd.com.tahsin.fb_login_kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.GraphRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.facebook.internal.ImageRequest
import com.facebook.AccessToken
import com.facebook.GraphResponse
import org.json.JSONObject




class MainActivity : AppCompatActivity() {

    var callbackManager : CallbackManager  ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*try {
            val info = packageManager.getPackageInfo("bd.com.tahsin.fb_login_kotlin", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }*/

       buttonLogin.setOnClickListener {

           //progreesBar.visibility = View.VISIBLE
           callbackManager = CallbackManager.Factory.create()
           LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
           LoginManager.getInstance().registerCallback(callbackManager,
                   object : FacebookCallback<LoginResult> {
                       override fun onSuccess(loginResult: LoginResult) {
                           // App code
                           progreesBar.visibility = View.GONE
                           Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                           getFacebookData(loginResult)

                       }

                       override fun onCancel() {
                           // App code
                           progreesBar.visibility = View.GONE
                           Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT ).show()
                       }

                       override fun onError(exception: FacebookException) {
                           // App code
                           progreesBar.visibility = View.GONE
                           Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT ).show()
                       }
                   })

       }

    }


    fun getFacebookData(result: LoginResult ){

        val request = GraphRequest.newMeRequest(
                result.accessToken
        ) { `object`, response ->
            // Application code
            val mainRespObj : JSONObject = response.jsonObject
            val name = mainRespObj.getString("name")
            val firstName = mainRespObj.getString("first_name")
            val lastName = mainRespObj.getString("last_name")
            val gender = mainRespObj.getString("gender")
            val proPicUrl = mainRespObj.getJSONObject("picture").getString("url")

            val intent = Intent(applicationContext, LoggedInActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("first_name", firstName)
            intent.putExtra("last_name", lastName)
            intent.putExtra("gender", gender)
            intent.putExtra("imageUrl", proPicUrl)
            startActivity(intent)
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name, first_name, last_name, gender, link, picture")
        request.parameters = parameters
        request.executeAsync()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

}
