import Map from './Map';

import "./ThemeCSS/ThemeDetail.css";


const ThemeDetail = (props) => {
    const Theme = props.Theme

    console.log(Theme);

    return (
        <div>
            <div className='DetailAndMap'>
                <div className='ThemeDetailInfo'>
                    <div id='ThemeDetailName'>{Theme.name}</div>
                    <div id='ThemeDetailAdd'>{Theme.address}</div>
                    <div id='ThemeDetailNumber'>{Theme.number}</div>
                   
                    
                    
                </div>
                <div className='kakaoMap'>
                    <Map lat={Theme.lat} lng={Theme.lng}/>
                </div>
            </div>
            

        </div>
    )

}

export default ThemeDetail;