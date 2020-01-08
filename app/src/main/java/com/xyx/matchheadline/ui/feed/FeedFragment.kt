package com.xyx.matchheadline.ui.feed


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xyx.matchheadline.BR
import com.xyx.matchheadline.R
import com.xyx.matchheadline.databinding.FragmentFeedBinding
import com.xyx.matchheadline.ui.FeedsViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : Fragment() {

    private lateinit var feedsViewModel: FeedsViewModel

    private val choiceListener = View.OnClickListener {
        val feed = feedsViewModel.feedsResp.value!!.items[feedsViewModel.index.value!!]
        val index = it.tag as Int
        if (index == feed.correctAnswerIndex) {
            feedsViewModel.score.value = (feedsViewModel.score.value ?: 0) + 2
            feedsViewModel.index.value = (feedsViewModel.index.value ?: 0) + 1
        } else {
            feedsViewModel.score.value = (feedsViewModel.score.value ?: 0) - 1
            feedsViewModel.index.value = (feedsViewModel.index.value ?: 0) + 1
        }
        // TODO goto result
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        feedsViewModel = ViewModelProviders.of(activity!!).get(FeedsViewModel::class.java)
        DataBindingUtil.bind<FragmentFeedBinding>(view!!)?.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.feedsViewModel, this@FeedFragment.feedsViewModel)
        }

        feedsViewModel.run {
            index.observe(viewLifecycleOwner, Observer { refreshUI() })
            feedsResp.observe(viewLifecycleOwner, Observer { refreshUI() })
        }

        srl.setOnRefreshListener {
            feedsViewModel.loadFeeds()
        }
    }

    private fun refreshUI() {
        feedsViewModel.feedsResp.value?.run {
            btnContainer.removeAllViews()
            if (feedsViewModel.index.value!! < resultSize) {
                val feed = items[feedsViewModel.index.value!!]
                feed.headlines.forEachIndexed { index, line ->
                    val choiceBtn = Button(context)
                    choiceBtn.text = line
                    choiceBtn.tag = index
                    choiceBtn.isAllCaps = false
                    choiceBtn.setOnClickListener(choiceListener)
                    btnContainer.addView(choiceBtn)
                }
            }
        }
    }

}
