package com.ssafy.moabang.src.theme

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.FragmentThemeReviewBinding

class ThemeReviewFragment : Fragment() {
    private lateinit var binding: FragmentThemeReviewBinding
    private lateinit var callback: OnBackPressedCallback
    private lateinit var themeDetailActivity: ThemeDetailActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        themeDetailActivity = context as ThemeDetailActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (themeDetailActivity.behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    themeDetailActivity.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else if (themeDetailActivity.behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    val fragmentManager = activity?.supportFragmentManager
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().remove(this@ThemeReviewFragment)
                            .commit()
                        fragmentManager.popBackStack()
                    }
                    themeDetailActivity.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

}