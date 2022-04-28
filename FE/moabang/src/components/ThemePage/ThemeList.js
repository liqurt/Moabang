import React, { useState } from "react"
import Modal from './Modal';
import ThemeDetail from './ThemeDatail';

import "./ThemeCSS/Theme.css"

const ThemeList = (props) => {
    const Theme = props.Theme;

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
            {Theme.map((item, index) => (
                
                <div className='detail-container' key={index} onClick={() => {
                    openModal();
                    setModalData(item);
                }}>
                    <img className='ThemeImg' alt='profile' src={item.img}/>
                    
                    <div className='ThemeInfo'>
                        <div id='ThemeName' >{item.name}</div>
                        <div id='ThemeNumber'>{item.number}</div><br></br>
                        <p id='ThemeAdd'>{item.address}</p>
                    </div>
                    
                </div>
            ))}

            <Modal open={modalOpen} close={closeModal} header="Modal heading">
                <ThemeDetail Theme={modalData} />
            </Modal>
            
            
            
            
        </div>
    )

}

export default ThemeList;

