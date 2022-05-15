import axios from 'axios';
import { useState ,useEffect } from 'react';
import Swal from 'sweetalert2';

const BoardDetail = () => {
    const [board, setBoard] = useState([]);
    const [checkUpdate, setCheckUpdate] = useState(true);
    const [header, setHeader] = useState();
    const [content, setContent] = useState();
    const [title, setTitle] = useState();

    const HeaderSelectHandler = (event) => {
        console.log(event.target.value);
        setHeader(event.target.value);
    }
    const ContentHandler = (event) => {
        console.log(event.target.value);
        setContent(event.target.value);
    }
    const TitleHandler = (event) => {
        console.log(event.target.value);
        setTitle(event.target.value)
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(window.location.href);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }
    const WriteCancelBtn = () => {
        Swal.fire({
            title: '게시글을 수정을 취소 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '예',
            cancelButtonText:'아니요',
        }).then((result) => {
            if (result.isConfirmed) {
                setCheckUpdate(e => !e);
            }     
        })
    }

    const WriteSummitBtn = () => {
        
        //서버로 작성 내용 보내준다
        if (title == "") {
            Swal.fire('제목이 없습니다');
        } else if (header == "-1") {
            Swal.fire('카테고리 미선택');
        }
        else if (content == "") {
            Swal.fire('내용을 입력해 주세요');
        } else {

            Swal.fire({
                title: '게시글을 수정 하시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '예',
                cancelButtonText:'아니요',
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.put(`/community/update/${board.rid}`, {
                        content: content,
                        header: header,
                        title: title,
                    
                    }, {
                        headers: {
                            'Authorization': localStorage.getItem("myToken")
                        }
                    }).then(res => {
                        Swal.fire({
                            icon: 'success',
                            title: "수정 성공."
                        })
                        window.location.href = `/board`;
                    })
                    
                }     
            })
            
        }
        //작성 클릭 시 리스트로 넘어감
        

    }

    //게시글 상세 정보 가져오기
    const getBoard = () => {
        axios.get(`/community/read/${getParameterByName('rid')}`,
            {
                headers: {
                    'Authorization': localStorage.getItem("myToken")
                }
            }
        ).then(res => {
            setBoard(res.data);
            setHeader(res.data.header);
            setContent(res.data.content);
            setTitle(res.data.title);
		}).catch(error => {
			console.error(error);
		});
    }

    useEffect(() => {
        getBoard();
    }, []);

    

    const UpdateBtn = () => {
        setCheckUpdate(e => !e);
    }
    //게시글 삭제
    const DeleteBtn = () => {
        Swal.fire({
            title: '글을 삭제하시겠습니까?',
            text: "삭제하시면 다시 복구시킬 수 없습니다.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '삭제',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.value) {
                
                axios.delete(`/community/delete/${board.rid}`,
                    {
                        headers: {
                            'Authorization': localStorage.getItem("myToken")
                        }
                    }
                ).then((res) => {
                    window.location.href = `/board`;
                }).catch(error => {
                    console.error(error);
                });
                
            }
        })
        
    }
    return (
        <div>
            {checkUpdate ?
                <div className='boardWrite-container'>
                    <div className='boardTitle'>제목: 
                        {board.title}
                    </div>
                    <div className='boardHeader'>카테고리:  
                        {board.header}
                    </div>
                    <div className='boardContent'>
                        {board.content} </div>
                    <button className='boardWriteBtn' onClick={UpdateBtn}>수정</button>
                    <button className='boardCancelBtn' onClick={DeleteBtn}>삭제</button>
                </div>
                :
                <div className='boardWrite-container'>
                    <div className='boardTitle'>제목: 
                        <input className='inputTitle' type="text" onChange={TitleHandler} value={title}></input>
                    </div>
                    <div className='boardHeader'>카테고리:  
                        <select onChange={HeaderSelectHandler} value={header}>
                            <option value="-1">선택</option>
                            <option value="자유">자유</option>
                            <option value="구인">구인</option>
                        </select>
                    </div>
                    <div className='boardContent'>
                        <textarea className='inputContent' type="text" placeholder='내용을 입력해 주세요'  value={content} onChange={ContentHandler}></textarea>
                    </div>
                    <button className='boardWriteBtn' onClick={WriteSummitBtn}>작성</button>
                    <button className='boardCancelBtn' onClick={WriteCancelBtn}>취소</button>
                </div>
            }
            
        </div>
    );    
}

export default BoardDetail;