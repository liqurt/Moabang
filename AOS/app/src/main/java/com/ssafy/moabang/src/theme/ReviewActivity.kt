package com.ssafy.moabang.src.theme

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityReviewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import com.ssafy.moabang.data.model.dto.ReviewForCreate
import com.ssafy.moabang.data.model.dto.ReviewForUpdate
import com.ssafy.moabang.data.model.response.ReviewResponse
import com.ssafy.moabang.data.model.viewmodel.ReviewViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DATE


class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private val reviewViewModel: ReviewViewModel by viewModels()
    private var tid:Int = 0
    private var review:ReviewResponse? = null
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        type = intent.getStringExtra("type").toString()
        if(type == "등록") {
            tid = intent.getIntExtra("tid", 0)
        } else if(type == "수정"){
            review = intent.getParcelableExtra("review")
        }

        init()
        if(type == "등록") initAdd()
        else if(type == "수정")initRevise()
    }

    private fun init(){
        binding.toolbarReview.ivToolbarIcon.setOnClickListener { this.onBackPressed() }
        binding.tvReviewCancel.setOnClickListener { this.onBackPressed() }

        var array: Array<String> = arrayOf("네", "아니오")
        setChipGroup(binding.cgReviewSuccess, array)

        array = arrayOf("아주 쉬움", "쉬움", "보통", "어려움", "아주 어려움")
        setChipGroup(binding.cgReviewDiff, array)

        array = arrayOf("적음", "보통", "많음")
        setChipGroup(binding.cgReviewActive, array)

        binding.ratingBarReview.setOnRatingBarChangeListener{_, rating, _ ->
            binding.tvReviewRating.text = (rating*2).toString() + "/10"
        }

        binding.tvReviewOk.setOnClickListener {
            registerReview()
        }
    }

    private fun initAdd() {
        binding.toolbarReview.tvToolbarTitle.text = "리뷰 작성"
        initDate(getToday("yyyy.MM.dd"))


    }

    private fun initRevise(){
        binding.tvReviewOk.text = "수정"
        binding.toolbarReview.tvToolbarTitle.text = "리뷰 수정"
        initDate(review!!.playDate)
        binding.etReviewPlayer.setText(review!!.player.toString())

        if(review!!.isSuccess == 1){
            binding.cgReviewSuccess.findViewById<Chip>(binding.cgReviewSuccess[0].id).isChecked = true
        } else if(review!!.isSuccess == 0){
            binding.cgReviewSuccess.findViewById<Chip>(binding.cgReviewSuccess[1].id).isChecked = true
        }

        binding.etReviewTimeLeft.setText(review!!.clearTime.toString())
        binding.etReviewHint.setText(review!!.hint.toString())

        binding.cgReviewDiff.findViewById<Chip>(binding.cgReviewDiff[review!!.chaegamDif-1].id).isChecked = true

        when(review!!.active){
            "적음" -> {
                binding.cgReviewActive.findViewById<Chip>(binding.cgReviewActive[0].id).isChecked = true
            }
            "보통" -> {
                binding.cgReviewActive.findViewById<Chip>(binding.cgReviewActive[1].id).isChecked = true
            }
            "많음" -> {
                binding.cgReviewActive.findViewById<Chip>(binding.cgReviewActive[2].id).isChecked = true
            }
        }

        binding.etReviewRplayer.setText(review!!.recPlayer.toString())
        binding.ratingBarReview.rating = review!!.rating/2
        binding.tvReviewRating.text = review!!.rating.toString() + "/10"
        binding.etReviewContent.setText(review!!.content)


    }

    private fun registerReview(){
        var cnt = 0 // 8이 되어야 함
        var txt = ""

        var active: String = ""
        var clearTime: Int = 0
        var chaegamDif: Int = 0
        var hint: Int = 0
        var isSuccess: Int = 0
        var player: Int = 0
        var rating: Float = 0.0F
        var recPlayer: Int = 0

        var playDate = binding.tvReviewDateSelected.text.toString()

        if(binding.etReviewPlayer.text.toString() != ""){
            player = binding.etReviewPlayer.text.toString().toInt()
            cnt++
        } else {
            Toast.makeText(this, "플레이 인원을 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.cgReviewSuccess.checkedChipIds.size >= 1){
            txt = binding.cgReviewSuccess.findViewById<Chip>(binding.cgReviewSuccess.checkedChipId).text.toString()
            isSuccess = if(txt == "네") 1 else 0
            cnt++
        } else {
            if(cnt == 1) Toast.makeText(this, "탈출 성공여부를 선택해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.etReviewTimeLeft.text.toString() != ""){
            clearTime = binding.etReviewTimeLeft.text.toString().toInt()
            cnt++
        } else {
            if(cnt == 2) Toast.makeText(this, "탈출 소요시간을 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.etReviewHint.text.toString() != ""){
            hint = binding.etReviewHint.text.toString().toInt()
            cnt++
        } else {
            if(cnt == 3) Toast.makeText(this, "사용한 힌트 갯수를 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.cgReviewDiff.checkedChipIds.size >= 1){
            txt = binding.cgReviewDiff.findViewById<Chip>(binding.cgReviewDiff.checkedChipId).text.toString()
            chaegamDif = if(txt == "아주 쉬움") 1
                            else if(txt == "쉬움") 2
                            else if(txt == "보통") 3
                            else if(txt == "어려움") 4
                            else 5
            cnt++
        } else {
            if(cnt == 4) Toast.makeText(this, "체감 난이도를 선택해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.cgReviewActive.checkedChipIds.size >= 1){
            active = binding.cgReviewActive.findViewById<Chip>(binding.cgReviewActive.checkedChipId).text.toString()
            cnt++
        } else {
            if(cnt == 5) Toast.makeText(this, "체감 활동성을 선택해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.etReviewRplayer.text.toString() != ""){
            recPlayer = binding.etReviewRplayer.text.toString().toInt()
            cnt++
        } else {
            if(cnt == 6) Toast.makeText(this, "추천 인원을 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(binding.tvReviewRating.text.toString() != "별점을 입력해주세요"){
            rating = binding.ratingBarReview.rating.toString().toFloat()*2
            cnt++
        } else {
            if(cnt == 7) Toast.makeText(this, "별점을 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(cnt == 8){
            if(type == "등록") {
                var reviewv = ReviewForCreate(
                    active,
                    clearTime,
                    binding.etReviewContent.text.toString(),
                    chaegamDif,
                    hint,
                    isSuccess,
                    playDate,
                    player,
                    rating,
                    recPlayer,
                    tid
                )
                Log.d("REVIEW TEST", "registerReview: $reviewv")
                try {
                    reviewViewModel.reviewAdd(reviewv)
                    Toast.makeText(this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ThemeReviewFragment::class.java)
                    setResult(1, intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, "리뷰 등록 실패 : ${e.printStackTrace()}", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            if(type == "수정"){
                var reviewv = ReviewForUpdate(
                    active,
                    clearTime,
                    binding.etReviewContent.text.toString(),
                    chaegamDif,
                    hint,
                    isSuccess,
                    playDate,
                    player,
                    rating,
                    recPlayer,
                    review!!.rid
                )
            }

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

    private fun initDate(dt: String){
        var date = dt.split(".")
        binding.tvReviewDateSelected.text = dt

        binding.llReview.setOnClickListener {
            val dialog = DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, datePickerListener, date[0].toInt(), date[1].toInt()-1, date[2].toInt())
            dialog.show()
        }
    }

    private val datePickerListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = "$year.${monthOfYear + 1}.$dayOfMonth"
            binding.tvReviewDateSelected.text = reformDate(newDate)
        }

    private fun getToday(pattern: String): String {
        val dt = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat(pattern, Locale.KOREA)
        return sdf.format(dt)
    }

    private fun reformDate(dt: String): String {
        var sdf = SimpleDateFormat("yyyy.MM.dd")
        return sdf.format(sdf.parse(dt)).toString()
    }


    override fun onBackPressed() {
        // TODO: 경고 한번 하고 종료
        super.onBackPressed()
    }
}