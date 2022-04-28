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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fakeCafeIntoAppDB() // 일단 가짜 Data를 쓴다.

    }

    private fun fakeCafeIntoAppDB() {
        CoroutineScope(Dispatchers.Main).async {
            repository.clearAll()
            for (i in 0..9) {
                repository.insertCafe(getFakeCafe())
            }
            repository.getAllCafe().let {
                cafeList = it
                cafeListAdapter = CafeListRVAdapter(cafeList)
                binding.rvCafeF.adapter = cafeListAdapter
                binding.rvCafeF.layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    private fun getFakeCafe(): Cafe {
        val _cid: Int = (Math.random() * 1000).toInt()
        val _oid: Int = (Math.random() * 1000).toInt()
        val _sido = "경기도"
        val _gungu = "광주시"
        val _name = when(_cid){
            in 0..100 -> "언리미티드 이스케이프"
            in 100..200 -> "제주방탈출카페 시청점"
            in 200..300 -> "제주방탈출카페 제원점"
            in 300..400 -> "더코드 서귀포점"
            in 400..500 -> "더메이즈 제주점"
            in 500..600 -> "하일이스케이프"
            in 600..700 -> "더리얼 방탈출카페"
            in 700..800 -> "어메이징이스케이프"
            in 800..900 -> "셜록홈즈 여수점"
            in 900..999 -> "이스케이프탑 광주점"
            else -> "띠용"
        }
        val _url = "ssafy.com"
        val _time = "매일 09:00-18:00"
        val _img = when(_cid){
            in 0..100 -> "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=YD1C2o6-tAmP75VbP8Z2Nw&cb_client=search.gws-prod.gps&w=408&h=240&yaw=233.05055&pitch=0&thumbfov=100"
            in 100..200 -> "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211115_57%2F1636947621551PKdfS_JPEG%2F%25BD%25C3%25C3%25BB%25B0%25A3%25C6%25C7.jpg"
            in 200..300 -> "https://scontent-ssn1-1.xx.fbcdn.net/v/t31.18172-1/25531945_734296040093191_8501654756992196039_o.jpg?stp=dst-jpg_p160x160&_nc_cat=105&ccb=1-5&_nc_sid=7206a8&_nc_ohc=YxOkRuCz76oAX_PWlh2&_nc_ht=scontent-ssn1-1.xx&oh=00_AT-q9um46b0MmTAW6U-Npp99tdRpUc89WN8eFu4Oz1oDaA&oe=628DBB56"
            in 300..400 -> "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20170102_274%2F1483325573282108C4_JPEG%2F177156553768017_0.jpeg"
            in 400..500 -> "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=83N4iwx_zBOSg-bOdUci6w&cb_client=search.gws-prod.gps&w=408&h=240&yaw=107.126&pitch=0&thumbfov=100"
            in 500..600 -> "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fnaverbooking-phinf.pstatic.net%2F20190209_77%2F1549691085842vghae_JPEG%2Fimage.jpg"
            in 600..700 -> "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211105_206%2F1636091516654aAnFD_JPEG%2FOzUk2Ef0cTLopz6p6c6Y_Lqq.jpg"
            in 700..800 -> "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=7N3ezHssT0VP9YB6ymhT3Q&cb_client=search.gws-prod.gps&w=408&h=240&yaw=236.2236&pitch=0&thumbfov=100"
            in 800..900 -> "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20200216_170%2F1581796551224ljcKt_JPEG%2FLRKFMa5eVh6EttTdvPVlwZ2w.jpg"
            in 900..999 -> "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20160328_131%2F1459161751670g8Ngo_JPEG%2Fimage.JPEG"
            else -> "https://w.namu.la/s/dc42bb0527e08b0d65f370f3a8ad1c471ccbd90a5f01b85343e6471c7f4100486b9be8514d380c33651c70fdc1c7da610cd2effaa9696b1226d29082faa22131e41b8bd7a75491abd0819c4789a517c04b802b3948090326bdce86a2ab492a51fef2860145bbad67286ee2f8eb560c22"
        }
        val _latitude = 37.0
        val _longitude = 132.0
        val _location = "우정타운 404호"
        val _tel = "010-9554-6510"
        val cafe = Cafe(
            _cid,
            _oid,
            _sido,
            _gungu,
            _name,
            _url,
            _time,
            _img,
            _latitude,
            _longitude,
            _location,
            _tel
        )
        return cafe
    }


}