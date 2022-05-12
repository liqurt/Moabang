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
            <div className='list_chart'>
                {/* <Community /> */}
                {/* <Community /> */}
            </div>
        </div>
    );
};

export default Mainpage;