package com.football.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri

object Navigator {
    fun openNews(context: Context, newsLink: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(newsLink))
        )
    }
}
