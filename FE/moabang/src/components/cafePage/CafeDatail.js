import Map from './Map';

import "./CafeCSS/CafeDetail.css";


const CafeDetail = (props) => {
    const cafe = props.cafe

    console.log(cafe);

    return (
        <div>
            <div className='DetailAndMap'>
                <div className='cafeDetailInfo'>
                    <div id='cafeDetailName'>{cafe.cname}</div>
                    <div id='cafeDetailAdd'>{cafe.location}</div>
                    <div id='cafeDetailNumber'>{cafe.cphone}</div>
                   
                    
                    
                </div>
                <div className='kakaoMap'>
                    <Map lat={cafe.lat} lng={cafe.lng}/>
                </div>
            </div>
            

        </div>
    )

}

export default CafeDetail;