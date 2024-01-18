package com.dakheera47.linklander

import android.net.Uri

class UrlHandler {
    companion object {
        // Function to validate a URL (basic example)
        private fun isValidUrl(url: String): Boolean {
            return try {
                val uri = Uri.parse(url)
                uri.scheme != null && (uri.scheme == "http" || uri.scheme == "https")
            } catch (e: Exception) {
                false
            }
        }

        // Function to append parameters to a URL
        fun appendParameterToUrl(url: String, param: String): String {
            return if (isValidUrl(url)) {
                Uri.parse(url).buildUpon()
                    .appendQueryParameter("param", param)
                    .build()
                    .toString()
            } else {
                url
            }
        }
    }
}
