import { border } from '@mui/system';
import axios from 'axios';
import { useState ,useEffect } from 'react';
import Swal from 'sweetalert2';
import "./BoardCSS/BoardWrite.css";
import "./BoardCSS/BoardDetail.css"
import CommentList from './CommentList';
const BoardDetail = () => {
    const [board, setBoard] = useState([]);
    const [checkUpdate, setCheckUpdate] = useState(true);
    const [header, setHeader] = useState();
    const [content, setContent] = useState();
    const [title, setTitle] = useState();
    const [nickname, setNickname] = useState();
    const [date, setDate] = useState();
    const [tailCnt, setTailCnt] = useState(0);
    const [comment, setComment] = useState();
    const [pimg, setPimg] = useState();
    const [checkComment, setCheckComment] = useState(true);

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
    const getBoard = async() => {
        await axios.get(`/community/read/${getParameterByName('rid')}`,
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
            setNickname(res.data.user.nickname);
            setDate(res.data.updateDate.replace('T', ' '))
            setPimg(res.data.user.pimg);
		}).catch(error => {
			console.error(error);
		});
    }
    useEffect(() => {
        getBoard();
        getCommentList();
    }, [checkComment]);

    

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
    const goList = () => {
        window.location.href = `/board`;
    }

    
    //===============================================
    //밑으로 댓글 기능
    const [commentList, setCommentList] = useState([]);
    //댓글 생성
    const CreateTail = () => {
        axios.post('/community/comment/create',
            {
                communityId: board.rid,
                content: comment
            },
            {
                headers: {
                    'Authorization': localStorage.getItem("myToken")
                }
            }
        ).then(res => {
            Swal.fire({
                icon: 'success',
                title: "댓글 작성."
            })
            setComment("");
            setCheckComment(e=>e=!e);
        })

    }
    //댓글 리스트 가져오기
    const getCommentList = () => {
        axios.get(`/community/comment/list/${getParameterByName('rid')}`, 
            {
                headers: {
                    'Authorization': localStorage.getItem("myToken")
                }
            }
        ).then(res => {
            setCommentList(res.data);
        })
    }

    //입력 받은 댓글
    const CountTail = (event) => {
        setComment(event.target.value);//댓글
        setTailCnt(event.target.value.length);// 댓글 수
    }

    
    return (
        <div className='BoardDetailTotal'> 
            <div id='BoardDetailMainText'>커뮤니티</div>
            
            {checkUpdate ?
                <div className='BoardDetailContainer'>
                    <hr id='hr1'></hr>
                    <div className='BoardDetailTitleContainer'>
                        <div id='BoardDetailTitle'>{board.title}</div>
                        <span id='BoardDetailDate'>
                            <img id='BoardDetailImg' alt='profile' src={pimg}/>
                            &nbsp;&nbsp;&nbsp;{nickname}&nbsp;&nbsp;&nbsp;
                            {date}&nbsp;&nbsp;&nbsp;
                            {board.header}
                        </span>
                    </div>
                    <hr id='hr2'></hr>
                    <div className='BoardDetailContent'>
                        {board.content}
                    </div>
                    <hr id='hr3'></hr>
                   
                    <button className='BoardDetailWriteBtn '  onClick={UpdateBtn}>수정</button>
                    <button className='BoardDetailCancelBtn' onClick={DeleteBtn}>삭제</button>
                    <button className='goList' onClick={goList}>목록</button>
                    <div>
                        <div className='tailMain'>댓글</div>
                        <div className='tailForm'>
                            <textarea id='tailContent'  maxLength='100' onChange={CountTail} value={comment}></textarea>
                            <span id='tailCnt'>{tailCnt}/100자</span>
                            <button id='tailWriteBtn' onClick={CreateTail}>등록</button>
                        </div>
                    </div>
                    <CommentList commentList={commentList} setCheckComment={setCheckComment}/>

                </div>
                    
                
                :
                <div className='Board-Write-Main'>
                    <div className='boardWrite-container'>
                        <div className='boardTitle'>
                            <span id='boardWriteText'>제목<span id='TextStar'>*</span></span> 
                            <input className='inputTitle' placeholder='제목을 입력해주세요' type="text" onChange={TitleHandler} value={title}></input>
                        </div>
                        <div className='boardHeader'>
                            <span id='boardWriteText'>게시판<span id='TextStar'>*</span></span>
                            <select className='BoardWriteinputSelect' onChange={HeaderSelectHandler} value={header}>
                                <option value="-1">선택</option>
                                <option value="자유">자유</option>
                                <option value="구인">구인</option>
                            </select>
                        </div>
                        <div className='boardContent'>
                            <span id='boardWriteText'>내용<span id='TextStar'>*</span></span> 
                            <textarea className='inputContent' type="text" placeholder='내용을 입력해 주세요'  value={content} onChange={ContentHandler}></textarea>
                        </div>
                        <button className='boardWriteBtn '  onClick={WriteSummitBtn}>확인</button>
                        <button className='boardCancelBtn' onClick={WriteCancelBtn}>취소</button>
                    </div>
                </div>
                
            }
            
        </div>
    );    
}

export default BoardDetail;
