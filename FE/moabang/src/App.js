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
import CafeMain from './components/cafePage/cafeMain';
import ThemeMain from './components/ThemePage/ThemeMain';
import CompareMain from './components/CompareTheme/CompareMain';
import BoardMain from './components/Board/BoardMain';

import "./App.css";
function App() {
  return (
    <div className="App">
      <Navbar />
      <Routes>

        <Route path="/home" element={<Home />} />
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/" element={localStorage.getItem('myToken') ? <Home /> : <Login />} />
        <Route path="/" element={<Login />} />
        <Route path="/cafe" element={<CafeMain />} />
        <Route path="/theme" element={<ThemeMain />} />
        <Route path="/compare" element={<CompareMain />} />
        <Route path="/board" element={<BoardMain />} />
        {/* <Route path="/*" element={<Error />} /> */}
      </Routes>
    </div>
  );
}

export default App;
