import "./ReviewCSS/ReviewList.css";

import { useState, useEffect } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import Paging from '../ThemePage/PagingReview';

const ReviewList = ({tid, listRender, setListRender}) => {
    const [review, setReview] = useState([]);
    const [email, setEmail] = useState();
     //리뷰 리스트를 가져온다.
    const getReviewData=async()=>{
        await axios.get(`/theme/review/list/${tid}`)
            .then(res => {
                console.log(res.data[0].userInfo);
                setReview(res.data);
                setEmail(res.data.userInfo.email)
            }).catch(error => {
            });
    }
    
    useEffect(() => {
        getReviewData();
    }, [listRender]);
    
    const SuccAndFailToString = (e) => {
        if (e === 1) {
            return "성공"
        } else {
            return "실패"
        } 
    }

    //===========================================
    //삭제 핸들러
    const ReviewDeleteHandler = (event) => {
        Swal.fire({
            title: '글을 삭제하시겠습니까???',
            text: "삭제하시면 다시 복구시킬 수 없습니다.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '삭제',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.value) {
                
                axios.delete(`/theme/review/delete/${event.target.value}`,
                    {
                        headers: {
                            'Authorization': localStorage.getItem("myToken")
                        }
                    }
                ).then((res) => {
                    Swal.fire({
                        icon: 'success',
                        title: '삭제 성공'
                    })
                    setListRender(e => !e);
                }).catch(error => {
                    console.error(error);
                });

            }
        })



    }

    //===========================================
    //페이징 처리
    const [page, setPage] = useState(1);
    const [pageCnt, setPageCnt] = useState(450);
    
    const handlePageChange = (page) => {
        setPage(page);
        
    };
    
    const indexOfLast = page * 3;
    const indexOfFirst = indexOfLast - 3;
    function currentPosts(tmp) {
        let currentPosts = 0;
        currentPosts = tmp.slice(indexOfFirst, indexOfLast);
        return currentPosts;
    }

    useEffect(() => {
        setPageCnt((cnt) =>cnt = review.length );
        
    }, [review]);

    const starScore = () => {
        return <img id='ReviewListStarImg' src='https://emojigraph.org/media/facebook/star_2b50.png' alt='starscore'></img>
    }

    console.log(review);
    return (
        <div >
            {
                review.length === 0 ?
                    <div id='emptyReviewContent'>
                        리뷰가 없어요!!
                    </div>
                    
                    :
                    <div>
                        {currentPosts(review).map((review, index) => (
                            <div key={index} className='review-container'>
                                <div className='ReviewHead'>
                                    <img alt='reviewImg' id='reviewListImg' src='https://thumb.ac-illust.com/44/44924d4d6082fc1256d9784422ff3604_t.jpeg'></img>
                                    <span id='playDate'>{review.playDate}</span>
                                    <span id='ReviewNemNum'>{review.player}명</span>
                                    <span id='ReviewSuccAndFail'>{SuccAndFailToString(review.isSuccess)}</span>
                                    {
                                        localStorage.getItem('email') === review.userInfo.email ?
                                            <button id='ReviewDelete' value={review.rid} onClick={ReviewDeleteHandler}>삭제</button>
                                            :
                                            <span></span>
                                    }
                                    
                                    
                                </div>
                                
                                <div className='ReviewDetail' >
                                    <br></br>
                                    <span id='ReviewListStar'>{starScore()}&nbsp;x&nbsp;{review.rating }</span>
                                    <div id='ReviewListDiff'><span id='ReviewListText'>체감 난이도:</span>&nbsp;{review.chaegamDif}</div>
                                    <div id='ReviewListTime'><span id='ReviewListText'>클리어 타임:</span>&nbsp;{review.clearTime }분</div>
                                    <div id='ReviewListActive'><span id='ReviewListText'>활동성:</span>&nbsp;{review.active }</div>
                                    <div id='ReviewListHint'><span id='ReviewListText'>사용 힌트수:</span>&nbsp;{review.hint}개</div>
                                    <div id='ReviewListRecNum'><span id='ReviewListText'>추천인원:</span>&nbsp;{review.recPlayer }명</div>
                                   
                                    <div id='ReviewListContent'><span id='ReviewListText'>내용:</span>{review.content}</div>
                                </div>
                            </div>
                        ))}
                        <br></br>
                        <br></br>
                        <div>
                            <Paging page={page} count={pageCnt} setPage={handlePageChange }/>
                        </div>
                    </div>
                    
            }
            
        </div>
    )
}

export default ReviewList;