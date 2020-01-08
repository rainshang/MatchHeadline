package com.xyx.matchheadline.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xyx.matchheadline.R

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_VERSION = "KEY_VERSION"
        const val KEY_INDEX = "KEY_INDEX"
        const val KEY_SCORE = "KEY_SCORE"
    }

    private var feedsVersion: Int = 0
        get() =
            getSharedPreferences(packageName, Context.MODE_PRIVATE).getInt(KEY_VERSION, 0)
        set(value) {
            field = value
            getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                .putInt(KEY_VERSION, value).apply()
        }

    private var feedsIndex: Int = 0
        get() =
            getSharedPreferences(packageName, Context.MODE_PRIVATE).getInt(KEY_INDEX, 0)
        set(value) {
            field = value
            getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                .putInt(KEY_INDEX, value).apply()
        }

    private var totalScore: Int = 0
        get() =
            getSharedPreferences(packageName, Context.MODE_PRIVATE).getInt(KEY_SCORE, 0)
        set(value) {
            field = value
            getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                .putInt(KEY_SCORE, value).apply()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val feedsViewModel = ViewModelProviders.of(this).get(FeedsViewModel::class.java)

        feedsViewModel.run {
            score.value = totalScore
            index.value = feedsIndex

            feedsResp.observe(this@MainActivity, Observer {
                if (it.version == feedsVersion) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.tip_latest_data),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.tip_data_updated),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    feedsVersion = it.version
                    index.value = 0
                }
            })
            score.observe(this@MainActivity, Observer { totalScore = it })
            index.observe(this@MainActivity, Observer { feedsIndex = it })

            loadFeeds()
        }
    }

}
