import "./ReviewCSS/ReviewList.css";

import { useState, useEffect } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';

const ReviewList = ({tid, listRender, setListRender}) => {
    const [review, setReview] = useState([]);
    
     //리뷰 리스트를 가져온다.
     function getReviewData() {
         axios.get(`/theme/review/list/${tid}`)
            .then(res => {
                setReview(res.data);
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
                ).then(response => {
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

    return (
        <div >
            {review.map((review, index) => (
                <div key={index} className='review-container'>
                    <div className='ReviewHead'>
                        <img alt='reviewImg' id='reviewListImg' src='https://thumb.ac-illust.com/44/44924d4d6082fc1256d9784422ff3604_t.jpeg'></img>
                        <span id='playDate'>{review.playDate}</span>
                        <span id='ReviewNemNum'>{review.player}명</span>
                        <span id='ReviewSuccAndFail'>{SuccAndFailToString(review.isSuccess)}</span>
                        <button id='ReviewDelete' value={review.rid} onClick={ReviewDeleteHandler}>삭제</button>
                        <button id='ReviewUpdate'>수정</button>
                    </div>
                    
                    <div className='ReviewDetail' >
                        <div>체감 난이도:{review.chaegamDif}</div>
                        <div>평점:{review.rating }</div>
                        <div>소요시간:{review.clearTime }</div>
                        <div>활동성:{review.active }</div>
                        <div>사용 힌트수:{review.hint }</div>
                        <div>추천인원:{review.recPlayer }</div>
                        <div>내용:{review.content}</div>
                    </div>
                </div>
            ))}
        </div>
    )
}

export default ReviewList;