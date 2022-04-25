/**
* react-google-login을 이용한 구글로그인 구현
* react-google-login의 로그아웃 기능 사용 시 
* 재로그인을 할 때 자동 로그인이 되기에 로그아웃은 따로 코드 구현
* @author 최성석
* @version 1.0
* 
*/
import React from 'react';
import './loginpage.css'
import { GoogleLogin } from 'react-google-login';
import KakaoLogin from "../KakaoLogin/KakaoLogin"
import axios from "axios";
import logo from '../login/모아방 로고.png';

//보안 상의 이유로 .env에 저장된 구글 클라이언트 아이디 값 불러오기
const Google_clinet_id = process.env.REACT_APP_GOOGLE_CLIENT_ID;
const loginpage = () => {
    //로그인 성공 시
    const onSuccessGoogle = async (response) => {
        console.log(response);
        const { accessToken } = response;
        const data = {
            accessToken: accessToken
        }
        console.log(data);

        // 로그인 성공한 경우 access토큰을 백으로 보내 줌
        await axios.get('/test/login',
            {
                //헤더에 accessToken값을 담아 보냄
                headers: {
                    code: `${accessToken}`
                }
            })
            .then(response => {

                console.log("로그인 성공 : ", response)

            }).catch(error => {
                // ... 에러 처리
                console.log("Error : ", error)
            });
    }
    //로그인 실패 시
    const onFailureGoogle = (error) => {
        console.log(error);
    }

    //로그아웃 성공 시
    const onLogoutSuccess = () => {
        alert("로그아웃 되었습니다!");
    };
    //로그아웃
    const onLogout = () => {
        if (window.gapi) {
            const auth2 = window.gapi.auth2.getAuthInstance();
            if (auth2 !== null) {
                auth2
                    .signOut()
                    .then(auth2.disconnect().then(() => onLogoutSuccess()))
                    .catch(e => console.log(e));
            }
        }
    };

    return (
        <div className='login'>
            <img src={logo}></img>
            <h2>모아방</h2>
            <KakaoLogin />
            <p>네이버 로그인</p>
            <GoogleLogin
                className="google-button"
                clientId={Google_clinet_id}
                buttonText="구글 로그인" // 버튼에 뜨는 텍스트
                onSuccess={onSuccessGoogle}
                onFailure={onFailureGoogle}
                isSignedIn={true}
            // cookiePolicy={"single_host_origin"}
            />
            <p></p>
            {/* <button onClick={onLogout}>로그아웃</button> */}
            <button type="button" onClick={onLogout}>
                logout
            </button>
        </div>
    );
};

export default loginpage;