package com.ssafy.moabang.src.main.cafe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.moabang.adapter.CafeListRVAdapter
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentCafeListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CafeListFragment : Fragment() {
    private lateinit var repository: Repository
    private lateinit var binding: FragmentCafeListBinding

    private lateinit var cafeListAdapter: CafeListRVAdapter
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

}