import React from 'react';
import './loginpage.css'
import { GoogleLogin, GoogleLogout } from 'react-google-login';
import axios from "axios";
import logo from '../login/모아방 로고.png';

const Google_clinet_id = process.env.REACT_APP_GOOGLE_CLIENT_ID;
const loginpage = () => {

    const onSuccessGoogle = async (response) => {
        console.log(response);
        const { googleId, profileObj: { email, name }, accessToken } = response;
        const data = {
            accessToken: accessToken
        }
        console.log(data);

        // 로그인 성공한 경우 access토큰을 백으로 보내 
        await axios.get('/test/login',
            {
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

    //로그아웃
    const onLogoutSuccess = () => {
        alert("로그아웃 되었습니다!");
    };

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
            <p>카카오 로그인</p>
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