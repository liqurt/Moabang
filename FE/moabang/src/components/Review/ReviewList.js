import "./ReviewCSS/ReviewList.css";

import { useState, useEffect } from 'react';
import axios from 'axios';

const ReviewList = (props) => {
    //리뷰 리스트를 가져온다.
    const [review, setReview] = useState([]);
    
    async function getReviewData() {
        axios.get(`/theme/review/list/${props.tid}`)
            .then(res => {
                console.log(res)
                setReview(res.data);
            });
    }

    useEffect(() => {
        getReviewData();
    }, []);
    
    const SuccAndFailToString = (e) => {
        if (e === 1) {
            return "성공"
        } else {
            return "실패"
        } 
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
                        <span>수정</span>
                        <span>삭제</span>
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