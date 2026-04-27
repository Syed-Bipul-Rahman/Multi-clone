package com.dong.multirun

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<android.widget.TextView>(R.id.tvPremium).setOnClickListener {
            toast("Premium Subscription")
        }

        findViewById<android.widget.TextView>(R.id.tvDarkMode).setOnClickListener {
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        findViewById<android.widget.TextView>(R.id.tvScanQR).setOnClickListener {
            toast("QR Scanner coming soon")
        }

        findViewById<android.widget.TextView>(R.id.tvGesturePwd).setOnClickListener {
            toast("Gesture Password coming soon")
        }

        findViewById<android.widget.TextView>(R.id.tvUpdate).setOnClickListener {
            toast("Checking for updates…")
        }

        findViewById<android.widget.TextView>(R.id.tvMoreApps).setOnClickListener {
            toast("More Apps")
        }

        findViewById<android.widget.TextView>(R.id.tvContactUs).setOnClickListener {
            toast("Contact Us")
        }

        findViewById<android.widget.TextView>(R.id.tvPrivacyPolicy).setOnClickListener {
            toast("Privacy Policy")
        }

        findViewById<android.widget.TextView>(R.id.tvTermsOfService).setOnClickListener {
            toast("Terms of Service")
        }

        // Set version string
        val versionName = packageManager
            .getPackageInfo(packageName, 0)
            .versionName
        findViewById<android.widget.TextView>(R.id.tvVersion).text =
            getString(R.string.version_prefix) + versionName
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
