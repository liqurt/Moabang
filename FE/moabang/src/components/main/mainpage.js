import React from 'react';
import { Link } from 'react-router-dom';

const mainpage = () => {

    return (
        <div>
            LandingPage
            <Link to="/login">로그인</Link>
        </div>
    );
};

export default mainpage;