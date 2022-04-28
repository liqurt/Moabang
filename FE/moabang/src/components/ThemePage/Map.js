/*global kakao*/ 
import React, { useEffect } from 'react'
import "./ThemeCSS/ThemeDetail.css";

const Map = (props) => {
    const initMap = () => {
        var container = document.getElementById('map');
        var options = {
            center: new kakao.maps.LatLng(props.lat, props.lng),
            level: 3
        };
        var map = new kakao.maps.Map(container, options);

        let markerPosition = new kakao.maps.LatLng(
            props.lat,
            props.lng
        );
          // 마커를 생성
        let marker = new kakao.maps.Marker({
            position: markerPosition,
        });
          // 마커를 지도 위에 표시
        marker.setMap(map);
    }
    

    useEffect(()=>{
        initMap();
    }, [])


    return (
        <div>
            <div id="map" style={{width:"295px", height:"195px"}}></div> 
        </div>
    )
}

export default Map;