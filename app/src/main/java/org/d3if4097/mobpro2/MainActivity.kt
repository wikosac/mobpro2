package org.d3if4097.mobpro2

import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.d3if4097.mobpro2.databinding.ActivityMainBinding
import org.d3if4097.mobpro2.notify.AlarmUtils
import org.d3if4097.mobpro2.notify.FcmService
import org.d3if4097.mobpro2.notify.createChannel
import org.d3if4097.mobpro2.notify.sendNotification

class MainActivity : AppCompatActivity() {

    private val contract = FirebaseAuthUIActivityResultContract()
    private val signInLauncher = registerForActivityResult(contract) {  }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val CHECK_IN_URL = "https://checkin.telkomuniversity.ac.id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { mulaiLogin() }
        binding.logout.setOnClickListener { AuthUI.getInstance().signOut(this) }
        binding.checkin.setOnClickListener { checkInSekarang() }

        viewModel.authState.observe(this) { updateUI(it) }
        tanganiPengumuman(intent)

        // Pembuatan channel baru (news)
        createChannel(
            this,
            R.string.news_channel_id,
            R.string.news_channel_name,
            R.string.news_channel_desc
        )
        // Pembuatan channel sebelumnya (reminder).
        createChannel(
            this,
            R.string.notif_channel_id,
            R.string.notif_channel_name,
            R.string.notif_channel_desc
        )
    }

    private fun updateUI(user: FirebaseUser?) = with(binding) {
        if (user == null) {
            userGroup.visibility = View.GONE
            login.visibility = View.VISIBLE
            AlarmUtils.setAlarmOff(this@MainActivity)
        }
        else {
            namaTextView.text = user.displayName
            Glide.with(this@MainActivity).load(user.photoUrl).into(imageView)
            userGroup.visibility = View.VISIBLE
            login.visibility = View.GONE
            AlarmUtils.setAlarm(this@MainActivity)
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

    private fun checkInSekarang() {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(this, Uri.parse(CHECK_IN_URL))
    }

    private fun tanganiPengumuman(intent: Intent) {
        if (!intent.hasExtra(FcmService.KEY_URL)) return
        val url = intent.getStringExtra(FcmService.KEY_URL) ?: return
        val tabsIntent = CustomTabsIntent.Builder().build()
        tabsIntent.launchUrl(this, Uri.parse(url))
    }
}