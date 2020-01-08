package com.xyx.matchheadline.ui.result


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xyx.matchheadline.R
import com.xyx.matchheadline.binding.loadImage
import kotlinx.android.synthetic.main.fragment_result.*

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        result.text =
            getString(if (args.isCorrect) R.string.tip_correct else R.string.tip_wrong, args.score)
        image.loadImage(args.feed.imageUrl)
        headline.text = args.feed.headlines[args.feed.correctAnswerIndex]
        btnMore.setOnClickListener {
            findNavController()
                .navigate(ResultFragmentDirections.actionResultFragmentToArticleFragment(args.feed.storyUrl))
        }
        btnContinue.setOnClickListener { findNavController().navigateUp() }
    }

}
