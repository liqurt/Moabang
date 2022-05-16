import React from 'react';
// import ReactDOM from 'react-dom';
// import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import axios from "axios";
import { createRoot } from 'react-dom/client';
import { CookiesProvider } from 'react-cookie';


axios.defaults.baseURL = "https://모아방.kr:8080";
axios.defaults.withCredentials = true;
// axios.defaults.headers['Access-Control-Allow-Origin'] = '*';
const root = createRoot(document.getElementById('root'));

root.render(
  <BrowserRouter>
    <CookiesProvider>
      <App />
    </CookiesProvider>
  </BrowserRouter>
);
