package `in`.innovateria.onlinestore.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.android.material.button.MaterialButton
import `in`.innovateria.onlinestore.Models.UserModel
import `in`.innovateria.onlinestore.R
import `in`.innovateria.onlinestore.Utils.AnimationUtils
import `in`.innovateria.onlinestore.Utils.Constant
import `in`.innovateria.onlinestore.Utils.DBHelper
import kotlinx.coroutines.GlobalScope
import java.security.MessageDigest
import java.util.UUID
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {

    private lateinit var googleIdOption: GetGoogleIdOption

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        // Google SignIn With Credential Manager
        val credentialManager = CredentialManager.create(this)

        splashLogic();

        //Guest Visitors Control
        val startBtn: AppCompatButton = findViewById(R.id.startBtn)
        startBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Google Sign In
        val google_btn: MaterialButton = findViewById(R.id.google_btn)
        google_btn.setOnClickListener {

            val rawNonce = UUID.randomUUID().toString()
            val bytes = rawNonce.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.joinToString("") { byte -> "%02x".format(byte) }

            googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(getString(R.string.google_web_client_id))
                .setAutoSelectEnabled(true)
                .setNonce(hashedNonce)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            lifecycleScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        context = this@WelcomeActivity,
                        request = request,
                    )
                    handleSignIn(result)
                } catch (e: Exception) {
                    Toast.makeText(this@WelcomeActivity, e.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        when (credential) {

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                        Log.d("UserData",googleIdTokenCredential.id)

                        val userMap = mapOf(
                            "name" to googleIdTokenCredential.displayName,
                            "email" to googleIdTokenCredential.id,
                            "photo" to googleIdTokenCredential.profilePictureUri.toString(),
                            "mobile" to googleIdTokenCredential.phoneNumber
                        )
                        val db = DBHelper(this)
                        db.createUserToFirebase(userMap)

                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("TAG", "Received an invalid google id token response", e)
                    }

                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("TAG", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("TAG", "Unexpected type of credential")
            }
        }
    }

    private fun splashLogic() {
        val splashScreenHolder = findViewById<View>(R.id.splash_screen_holder)
        splashScreenHolder.postDelayed(
            {
                val userMap = Constant().getUserMapFromPreferences(this@WelcomeActivity)
                if (userMap != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                AnimationUtils.slideToEndWithFadeOut(splashScreenHolder, this)
                splashScreenHolder.visibility = View.GONE
            },
            3000
        )
    }
}