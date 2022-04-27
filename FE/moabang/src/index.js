import React from 'react';
// import ReactDOM from 'react-dom';
// import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import axios from "axios";
import { createRoot } from 'react-dom/client';


axios.defaults.baseURL = "http://114.129.238.28";
// axios.defaults.withCredentials = true;
// axios.defaults.headers['Access-Control-Allow-Origin'] = '*';
const root = createRoot(document.getElementById('root'));

root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
