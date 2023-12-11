package com.dakheera47.linklander

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val FIREFOX_PACKAGE_NAME = "org.mozilla.firefox"

    fun findFirefoxPackageName(): String? {
        val packageManager = packageManager
        val packages = packageManager.getInstalledPackages(0)

        for (packageInfo in packages) {
            Log.d("Package names", packageInfo.packageName)
            if (packageInfo.packageName.contains("firefox", ignoreCase = true)) {
                return packageInfo.packageName // Return the first found package name containing "firefox"
            }
        }

        return null // No matching package found
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linkTextView = findViewById<TextView>(R.id.link_display)
        val _9xbudBtn = findViewById<Button>(R.id._9xbud)
        val _9xplayerBtn = findViewById<Button>(R.id._9xplayer)
        var sharedText = ""

        // Check if the intent is a share intent
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
            linkTextView.text = sharedText.ifEmpty { "No Link Selected" }
        }

        _9xbudBtn.setOnClickListener {
            // Handle click for 9xbud.com
            openUrlInSpecificBrowser("https://www.9xbud.com/${sharedText}")
        }

        _9xplayerBtn.setOnClickListener {
            // Handle click for 9xplayer.com
            openUrlInSpecificBrowser("https://www.9xplayer.com/${sharedText}")
        }


//        // Check if the intent is a share intent
//        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
//            // Extract the shared text (URL)
//            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
//
//            if (sharedText != null) {
//                // Append your data to the URL
//                val modifiedUrl = "https://9xbud.com/$sharedText"
//                // Open the URL in a specific browser
//                openUrlInSpecificBrowser(modifiedUrl)
//            }
//        }
    }

    private fun openUrlInSpecificBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (findFirefoxPackageName() != null) {
            intent.setPackage(FIREFOX_PACKAGE_NAME) // Replace with the specific browser's package name

            Log.d("found firefox", "firefox found!")
            Toast.makeText(this, "Firefox found on device", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("found firefox", "firefox not found :(")
            Toast.makeText(this, "Firefox NOT found on device", Toast.LENGTH_SHORT).show()
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Browser not found", Toast.LENGTH_SHORT).show()
        }
    }
}
