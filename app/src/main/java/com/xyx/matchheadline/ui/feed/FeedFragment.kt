package com.xyx.matchheadline.ui.feed


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xyx.matchheadline.BR
import com.xyx.matchheadline.R
import com.xyx.matchheadline.databinding.FragmentFeedBinding
import com.xyx.matchheadline.ui.FeedsViewModel

/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val feedsViewModel = ViewModelProviders.of(activity!!).get(FeedsViewModel::class.java)
        DataBindingUtil.bind<FragmentFeedBinding>(view)?.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.feedsViewModel, feedsViewModel)
        }
    }

}
