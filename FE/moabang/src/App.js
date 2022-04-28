
import {
  Routes,
  Navigate,
  Route,
} from 'react-router-dom';
// import './App.css';
import Login from './components/login/loginpage';
import Home from './components/main/mainpage';
// import Error from './components/error/error';
import CafeMain from './components/cafePage/CafeMain';
import ThemeMain from './components/ThemePage/ThemeMain';

import "./App.css";
function App() {

  return (
    <div className="App">
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<Login />} />
        <Route path="/cafe" element={<CafeMain />} />
        <Route path="/theme" element={<ThemeMain />} />
        {/* <Route path="/*" element={<Error />} /> */}
      </Routes>
  
      
    </div>
  );
}

export default App;
