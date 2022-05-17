import React from 'react';
import Carousels from './Contents/Myloc_cafe/Carousels';
import PopularTheme from './Contents/Popular_theme/Popular_theme';
import Community from './Contents/Community/Community';
import Chart from './Contents/Chart/Charts';
import './mainpage.css'

const mainpage = () => {

    return (
        <div className='mainpage'>
            <Carousels />
            {/* <PopularTheme /> */}
            <div className='list_chart'>
                <Community />
                <PopularTheme />
            </div>

        </div>
    );
};

export default mainpage;