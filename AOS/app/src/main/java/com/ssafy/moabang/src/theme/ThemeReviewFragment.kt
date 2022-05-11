package com.ssafy.moabang.src.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.moabang.R
import com.ssafy.moabang.adapter.ReviewListRVAdapter
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.response.ReviewResponse
import com.ssafy.moabang.data.model.response.ReviewStatResponse
import com.ssafy.moabang.data.model.viewmodel.ReviewViewModel
import com.ssafy.moabang.data.model.viewmodel.ThemeViewModel
import com.ssafy.moabang.databinding.FragmentThemeReviewBinding

class ThemeReviewFragment : Fragment() {
    private lateinit var binding: FragmentThemeReviewBinding
    private lateinit var callback: OnBackPressedCallback
    private lateinit var themeDetailActivity: ThemeDetailActivity
    private lateinit var reviewListRVAdapter: ReviewListRVAdapter
    private lateinit var theme: Theme
    private lateinit var list: List<ReviewResponse>
    private val reviewViewModel: ReviewViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

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

        binding.btnThemeRVFSort.setOnClickListener {
            showPopup(binding.btnThemeRVFSort)
        }

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
        themeViewModel.themeStat(theme.tid)
        themeViewModel.themeStatLiveDate.observe(requireActivity()){
            Log.d("THEME STAT TEST", "theme stat initInfo: $it")

            if(it.r_activity != null) {
                binding.ratingBarThemeRVF.rating = it.r_rating / 2
                binding.tvThemeRVFRating.text = String.format("%.1f", it.r_rating)
                binding.tvThemeRVFDiff.text = it.r_chaegamDif.toString()
                binding.tvThemeRVFActive.text = it.r_activity
                binding.tvThemeRVFPlayer.text = it.r_recPlayer.toString() + "명"
                binding.tvThemeRVFSuccess.text = (it.r_isSuccess * 100).toString() + "%"
                binding.tvThemeRVFTime.text = it.r_clearTime.toString() + "분"
                binding.tvThemeRVFHint.text = String.format("%.1f", it.r_hint) + "개"
            }
        }


    }

   private fun initRVA() {

        reviewListRVAdapter = ReviewListRVAdapter()
        binding.rvThemeRVF.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = reviewListRVAdapter
        }

       reviewViewModel.reviewListLiveData.observe(requireActivity()){
           reviewListRVAdapter.data = it as MutableList<ReviewResponse>
           list = it
           reviewListRVAdapter.notifyDataSetChanged()
       }
    }

    private fun showPopup(v: View){
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.review_sort_menu, popup.menu)
        popup.setOnMenuItemClickListener { it ->
            when(it.itemId){
                R.id.sort_by_rate -> {
                    list = if(list != list.sortedByDescending { it.rating }) list.sortedByDescending { it.rating }
                    else list.sortedBy { it.rating }
                    reviewListRVAdapter.sortList(list)
                    true
                }
                R.id.sort_by_playDate -> {
                    list = if(list != list.sortedBy { it.playDate }) list.sortedBy { it.playDate }
                    else list.sortedByDescending { it.playDate }
                    reviewListRVAdapter.sortList(list)
                    true
                }
                R.id.sort_by_regDate -> {
                    list = if(list != list.sortedBy { it.regDate }) list.sortedBy { it.regDate }
                    else list.sortedByDescending { it.regDate }
                    reviewListRVAdapter.sortList(list)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

}