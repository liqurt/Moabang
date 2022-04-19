import React from 'react';
import './loginpage.css'
import { GoogleLogin, GoogleLogout } from 'react-google-login';
import axios from "axios";

export const Google_clinet_Id = process.env.REACT_APP_GOOGLE_CLIENT_ID;

const loginpage = () => {

    const onSuccessGoogle = async (response) => {
        console.log(response);

        const { googleId, profileObj: { email, name }, accessToken } = response;

        // await onSocial({
        //     socialId: googleId,
        //     socialType: 'google',
        //     email,
        //     nickname: name
        // });
        const data = {
            socialId: googleId,
            socialType: 'google',
            email,
            nickname: name
        };
        // axios.post('/login', data).then(response => {
        //     const { accessToken } = response.access_token;

        //     // API 요청하는 콜마다 헤더에 accessToken 담아 보내도록 설정
        //     axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

        //     // accessToken을 localStorage, cookie 등에 저장하지 않는다!

        // }).catch(error => {
        //     // ... 에러 처리
        // });
    }

    const onFailureGoogle = (error) => {
        console.log(error);
    }




    return (
        <div className='home'>
            <h2>Landing_page</h2>
            <GoogleLogin
                clientId={Google_clinet_Id}
                onSuccess={onSuccessGoogle}
                onFailure={onFailureGoogle}
                cookiePolicy='single_host_origin'
            />
            <p></p>
            <GoogleLogout />
        </div >
    );


};


export default loginpage;