package com.ssafy.moabang.src.main.cafe

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.moabang.adapter.CafeListRVAdapter
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentCafeListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CafeListFragment : Fragment() {
    private lateinit var repository: Repository
    private lateinit var binding: FragmentCafeListBinding

    private lateinit var cafeList: List<Cafe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()


    }

    private fun init(){
        // 핸드폰 키보드의 Search 버튼을 누르거나, 그냥 컴퓨터로 엔터쳤을때
        binding.etCafeF.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if(p1 == EditorInfo.IME_ACTION_SEARCH){
                    search()
                    return true
                }
                return false
            }
        })

        // moabang.db 에서 cafe table을 전부 cafeList에 담는다.
        CoroutineScope(Dispatchers.Main).launch {
            cafeList = repository.getAllCafe()
            binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
            binding.rvCafeF.layoutManager = GridLayoutManager(context, 2)
            Log.d("AAAAA","${cafeList.size}")
        }
    }

    /**
     * filter by cname
     */
    private fun search(){
        val queryString = binding.etCafeF.text.toString()
        val job = CoroutineScope(Dispatchers.Main).launch {
            cafeList = repository.getCafeByName(queryString)
            binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
            Log.d("AAAAA","$cafeList")
        }
    }

    /**
     * filter by region(도, 시군구)
     */
    private fun filter(){

    }



}