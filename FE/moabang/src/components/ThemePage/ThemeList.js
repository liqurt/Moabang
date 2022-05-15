import React, { useState } from "react"
import Modal from './Modal';
import ThemeDetail from './ThemeDetail';
import axios from 'axios';
import Swal from 'sweetalert2';

import "./ThemeCSS/Theme.css"
import "../cafePage/Modal/ModalList.css"//난이도와 인원제한 사진 CSS를 가져오기 위한 import



const ThemeList = (props) => {
    const Theme = props.Theme;
    console.log(Theme);
    const heartChange = (event) => {
        //좋아요 버튼
        console.log(event.target.alt)
        axios.get(`/theme/${event.target.alt}/like/`,
            {
                headers: {
                    'Authorization': localStorage.getItem("myToken")
                }
            }
        ).then(response => {
            props.setTListRender(e => !e);
            Swal.fire({
                icon: 'success',
                title: response.data
            })
        }).catch(error => {
            console.error(error);
        });
        
    }


    //Modal창 띄우기
    const [modalOpen, setModalOpen] = useState(false);
    const [modalData, setModalData] = useState(null);

    //모달 열기
    const openModal = () => {
        document.body.style.overflow = "hidden";
        setModalOpen(true);
    };
    //모달 닫기
    const closeModal = () => {
        document.body.style.overflow = "unset";
        setModalOpen(false);
    };
    //난이도별 열쇠 이미지 개수 맞추기
    const DifficultyKeyImg = (diff) => {
        const result = [];
        for (let i = 0; i < diff; i++) {
            result.push(<img key={i}  id='DiffKey' src='https://www.emojiall.com/images/240/htc/1f511.png' alt='keyimg' />)
        }
        return result;

    }
    //사람 개수 최소
    const ConanMin = (min) => {
        const num = parseInt(min.substr(-3,1));
        const result = [];
        for (let i = 0; i < num; i++) {
            result.push(<img key={i}  id='Conan' src='https://us.123rf.com/450wm/iconmama/iconmama1512/iconmama151200208/49892585-%EC%9D%B8%EA%B0%84-%EC%95%84%EC%9D%B4%EC%BD%98.jpg?ver=6' alt='conanMin' />)
        }
        return result;
        
    }
    //사람 개수 최대
    const ConanMax = (max) => {
        const num = parseInt(max.substr(-1));
        const result = [];
        for (let i = 0; i < num; i++) {
            result.push(<img key={i} id='Conan' src='https://us.123rf.com/450wm/iconmama/iconmama1512/iconmama151200208/49892585-%EC%9D%B8%EA%B0%84-%EC%95%84%EC%9D%B4%EC%BD%98.jpg?ver=6' alt='conanMax' />)
        }
        return result;
    }
    //물결표
    const Water = () => {
        return <img id='water' src='https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Tilde.svg/1200px-Tilde.svg.png' alt="water" ></img>
    }
    //별점
    const starScore = () => {
        return <img id='starscore' src='https://emojigraph.org/media/facebook/star_2b50.png' alt='starscore'></img>
    }

    return (
        <div className='detailList' >
            {Theme.map((item, index) => (
                
                <div className='detail-container' key={index} >
                    <img className='ThemeImg' alt='profile' src={item.img} onClick={() => {
                        openModal();
                        setModalData(item);
                    }} />
                    <div className="heartImg">
                        {item.islike ?  
                            <img className="heart " onClick={heartChange} alt={item.tid}
                                src='https://mblogthumb-phinf.pstatic.net/20140709_176/wsm0030_1404859139443xgQQv_PNG/PicsArt_1404831829726.png?type=w800' /> :
                            <img className="heart " onClick={heartChange} alt={item.tid}
                                src='https://mblogthumb-phinf.pstatic.net/20140709_15/wsm0030_1404859141585ixxmQ_PNG/1404859141390_PicsArt_1404833054881.png?type=w2' />
                        }
                        {
                            item.count < 100 ?
                                <div id='likeCnt'>{item.count}</div>:
                                <div id='likeCnt'>100+</div>
                        }
                    </div>
                    
                    <div className='ThemeInfo'>
                        <div id='ThemeName' >{item.tname}</div>
                        <div id='ThemeType' >{item.type}</div>
                        <div id='ThemeGenre' >{item.genre}</div>
                        <div id='ThemeRplayer' >{ConanMin(item.rplayer)}
                            {Water()}{ConanMax(item.rplayer)}</div>
                        <div id='ThemeDiff'>{DifficultyKeyImg(item.difficulty)}</div>
                        <div id='ThemeTime' >{item.time}</div>
                        <div id='ThemeGrade' >{starScore()} {item.grade}</div>
                        
                    </div>
                    
                </div>
            ))}

            <Modal open={modalOpen} close={closeModal} header="Modal heading">
                <ThemeDetail Theme={modalData} setTListRender={props.setTListRender}/>
            </Modal>
            
            
            
            
        </div>
    )

}

export default ThemeList;

