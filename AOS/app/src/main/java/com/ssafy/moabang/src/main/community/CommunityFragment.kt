package com.ssafy.moabang.src.main.community

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.adapter.CommunityRVAdapter
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.data.model.repository.CommunityRepository
import com.ssafy.moabang.databinding.FragmentCommunityBinding
import kotlinx.coroutines.*

/**
 * 1. chip 바탕으로 header 선정(default : null)
 * 2. 모든 글을 불러온다.
 * 3. header에 맞는 글만 추려낸다.
 * 4. 뷰에 적용
 * 5. clickListener를 단다.
 */

class CommunityFragment : Fragment() {
    private var currentHeader: String = "전체"
    private lateinit var allCommunityList: List<Community>
    private lateinit var currentCommunityList: List<Community>
    private lateinit var latest3AnnouncementList: List<Community>

    private lateinit var binding: FragmentCommunityBinding

    private var communityRepository = CommunityRepository()

    private var communityRVAdapter = CommunityRVAdapter()
    private var communityRVAdapterLatest3Announcement = CommunityRVAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
        setAllCommunity()
    }

    private fun setButtons() {
        binding.chipAll.setOnClickListener {
            currentHeader = "전체"
            setCurrentCommunity()
            binding.rvCommuF3Announcement.visibility = View.VISIBLE
        }
        binding.chipAnnouncement.setOnClickListener {
            currentHeader = "공지"
            setCurrentCommunity()
            binding.rvCommuF3Announcement.visibility = View.GONE
        }
        binding.chipFreeBoard.setOnClickListener {
            currentHeader = "자유"
            setCurrentCommunity()
            binding.rvCommuF3Announcement.visibility = View.VISIBLE
        }
        binding.chipParty.setOnClickListener {
            currentHeader = "구인"
            setCurrentCommunity()
            binding.rvCommuF3Announcement.visibility = View.VISIBLE
        }
        binding.fabWrite.setOnClickListener {
            val intent = Intent(context, CommunityWriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAllCommunity() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                allCommunityList = getAllCommunityFromServer()
            }.await()
            setLatest3()
            setCurrentCommunity()
        }
    }

    private fun setCurrentCommunity() {
        if (currentHeader == "전체") {
            currentCommunityList = allCommunityList.filterNot {
                it.header == "공지" && latest3AnnouncementList.contains(it)
            }
        } else if (currentHeader == "공지") {
            currentCommunityList = allCommunityList.filter { it.header == "공지" }
        } else {
            currentCommunityList = allCommunityList.filter { it.header == currentHeader }
        }
        initRcv()
    }

    private fun setLatest3() {
        latest3AnnouncementList = allCommunityList.filter { it.header == "공지" }.subList(0, 3)
        init3AnnouncementRCV()
    }

    private fun init3AnnouncementRCV() {
        communityRVAdapterLatest3Announcement.apply {
            data = latest3AnnouncementList
            context = requireContext()
            mode = "latest3Announcement"
            itemClickListener = object : CommunityRVAdapter.ItemClickListener {
                override fun onClick(community: Community) {
                    goToCommunityDetail(community)
                }
            }
        }
        binding.rvCommuF3Announcement.apply {
            adapter = communityRVAdapterLatest3Announcement
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initRcv() {
        communityRVAdapter.apply {
            data = currentCommunityList
            context = requireContext()
            mode = "normal"
            itemClickListener = object : CommunityRVAdapter.ItemClickListener {
                override fun onClick(community: Community) {
                    goToCommunityDetail(community)
                }
            }
        }
        binding.rvCommuF.apply {
            adapter = communityRVAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private suspend fun getAllCommunityFromServer(): List<Community> =
        withContext(Dispatchers.IO) {
            val result = communityRepository.getAllCommunity()
            if (result != null) {
                return@withContext result.body()!!
            } else {
                return@withContext emptyList()
            }
        }

    private fun goToCommunityDetail(community: Community) {
        val intent = Intent(requireContext(), CommunityDetailActivity::class.java)
        intent.putExtra("community", community)
        startActivity(intent)
    }

}