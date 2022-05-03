package com.ssafy.moabang.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.data.model.ThemeViewModel
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.FragmentThemeBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity

class ThemeFragment : Fragment() {
    private lateinit var binding: FragmentThemeBinding
    private lateinit var themeListRVAdapter: ThemeListRVAdapter

    val themeViewModel: ThemeViewModel by viewModels()
    private lateinit var themeList: List<Theme>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val sp = requireActivity().getSharedPreferences("moabang", AppCompatActivity.MODE_PRIVATE)
        val token = sp.getString("moabangToken", "")!!

        themeViewModel.getAllTheme(token)
        themeListRVAdapter = ThemeListRVAdapter()

        themeViewModel.themeListLiveData.observe(requireActivity()){
            themeListRVAdapter.data = it
            themeListRVAdapter.notifyDataSetChanged()
        }


        binding.rvThemeF.apply{
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = themeListRVAdapter
        }
//        val imgUrl = "http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC(1).jpg"
//        val imgUrl2 = "http://solver-gd.com/upload_file/room/12344(17).jpg"
//
//        themeListRVAdapter.data = listOf(
//            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "'2022년 대한민국은 현재 일본 식민지 시대'\n" +
//                    "\n" +
//                    "우리는 일본인들의 멸시와 차별 속에서\n" +
//                    "하루하루 힘들게 살아가고 있다.\n" +
//                    "\n" +
//                    "어머니가 돌아가시고 그로부터 약 1년 후 문자 하나가 왔다.\n" +
//                    "\n" +
//                    "\"안녕하세요 저는 남자현이라고 합니다.\n" +
//                    "어머님(안윤복)이 자녀분들에게 남겨주신 목걸이가 필요해요.\n" +
//                    "내일 저녁 10시 홍대꾸에 있는 아시아믹스 술집\n" +
//                    "2번 테이블에서 기다릴께요\"\n" +
//                    "\n" +
//                    "문득 1년 전 그날이 기억났다.\n" +
//                    "\n" +
//                    "다급하게 집에 들어오신 어머니\n" +
//                    "그리고 내 손에 목걸이를 전해주시면서 하셨던 말\n" +
//                    "\n" +
//                    "\"엄마 지인이 목걸이를 찾으면 기억했다가 꼭 전해줘야 한다.\"\n" +
//                    "\n" +
//                    "그리고 다음날 어머님은 뺑소니 사고로 돌아가셨다.\n" +
//                    "\n" +
//                    "어머니의 마지막 유언이 돼버렸던 말...\n" +
//                    "\n" +
//                    "내일 나는 목걸이를 전해줘야 한다!\n" +
//                    "\n" +
//                    "*편한 복장을 권장합니다.(치마 비추천)\n" +
//                    "-추천 인원은 3인 이상입니다.", 4, "2~4", 75, "판타지", 4.6, false, "보통", "복합", "http://www.code-k.co.kr/sub/code_sub03.html?R_JIJEM=S3#"),
//            Theme(2, "솔버 2호점", "디어 마르시", imgUrl2, "밤하늘, 나를 향해 가장 푸르게 빛나는 저 별, 나는 그에게 \"에덴\" 이라는 이름을 붙여주었다.",  4, "2~4", 75, "감성", 4.1, true, "적음","자물쇠", "http://solver-gd.com/m/02.html#"),
//            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명",  4, "2~4", 75, "판타지", 4.6, false, "보통","복합", "http://www.code-k.co.kr/sub/code_sub03.html?R_JIJEM=S3#"),
//            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명",  4, "2~4", 75, "판타지", 4.6, false, "보통","복합", "http://www.code-k.co.kr/sub/code_sub03.html?R_JIJEM=S3#"),
//            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명",  4, "2~4", 75, "판타지", 4.6, false, "보통","복합", "http://www.code-k.co.kr/sub/code_sub03.html?R_JIJEM=S3#"),
//        )

        themeListRVAdapter.itemClickListener = object : ThemeListRVAdapter.ItemClickListener {
            override fun onClick(item: Theme) {
                if(item != null){
                    val intent = Intent(requireActivity(), ThemeDetailActivity::class.java).putExtra("theme", item)
                    startActivity(intent)
                }
            }
        }
    }

    inner class SwitchListener: CompoundButton.OnCheckedChangeListener{
        override fun onCheckedChanged(button: CompoundButton, isChecked: Boolean) {
            if(isChecked){
                // TODO
            } else {
                // TODO
            }
        }

    }


}