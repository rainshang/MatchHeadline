package com.xyx.matchheadline.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.xyx.matchheadline.BR
import com.xyx.matchheadline.R
import com.xyx.matchheadline.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

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
        val feedsViewModel = ViewModelProviders.of(this).get(FeedsViewModel::class.java)
        DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                setVariable(BR.feedsViewModel, feedsViewModel)
            }

        feedsViewModel.run {
            feedsResp.observe(this@MainActivity, Observer {
                if (it.version == feedsVersion) {
                    Snackbar.make(srl, getString(R.string.tip_latest_data), Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(srl, getString(R.string.tip_latest_data), Snackbar.LENGTH_SHORT)
                        .show()
                    feedsVersion = it.version
                    feedsIndex = 0
                }
            })
            loadFeeds()
        }

        srl.setOnRefreshListener {
            feedsViewModel.loadFeeds()
        }
    }

}
