import {
  Routes,
  Route
} from 'react-router-dom';
// import './App.css';
import Login from './components/login/loginpage';
import Home from './components/main/mainpage';
import Mypage from './components/mypage/Mypage';
import Navbar from './components/nav/Navibar';
// import Error from './components/error/error';

function App() {
  return (
    <div className="App">
      <Navbar />
      <Routes>

        <Route path="/home" element={<Home />} />
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/" element={localStorage.getItem('myToken') ? <Home /> : <Login />} />
        {/* <Route path="/*" element={<Error />} /> */}
      </Routes>
    </div>
  );
}

export default App;
