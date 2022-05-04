package com.ssafy.moabang.src.main.cafe

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.moabang.R
import com.ssafy.moabang.adapter.CafeListRVAdapter
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentCafeListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class CafeListFragment : Fragment(), DialogInterface.OnDismissListener,
    PopupMenu.OnMenuItemClickListener {
    private lateinit var repository: Repository
    private lateinit var binding: FragmentCafeListBinding
    private val vm: CafeListViewModel by viewModels()
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

    private fun init() {
        // 핸드폰 키보드의 Search 버튼을 누르거나, 그냥 컴퓨터로 엔터쳤을때
        binding.etCafeF.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                    return true
                }
                return false
            }
        })

        // 필터
        binding.btCafeFFilter.setOnClickListener { filter() }

        // 정렬
        binding.btCafeFSort.setOnClickListener { showPopup(binding.btCafeFSort) } // 팝업메뉴

        // moabang.db 에서 cafe table을 전부 cafeList에 담는다.
        CoroutineScope(Dispatchers.Main).launch {
            cafeList = repository.getAllCafe()
            binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
            binding.rvCafeF.layoutManager = GridLayoutManager(context, 2)
        }
    }


    private fun search() {
        val queryString = binding.etCafeF.text.toString()
        CoroutineScope(Dispatchers.Main).launch {
            cafeList = repository.getCafeByName(queryString)
            binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
        }
    }

    private fun filter() {
        CafeListDialog().show(childFragmentManager, "")
    }


    // 팝업메뉴
    private fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.cafe_sort_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name_ascending -> {
                cafeList = cafeList.sortedBy { it.cname }
                binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
                true
            }
            R.id.sort_by_name_descending -> {
                cafeList = cafeList.sortedByDescending { it.cname }
                binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
                true
            }
            else -> false
        }
    }

    override fun onDismiss(p0: DialogInterface?) {
        if (vm.si == "전체") {
            CoroutineScope(Dispatchers.Main).launch {
                val job = CoroutineScope(Dispatchers.IO).async {
                    cafeList = repository.getCafeByIsland(vm.island)
                }
                job.await()
                binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
            }
        } else if (vm.island.isNotEmpty() && vm.si.isNotEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                val job = CoroutineScope(Dispatchers.IO).async {
                    cafeList = repository.getCafeByIslandSi(vm.island, vm.si)
                }
                job.await()
                binding.rvCafeF.adapter = CafeListRVAdapter(cafeList)
            }
        } else {
            Log.d("AAAAA", "User가 dialog에서 취소버튼을 눌렀음.")
        }
    }


}