import "./ReviewCSS/ReviewList.css";



const ReviewList = (props) => {
    //리뷰 리스트를 가져온다.
    const Review = props.ReviewData;


    return (
        <div >
            {Review.map((review, index) => (
                <div key={index} className='review-container'>
                    <div className='ReviewHead'>
                        <div id='circle'>○</div>
                        <div id='ReviewTitle'>{review.title}</div>
                        <div id='ReviewNemNum'>{review.memNum}명</div>
                        <div id='ReviewSuccAndFail'>{review.succAndFail}</div>
                    </div>
                    
                    <div className='ReviewDetail' >
                        
                        <div>평점:{review.star }</div>
                        <div>소요시간:{review.time }</div>
                        <div>활동성:{review.active }</div>
                        <div>사용 힌트수:{review.hint }</div>
                        <div>추천인원:{review.recommendNum }</div>
                        <div>내용:{review.content }</div>
                    </div>
                </div>
            ))}
        </div>
    )
}

export default ReviewList;