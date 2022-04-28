package com.ssafy.moabang.src.main.cafe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentCafeListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CafeListFragment : Fragment() {
    private lateinit var repository: Repository
    private lateinit var binding: FragmentCafeListBinding

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
        CoroutineScope(Dispatchers.Main).launch {
            repository.clearAll()
        }
        for(i in 0..9){
            CoroutineScope(Dispatchers.Main).launch {
                repository.insertCafe(getFakeCafe())
            }
        }
    }

    private fun getFakeCafe(): Cafe {
        val _cid : Int = (Math.random() * 1000).toInt()
        val _oid : Int = (Math.random() * 1000).toInt()
        val _sido = "경기도"
        val _gungu = "광주시"
        val _name = "우정타운"
        val _url = "ssafy.com"
        val _time = "매일 09:00-18:00"
        val _img = "https://scontent-ssn1-1.xx.fbcdn.net/v/t31.18172-1/25531945_734296040093191_8501654756992196039_o.jpg?stp=dst-jpg_p160x160&_nc_cat=105&ccb=1-5&_nc_sid=7206a8&_nc_ohc=YxOkRuCz76oAX_PWlh2&_nc_ht=scontent-ssn1-1.xx&oh=00_AT-q9um46b0MmTAW6U-Npp99tdRpUc89WN8eFu4Oz1oDaA&oe=628DBB56"
        val _latitude = 37.0
        val _longitude = 132.0
        val _location = "우정타운 404호"
        val _tel = "010-9554-6510"
        val cafe = Cafe(_cid,_oid,_sido,_gungu,_name,_url,_time,_img,_latitude,_longitude,_location,_tel)
        return cafe
    }



}