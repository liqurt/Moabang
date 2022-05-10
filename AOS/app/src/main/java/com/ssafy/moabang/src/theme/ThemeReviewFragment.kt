package com.ssafy.moabang.src.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.moabang.adapter.ReviewListRVAdapter
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.viewmodel.ReviewViewModel
import com.ssafy.moabang.databinding.FragmentThemeReviewBinding
import okhttp3.internal.notify

class ThemeReviewFragment : Fragment() {
    private lateinit var binding: FragmentThemeReviewBinding
    private lateinit var callback: OnBackPressedCallback
    private lateinit var themeDetailActivity: ThemeDetailActivity
    private lateinit var reviewListRVAdapter: ReviewListRVAdapter
    private lateinit var theme: Theme
    private val reviewViewModel: ReviewViewModel by viewModels()

    override fun onResume() {
        reviewViewModel.getReview(theme.tid)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        theme = arguments?.getParcelable<Theme>("theme")!!
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
        initInfo()
        initRVA()

        binding.btnThemeRVFReview.setOnClickListener {
            startActivity(Intent(requireContext(), ReviewActivity::class.java)
                .putExtra("type", "등록")
                .putExtra("tid", theme.tid))
        }

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

    private fun initInfo() {
        binding.ratingBarThemeRVF.rating = theme.grade.toFloat()/2
        binding.tvThemeRVFRating.text = theme.grade.toString()

        // TODO: 통계 정보로 세팅
    }

   private fun initRVA() {

        reviewListRVAdapter = ReviewListRVAdapter()
        binding.rvThemeRVF.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = reviewListRVAdapter
        }

       reviewViewModel.reviewListLiveData.observe(requireActivity()){
           reviewListRVAdapter.data = it
           reviewListRVAdapter.notifyDataSetChanged()
       }
    }

}