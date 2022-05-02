package com.ssafy.moabang.src.main.cafe

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.CafeListDialogFragmentBinding

class CafeListDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: CafeListDialogFragmentBinding
    private val vm : CafeListViewModel by viewModels(ownerProducer = {requireParentFragment()})

    companion object {
        fun newInstance() = CafeListDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CafeListDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spIsland.apply {
            adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.cafe_list_island,
                android.R.layout.simple_spinner_dropdown_item
            )
            onItemSelectedListener = this@CafeListDialog
        }

        binding.spSi.apply {
            adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.cafe_list_si_seoul,
                android.R.layout.simple_spinner_dropdown_item
            )
            onItemSelectedListener = this@CafeListDialog
        }

        binding.cancel.setOnClickListener {
            dismiss()
            vm.island = ""
            vm.si = ""
            if(parentFragment is DialogInterface.OnDismissListener){
                (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
            }
        }

        binding.ok.setOnClickListener {
            dismiss()
            if(parentFragment is DialogInterface.OnDismissListener){
                (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
            }
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p0 == binding.spIsland){
            when(p0.getItemAtPosition(p2)){ // island에 맞게, si spinner의 내용물 설정
                "서울" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_seoul,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
                "경기/인천" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_gyeonggi_incheon,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
                "충청" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_chungcheong,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
                "강원" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_gangwon,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
                "경상" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_gyeongsang,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
                "전라" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_jeolla,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
                "제주" -> {
                    binding.spSi.adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.cafe_list_si_jeju,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
            }
            vm.island = p0.getItemAtPosition(p2).toString()
        }
        else if(p0 == binding.spSi){
            vm.si = p0.getItemAtPosition(p2).toString()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.d("AAAAA", "onNothingSelected: ")
    }

}