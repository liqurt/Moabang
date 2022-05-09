import React, { useEffect, useState } from 'react';
import ThemeSearchbar from './ThemeSearchbar';
import axios from 'axios';

import "./ThemeCSS/Theme.css"
/*매장명, 테마명,  장르, 난이도, 시간,인원, 평정, 유형,  사진, 홈페이지, 예약페이지*/



const ThemeMain = () => {
    //카페 리스트를 url로 가져옴
    const [themeData, setthemeData] = useState([]); //DB에서 받아온 데이터 저장
    const [ThemeCount, setThemeCount] = useState(45); //Theme 총 개수
    
    async function getThemeData() {
        axios.get('http://k6d205.p.ssafy.io:8080/cafe/theme/list')
            .then(response => {
                setthemeData(response.data);
                setThemeCount(response.data.length);
                console.log(response);
                

        });
    }
    

    //페이지 네이션
    useEffect(() => {
        getThemeData();
    }, []);
    
    
  

    return (
        <div className='total'>
            <div >
                <ThemeSearchbar searchItems={themeData} totalcnt={ThemeCount}/>
            
            </div>
        </div>
    );
}
export default ThemeMain;