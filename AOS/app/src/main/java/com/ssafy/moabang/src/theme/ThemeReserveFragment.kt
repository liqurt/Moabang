package com.ssafy.moabang.src.theme

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.ReservationTime
import com.ssafy.moabang.databinding.FragmentThemeReserveBinding
import java.text.SimpleDateFormat
import java.util.*

class ThemeReserveFragment : Fragment() {
    private lateinit var binding: FragmentThemeReserveBinding
    private lateinit var callback: OnBackPressedCallback
    private lateinit var themeDetailActivity: ThemeDetailActivity
    private lateinit var date: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeReserveBinding.inflate(layoutInflater, container, false)
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
        initChip()
        initDate()

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (themeDetailActivity.behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    themeDetailActivity.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else if (themeDetailActivity.behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    val fragmentManager = activity?.supportFragmentManager
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().remove(this@ThemeReserveFragment)
                            .commit()
                        fragmentManager.popBackStack()
                    }
                    themeDetailActivity.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initDate(){
        date = getToday("yyyy-MM-dd").split("-")
        binding.tvThemeRSFDateSelected.text = getToday("yyyy-MM-dd")

        binding.llThemeRSF.setOnClickListener {
            val dialog = DatePickerDialog(requireContext(), R.style.MySpinnerDatePickerStyle, datePickerListener, date[0].toInt(), date[1].toInt()-1, date[2].toInt())
            dialog.show()
        }
    }

    private val datePickerListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = "$year-${monthOfYear + 1}-$dayOfMonth"
            binding.tvThemeRSFDateSelected.text = newDate
        }

    private fun getToday(pattern: String): String {
        val dt = Date(System.currentTimeMillis());
        val sdf = SimpleDateFormat(pattern);
        return sdf.format(dt);
    }

    private fun initChip(){
        var data = listOf(
            ReservationTime(1, "2022-05-02", "10:30", true),
            ReservationTime(1, "2022-05-02", "12:00", false),
            ReservationTime(1, "2022-05-02", "13:30", true),
            ReservationTime(1, "2022-05-02", "15:00", true),
            ReservationTime(1, "2022-05-02", "16:30", true),
            ReservationTime(1, "2022-05-02", "18:00", false),
            ReservationTime(1, "2022-05-02", "19:30", true),
            ReservationTime(1, "2022-05-02", "21:00", false),
        )

        var cnt = 0
        for(item in data){
            binding.chipGroupThemeRSF.addView(Chip(requireContext()).apply {
                text = item.time
                isCloseIconVisible = false
                isCheckable = true
                isCheckedIconVisible = true
                setCheckedIconTintResource(R.color.white)
                if(!item.isAvailable) {
                    isEnabled = false
                    setChipBackgroundColorResource(R.color.moabang_gray)
                } else {
                    setChipBackgroundColorResource(R.color.moabang_pink)
                    cnt++
                }
                setTextAppearanceResource(R.style.ChipTextStyle)
                chipMinHeight = 100F

            })
        }

        binding.tvThemeRSFReserveAvailable.text = cnt.toString() + "/" + data.size.toString()

    }


}