const { naver } = window as any;

function Login(props: any) {
  const initializeNaverLogin = () => {
    const naverLogin = new naver.LoginWithNaverId({
      clientId: wQKQLtHDnA_qHlqtAgoi,
      callbackUrl: "http://localhost:3000/", 
      isPopup: false, // popup 형식으로 띄울것인지 설정
      loginButton: { color: 'white', type: 1, height: '47' }, //버튼의 스타일, 타입, 크기를 지정
    });
    naverLogin.init();
  };
    
  useEffect(() => {
    initializeNaverLogin();
  }, []);
  
  return (
    
    <div id='naverIdLogin' /> 
    
  )
}