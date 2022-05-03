import React from 'react';
import Carousels from '../main/Carousels';
import Popular_theme from '../main/Popular_theme';
import { getCookie } from '../utils/Cookie';


const mainpage = () => {

    return (
        <div>
            <Carousels />
            <Popular_theme />
        </div>
    );
};

export default mainpage;