import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import "@babel/polyfill";

if (!window._babelPolyfill) {
  require("@babel/polyfill");
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);


