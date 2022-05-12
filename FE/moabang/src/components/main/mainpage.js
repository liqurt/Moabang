import React from 'react';
import Carousels from './Contents/Myloc_cafe/Carousels';
import PopularTheme from './Contents/Popular_theme/Popular_theme';
import Community from './Contents/Community/Community';
import './Mainpage.css'

const Mainpage = () => {

    return (
        <div>
            <Carousels />
            <PopularTheme />
            <Community />
        </div>
    );
};

export default Mainpage;