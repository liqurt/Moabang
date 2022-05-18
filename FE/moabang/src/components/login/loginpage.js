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
import KakaoLogin from "./KakaoLogin/KakaoLogin"
import logo from '../../image/Main_logo.png';

const loginpage = () => {

    return (
        <div className='login'>
            <img className='logo' src={logo}></img>
            <KakaoLogin />
            {/* <NaverLogin />
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
            {/* <button type="button" onClick={onLogout}>
            logout
        </button> */}
        </div>
    );
};

export default loginpage;