import React, { useState } from "react"
import Modal from './Modal';
import CafeDetail from './CafeDatail';

import "./CafeCSS/Cafe.css"

const CafeList = (props) => {
    const cafe = props.cafe;

    //Modal창 띄우기
    const [modalOpen, setModalOpen] = useState(false);
    const [modalData, setModalData] = useState(null);

    const openModal = () => {
        setModalOpen(true);
    };
    const closeModal = () => {
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
                        <div id='cafeNumber'>{item.cphone}</div><br></br>
                    </div>
                    
                </div>
            ))}

            <Modal open={modalOpen} close={closeModal} header="Modal heading">
                <CafeDetail cafe={modalData} />
            </Modal>
            
            
            
            
        </div>
    )

}

export default CafeList;

