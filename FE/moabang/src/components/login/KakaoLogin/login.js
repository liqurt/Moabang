import axios from 'axios';
import { getCookie, setCookie } from '../../utils/Cookie';

const { Kakao } = window;
/* https://developers.kakao.com/docs/latest/ko/kakaologin/js*/
export const API_HOST = process.env.REACT_APP_API_HOST;

const LoginWithKakao = () => {
  console.log("카카오 로그인 시작");
  const scope = "profile_nickname,profile_image, account_email";


  Kakao.Auth.login({
    scope,
    // success는 인증 정보를 응답(response)으로 받는다. 
    success: function (response) {
      //카카오 SDK에 사용자 토큰을 설정한다.

      window.Kakao.Auth.setAccessToken(response.access_token);

      var ACCESS_TOKEN = window.Kakao.Auth.getAccessToken();

      console.log("ACCESS_TOKEN : " + ACCESS_TOKEN);

      axios.post('/user/klogin', {

      }, {
        headers: {
          token: ACCESS_TOKEN,
        }
      })
        .then((res) => {
          console.log(res.headers.authorization);
          setCookie('myToken', res.headers.authorization, {
            path: "/",
            secure: true,
            sameSite: "none",
            // httpOnly: true,        //httpOnly 옵션은 ie 브라우져를 쓰거나 .com 등으로 끝나는 일반적인 도메인에만 적용가능하다.
          })
          console.log("로그인 성공");
          console.log("myToken : ", getCookie('myToken'));
          document.location.href = '/'
        })
        .catch((error) => {
          console.error(error);
          alert("카카오 로그인 에러?");
        });
    },
    fail: function (error) {
      console.log(error);
    },
  });
};

export default LoginWithKakao;