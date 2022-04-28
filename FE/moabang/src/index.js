import React from 'react';
// import ReactDOM from 'react-dom';
// import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import axios from "axios";
import { createRoot } from 'react-dom/client';
import "@babel/polyfill";


axios.defaults.baseURL = "http://localhost:3000/";
axios.defaults.withCredentials = true;
const root = createRoot(document.getElementById('root'));


if (!window._babelPolyfill) {
  require("@babel/polyfill");
}

root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
