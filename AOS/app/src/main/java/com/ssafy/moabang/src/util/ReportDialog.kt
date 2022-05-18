package com.ssafy.moabang.src.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.ssafy.moabang.databinding.ReportBinding

class ReportDialog(var context: Context, var id: Int, var from: String, var content: String?){

    private val binding = ReportBinding.inflate(LayoutInflater.from(context))
    private val dialog = Dialog(context)

    fun show(){
        initView()
        dialog.apply {
            show()
        }
    }

    private fun initView(){
        dialog.apply {
            setContentView(binding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
        binding.apply {
            reportTarget.text = "${from} : ${content}"
            reportReason.setText("말을 꼴받게 써놨음")
            reportCancel.setOnClickListener {
                dialog.dismiss()
            }
            reportSubmit.setOnClickListener {
                sendReport()
                dialog.dismiss()
            }
        }
    }

    private fun sendReport(){
        Log.d("AAAAA", "ReportDialog sendReport: ${context}, ${id}, ${from}, ${content}")
    }
}