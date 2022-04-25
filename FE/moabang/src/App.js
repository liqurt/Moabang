
import {
  Routes,
  Navigate,
  Route,
} from 'react-router-dom';
// import './App.css';
import Login from './components/login/loginpage';
import Home from './components/main/mainpage';
// import Error from './components/error/error';

function App() {

  return (
    <div className="App">


      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<Login />} />
        {/* <Route path="/*" element={<Error />} /> */}
      </Routes>
    </div>
  );
}

export default App;
