import React, { useEffect, useState } from 'react';
import ThemeSearchbar from './ThemeSearchbar';

import "./ThemeCSS/Theme.css"

const dumy = [
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    {
        img: "https://img1.kakaocdn.net/cthumb/local/R0x420/?fname=http%3A%2F%2Fpostfiles11.naver.net%2FMjAyMDA4MzFfNjMg%2FMDAxNTk4ODM3MzQyODU1.l06kblOz8_6LEMltEMmPwSeRIWC804IL41vgLkcgrtEg.9FwtKJGaKWTqEZDAO27SbGZDfhPghdOv8tfzIwYUyQsg.JPEG.blancdion%2FIMG_3464.jpg%3Ftype%3Dw966",
        name: "EXIT방탈출카페",
        lat: 37.3503516,
        lng: 127.1071772,
        address: "경기 성남시 분당구 돌마로 47 이코노샤르망 5층 506호",
        number: "031-711-8636" 
    },
    {
        img: "https://lh5.googleusercontent.com/p/AF1QipOvrI42txz4tgPF4MvBYCGud1G3D5EuQcDZwwh5=w640-h240-k-no",
        name: "제로월드 서현점",
        lat: 37.3854751,
        lng: 127.1246006,
        address: "경기 성남시 분당구 서현로210번길 17 광림프라자 지하1층",
        number: "031-707-9242"
    },
    

]

const ThemeMain = () => {
    //카페 리스트를 url로 가져옴
    const [data, setData] = useState([]); //DB에서 받아온 데이터 저장
    const [ThemeCount, setCageCount] = useState(45); //Theme 총 개수
    // async function fetchUrl() {
    //     const response = await fetch("여기에 URL 입력");
    //     const json = await response.json();
    //     setCageCount(cnt => cnt = json.length);
    //     setData(json);
    // }
    
    
    console.log(data);
    console.log(ThemeCount);

    //페이지 네이션
    useEffect(() => {
        setData((data) => data = dumy);
        setCageCount(cnt => cnt = dumy.length);
        //fetchUrl();
    }, []);
  

    return (
        <div className='total'>
            <div >
                <ThemeSearchbar searchItems={data} totalcnt={ThemeCount}/>
            
            </div>
        </div>
    );
}
export default ThemeMain;