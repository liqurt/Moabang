import React from 'react';
import Carousels from '../main/Contents/Carousels';
import PopularTheme from '../main/Contents/Popular_theme';
import Community from '../main/Contents/Community';
import Footer from './Contents/Footer';


const mainpage = () => {

    return (
        <div>
            <Carousels />
            <PopularTheme />
            <Community />
            <Footer />
        </div>
    );
};

export default mainpage;