import React from 'react';
import Slider from "react-slick";
import logo from '../login/모아방 로고.png';
import './Carousels.css'

const Carousels = () => {
    const settings = {
        dots: true,             // 스크롤바 아래 점으로 페이지네이션 여부
        infinite: true, 	    //무한 반복 옵션	 
        slidesToShow: 3,		// 한 화면에 보여질 컨텐츠 개수
        slidesToScroll: 1,		//스크롤 한번에 움직일 컨텐츠 개수
        speed: 500,	            // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows: true,
        draggable: false, 	//드래그 가능 여부 
        autoplay: true,
    };
    return (
        <div className="container">
            <div className='my-loc'>

                <p> 내 근처 방탈출 카페 </p>
            </div>
            <Slider {...settings}>
                <div className='picture'>
                    <img src="https://lh5.googleusercontent.com/p/AF1QipMO2kd5--SwDQJETAo-wC9HV_gmeqcUIp-DC5mQ=w408-h271-k-no" />

                    <div>
                        <p>넥스트에디션 강남1호점</p>
                    </div>
                </div>
                <div className='picture'>
                    <img src="https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=A5sHAKOEZMAeVtVEcHBRog&cb_client=search.gws-prod.gps&w=408&h=240&yaw=302.96442&pitch=0&thumbfov=100" />
                    <div>
                        <p>넥스트에디션 강남2호점</p>
                    </div>
                </div>
                <div className='picture'>
                    <img src="https://lh5.googleusercontent.com/p/AF1QipNFWXk2oMevBu1ASRNaiWbK6q9xuGA4dLk3TVnY=w408-h306-k-no" />

                    <div>
                        <p>넥스트에디션 강남3호점</p>
                    </div>
                </div>
                <div className='picture'>
                    <img src="https://lh5.googleusercontent.com/p/AF1QipO8YT-qovSJQ3lwus7_-8ErSao1Q3VR9L-3FirY=w408-h544-k-no" />

                    <div>
                        <p>넥스트에디션 강남5호점</p>
                    </div>
                </div>
                <div className='picture'>
                    <img src="https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=a5yegYj6L_kxDkvnSwE57A&cb_client=search.gws-prod.gps&w=408&h=240&yaw=31.844925&pitch=0&thumbfov=100" />

                    <div>
                        <p>도어이스케이프 레드 신논현점</p>
                    </div>
                </div>
                <div className='picture'>
                    <img src="https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=MS903sdl_s-CS8yvfptOMg&cb_client=search.gws-prod.gps&w=408&h=240&yaw=128.22763&pitch=0&thumbfov=100" />

                    <div>
                        <p>도어이스케이프 블루 신논현점</p>
                    </div>
                </div>
            </Slider >
        </div >
    );
};

export default Carousels;