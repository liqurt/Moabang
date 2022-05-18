
import "./BoardCSS/CommentList.css";
import Paging from './CommentPaging';
import { useState, useEffect } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';

const CommentList = ({ commentList,setCheckComment }) => {
    console.log(commentList);
    const [page, setPage] = useState(1);
    const [pageCnt, setPageCnt] = useState(45);
    
    const handlePageChange = (page) => {
        setPage(page);
        
    };
    const indexOfLast = page * 5;
    const indexOfFirst = indexOfLast - 5;
    function currentPosts(tmp) {
        let currentPosts = 0;
        currentPosts = tmp.slice(indexOfFirst, indexOfLast);
        return currentPosts;
    }

    useEffect(() => {
        setPageCnt((cnt) =>cnt = commentList.length );
        
    }, [commentList]);
    
    
    const CommentDeleteBtn = (e) => {
        console.log(e.target.value);
        axios.delete(`/community/comment/delete/${e.target.value}`, {
            headers: {
                'Authorization': localStorage.getItem("myToken")
            }
        }).then(res => {
            Swal.fire({
                icon: 'success',
                title: "삭제 성공."
            })
            setCheckComment(e => e = !e);
            
        })
    }


    return (
        <div className='Comment-Container'>
            {
                currentPosts(commentList).map((item, index) => {
                    if (item.userProfile === null) {
                        return (
                            <div className='Comment' key={index}>
                                <img id='CommentImg1' src="https://cdn.discordapp.com/attachments/963307192025485326/975564975617744946/unknown.png" alt='profile'  ></img>
                                
                                <span id='CommentUserName'>{item.userName}</span>
                                <span id='CommentDate'>{item.regDate}</span>
                                {
                                    localStorage.getItem('username') === item.userName ?
                                        <button id='CommentDeleteBtn' value={item.coid} onClick={CommentDeleteBtn}>삭제</button>
                                        :
                                        <span></span>
                                }
                                <div id='CommentContent'>{item.content}</div>
                                <hr id='hrSize'></hr>
                            </div> 
                        )
                    } else {
                        return (
                            <div className='Comment' key={index}>
                                <img id='CommentImg2' src={item.userProfile} alt='profile'  ></img>
                                
                                <span id='CommentUserName'>{item.userName}</span>
                                <span id='CommentDate'>{item.regDate}</span>
                                <div id='CommentContent'>{item.content}</div>
                                <hr id='hrSize'></hr>
                                
                            </div> 
                        )
                    }
                    
                    
                })
            }
            <div>
                <Paging page={page} count={pageCnt} setPage={handlePageChange }/>
            </div>
        </div>
    )

}

export default CommentList;