import axios from 'axios';
const { Kakao } = window;

/* https://developers.kakao.com/docs/latest/ko/kakaologin/js*/
export const API_HOST = process.env.REACT_APP_API_HOST;

const loginWithKakao = () => {
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
      // axios.post('/user/klogin', {
      //   headers: {
      //     'token': ACCESS_TOKEN
      //   }
      // })
      //   .then((response) => { console.log(response) })
      //   .catch((Error) => { console.log(Error) })

      axios({
        method: "post",
        url: "http://114.129.238.28/user/klogin",

      }, {
        headers: {
          token: ACCESS_TOKEN,
        }
      })
        // axios.post('/user/login', {
        //   data: {
        //     "token": ACCESS_TOKEN,
        //   }
        // })
        // .then((Response)=>{console.log(Response.data)})
        // .catch((Error)=>{console.log(Error)})
        .then((res) => {
          console.log(res);
          console.log("로그인 성공");
          // history.push("/main/feed");
          // var token = response.headers.get("jwt-token");
          // console.log(token);
        })
        .catch((error) => {
          // console.log(error);
          console.error(error);
          alert("카카오 로그인 에러?");
        });
    },
    fail: function (error) {
      console.log(error);
    },
  });

};

export default loginWithKakao;