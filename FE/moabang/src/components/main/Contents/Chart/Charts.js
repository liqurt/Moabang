import React, { useEffect, useState } from 'react';

import Chart from '../Chart/Chart';
import axios from 'axios';
import './Charts.css'

const Charts = () => {
    const Token = localStorage.getItem('myToken');
    const [cafe, setCafe] = useState([]);
    const [theme, setTheme] = useState([]);

    //카페 전체 데이터 배열에 저장
    function getCafeData() {

        axios.get('/cafe/list')
            .then((res) => {

                setCafe(res.data);


            })
            .catch((error) => {
                console.error(error);
            });
    }
    //카페 전체 데이터 배열에 저장
    function getThemeData() {

        axios.get('/cafe/theme/list')
            .then((res) => {

                setTheme(res.data);


            })
            .catch((error) => {
                console.error(error);
            });
    }

    //한번만 실행
    useEffect(() => {
        getCafeData();
        getThemeData();
    }, []);



    return (
        <div className='new_chart'>
            <div className='my-loc'>
                <p> 커뮤니티 새 글 </p>
            </div>
            <Chart cafe={cafe} theme={theme} />
        </div>
    );
};

export default Charts;