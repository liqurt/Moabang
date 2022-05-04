import React, { useState } from "react"
import ModalMain from './Modal/ModalMain';
import CafeDetail from './CafeDatail';

import "./CafeCSS/Cafe.css"

const CafeList = (props) => {
    const cafe = props.cafe;

    //Modal창 띄우기
    const [modalOpen, setModalOpen] = useState(false);
    const [modalData, setModalData] = useState(null);

    const openModal = () => {
        document.body.style.overflow = "hidden";
        setModalOpen(true);
    };
    const closeModal = () => {
        document.body.style.overflow = "unset";
        setModalOpen(false);
    };

    return (
        <div className='detailList' >
            {cafe.map((item, index) => (
                
                <div className='detail-container' key={index} onClick={() => {
                    openModal();
                    setModalData(item);
                }}>
                    <img className='cafeImg' alt='profile' src={item.img}/>
                    
                    <div className='cafeInfo'>
                        <div id='cafeName' >{item.cname}</div>
                        <div id='cafeAdd'>{item.location}</div>
                        <div id='cafeNumber'>{item.cphone}</div>
                    </div>
                    
                </div>
            ))}

            <ModalMain open={modalOpen} close={closeModal} header="Modal heading">
                <CafeDetail cafe={modalData} />
            </ModalMain>
            
            
            
            
        </div>
    )

}

export default CafeList;

