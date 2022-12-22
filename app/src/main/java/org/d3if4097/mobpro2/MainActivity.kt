package org.d3if4097.mobpro2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.d3if4097.mobpro2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val contract = FirebaseAuthUIActivityResultContract()
    private val signInLauncher = registerForActivityResult(contract) {  }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { mulaiLogin() }
        binding.logout.setOnClickListener { AuthUI.getInstance().signOut(this) }

        viewModel.authState.observe(this, { updateUI(it) })
    }

    private fun updateUI(user: FirebaseUser?) = with(binding) {
        if (user == null) {
            userGroup.visibility = View.GONE
            login.visibility = View.VISIBLE
        }
        else {
            namaTextView.text = user.displayName
            Glide.with(this@MainActivity).load(user.photoUrl).into(imageView)
            userGroup.visibility = View.VISIBLE
            login.visibility = View.GONE
        }
    }

    private fun mulaiLogin() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(intent)
    }
}