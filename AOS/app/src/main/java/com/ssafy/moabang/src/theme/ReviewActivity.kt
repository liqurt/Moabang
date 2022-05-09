package com.ssafy.moabang.src.theme

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.ActivityReviewBinding
import android.widget.RatingBar
import java.text.SimpleDateFormat
import java.util.*


class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private var tid:Int = 0
    private lateinit var date: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tid = intent.getIntExtra("tid", 0)
        init()
    }

    private fun init() {
        binding.toolbarReview.ivToolbarIcon.setOnClickListener { this.onBackPressed() }
        binding.toolbarReview.tvToolbarTitle.text = "리뷰 작성"
        initDate()

        var array: Array<String> = arrayOf("네", "아니오")
        setChipGroup(binding.cgReviewSuccess, array)

        array = arrayOf("아주 쉬움", "쉬움", "보통", "어려움", "아주 어려움")
        setChipGroup(binding.cgReviewDiff, array)

        array = arrayOf("적음", "보통", "많음")
        setChipGroup(binding.cgReviewActive, array)

        binding.ratingBarReview.setOnRatingBarChangeListener{_, rating, _ ->
            binding.tvReviewRating.text = (rating*2).toString() + "/10"
        }

    }


    private fun setChipGroup(res: ChipGroup, list: Array<String>){
        for(i in list.indices){
            res.addView(Chip(this).apply {
                text = list[i]
                isCheckable = true
                isCheckedIconVisible = true
                setCheckedIconTintResource(R.color.moabang_pink)
                setChipBackgroundColorResource(R.color.white)
                setTextAppearanceResource(R.style.ChipTextStyle2)
            })
        }
    }

    private fun initDate(){
        date = getToday("yyyy-MM-dd").split("-")
        binding.tvReviewDateSelected.text = getToday("yyyy-MM-dd")

        binding.llReview.setOnClickListener {
            val dialog = DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, datePickerListener, date[0].toInt(), date[1].toInt()-1, date[2].toInt())
            dialog.show()
        }
    }

    private val datePickerListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = "$year-${monthOfYear + 1}-$dayOfMonth"
            binding.tvReviewDateSelected.text = newDate
        }

    private fun getToday(pattern: String): String {
        val dt = Date(System.currentTimeMillis());
        val sdf = SimpleDateFormat(pattern);
        return sdf.format(dt);
    }


    override fun onBackPressed() {
        // TODO: 경고 한번 하고 종료
        super.onBackPressed()
    }
}