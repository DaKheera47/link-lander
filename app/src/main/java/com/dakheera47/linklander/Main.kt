package com.dakheera47.linklander

import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val FIREFOX_PACKAGE_NAME = "org.mozilla.firefox"

    private fun findFirefoxPackageName(): String? {
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

    class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            Log.d("debug", "Gets rendered at least")

//            val linkTextView = findViewById<TextView>(R.id.link_display)
//            val linkInput = findViewById<EditText>(R.id.link_input)
//            val clipboardBtn = findViewById<Button>(R.id.get_clipboard)
            val _9xbudBtn = findViewById<Button>(R.id._9xbud)
            val _9xplayerBtn = findViewById<Button>(R.id._9xplayer)

            // Check if the intent is a share intent
//            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
//            linkTextView.text = sharedText.ifEmpty { "No Link Selected" }
//
//            // Set shared text to EditText and hide clipboard button if shared text is valid
//            if (sharedText.isNotEmpty()) {
//                linkInput.setText(sharedText)
//                clipboardBtn.visibility = View.GONE
//            }
//
//            clipboardBtn.setOnClickListener {
//                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
//                val clipData = clipboard.primaryClip
//                if (clipData != null && clipData.itemCount > 0) {
//                    val text = clipData.getItemAt(0).text.toString()
//                    linkInput.setText(text)
//                    linkTextView.text = text
//                }
//            }

            _9xbudBtn.setOnClickListener {
                val url = UrlHandler.appendParameterToUrl("https://www.9xbud.com/", "linkInput.toString()")
                Log.d("urlout", url)
                openUrlInSpecificBrowser(url)
            }

            _9xplayerBtn.setOnClickListener {
                val url = "https://www.9xplayer.com/${"linkInput.text"}"
                openUrlInSpecificBrowser(url)
            }
        }

        private fun openUrlInSpecificBrowser(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
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
