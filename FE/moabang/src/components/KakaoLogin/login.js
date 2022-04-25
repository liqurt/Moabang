import axios from 'axios';
const { Kakao } = window;

/* https://developers.kakao.com/docs/latest/ko/kakaologin/js*/
const loginWithKakao = () => {
  console.log("시작1");
  const scope = "profile_nickname,profile_image, account_email";
  Kakao.Auth.login({
    scope,
    // success는 인증 정보를 응답(response)으로 받는다. 
    success: function (response) {
     //카카오 SDK에 사용자 토큰을 설정한다.
      window.Kakao.Auth.setAccessToken(response.access_token);
      
      var ACCESS_TOKEN = window.Kakao.Auth.getAccessToken();
      
      console.log("ACCESS_TOKEN : " + ACCESS_TOKEN);
      
      window.Kakao.API.request({
      url: "/v2/user/me",
      success: function ({ kakao_account }) {
        //어떤 정보 넘어오는지 확인
        console.log(kakao_account);
        const {  email,profile } = kakao_account;
        
        console.log(profile.nickname); //계정 이름
        console.log(email); // 이메일
        console.log(`responsed img: ${profile.profile_image_url}`); //계정 프로필
        

        axios({
          method: "post",
          url: "/user/login",
          data: {
            "id": email,
            "nickname": profile.nickname,
            "image": profile.profile_image_url,
            "token": ACCESS_TOKEN,
          },
        })
          .then((res) => {
            console.log(res);
            // history.push("/main/feed");
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


    },
    fail: function (error) {
      console.log(error);
    },
  });

};

export default loginWithKakao;