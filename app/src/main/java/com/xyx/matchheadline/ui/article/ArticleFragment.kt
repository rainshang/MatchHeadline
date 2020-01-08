package com.xyx.matchheadline.ui.article


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        webView = WebView(activity).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {}
        }
        return webView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webView.loadUrl(args.articleUrl)
    }

}
