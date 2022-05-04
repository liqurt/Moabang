
import { useState } from 'react'
import "./ReviewCSS/ReviewWrite.css"

const ReviewWrite = () => {
    const [title, setTitle] = useState(""); //제목
    const [joinNum, setJoinNum] = useState(0); //참가 인원
    const [isCleared, setIsCleared] = useState(""); //성공 여부
    const [star, setStar] = useState(0); //평점
    const [clearTime, setClearTime] = useState(0); //클리어 시간
    const [active, setActive] = useState(0); //활동성
    const [hint, setHint] = useState(0); //사용 힌트 수
    const [recommendNum, setRecommendNum] = useState(0); //추천 인원
    const [cotent, setCotent] = useState("");

    const handleSetTitle = (event) => {      
        setTitle(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetJoinNum = (event) => {      
        setJoinNum(event.target.value)//입력된 텍스트 답아옴

        const checkboxes = document.getElementsByName("joinNum");
        checkboxes.forEach((cb) => {
            cb.checked = false;
        })
        
        event.target.checked = true;
    }
    const handleSetIsCleared = (event) => {    
        setIsCleared(event.target.value)//입력된 텍스트 답아옴

        const checkboxes = document.getElementsByName("checkIsCleared");
        checkboxes.forEach((cb) => {
            cb.checked = false;
        })
        
        event.target.checked = true;


    }
    const handleSetStar = (event) => {      
        
        const checkboxes = document.getElementsByName("star");
        checkboxes.forEach((cb) => {
            cb.checked = false;
        })
        
        event.target.checked = true;
        setStar(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetClearTime = (event) => {      
        setClearTime(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetActive= (event) => {      
        setActive(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetHint = (event) => {      
        setHint(event.target.value)//입력된 텍스트 답아옴
    }
    const handleRecommendNum = (event) => {      
        setRecommendNum(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetCotent = (event) => {      
        setCotent(event.target.value)//입력된 텍스트 답아옴
    }


    const handleSubmit = () => {
        console.log(title);
        console.log(joinNum);
        console.log(isCleared);
        console.log(star);
        console.log(clearTime);
        console.log(active);
        console.log(hint);
        console.log(recommendNum);

        //axios로 결과를 쏴주고



        //초기화
        setTitle("");
        setJoinNum(0);
        setIsCleared("선택");
        setStar(0);
        setClearTime("");
        setActive(0);
        setHint("");
        setRecommendNum("");
        setCotent("");
    }
    
    return (
        <div >
            <details className='toggle-container'>
            <summary >
                <img id='reviewImg' src='https://cdn-icons-png.flaticon.com/512/651/651191.png' alt='reviewImg' />
            </summary>
                <div id='ReviewWriteTitle'>제목:<input id='input1'type="text" value={title} onChange={handleSetTitle} /></div>
                <div id='ReviewIsCleared'>
                    성공 여부
                    <input type="checkbox" name='checkIsCleared' value={"성공"} onClick={handleSetIsCleared} />성공
                    <input type="checkbox" name='checkIsCleared' value={"실패"} onClick={handleSetIsCleared} />실패
                    
                </div>
                <div id='ReviewJoinNum'>
                    참가 인원:
                    <input type={"checkbox"} name='joinNum' value={1} onClick={handleSetJoinNum}/>1명
                    <input type={"checkbox"} name='joinNum' value={2} onClick={handleSetJoinNum}/>2명
                    <input type={"checkbox"} name='joinNum' value={3} onClick={handleSetJoinNum}/>3명
                    <input type={"checkbox"} name='joinNum' value={4} onClick={handleSetJoinNum}/>4명
                    <input type={"checkbox"} name='joinNum' value={5} onClick={handleSetJoinNum}/>5명
                    <input type={"checkbox"} name='joinNum' value={6} onClick={handleSetJoinNum}/>6명
                </div>
                    
                <div id='ReviewStar'>
                    평점
                    <input type={"checkbox"} name='star' value={0} onClick={handleSetStar}/>0
                    <input type={"checkbox"} name='star' value={1} onClick={handleSetStar}/>1
                    <input type={"checkbox"} name='star' value={2} onClick={handleSetStar}/>2
                    <input type={"checkbox"} name='star' value={3} onClick={handleSetStar}/>3
                    <input type={"checkbox"} name='star' value={4} onClick={handleSetStar}/>4
                    <input type={"checkbox"} name='star' value={5} onClick={handleSetStar}/>5
                    
                </div>
                <div id='ReviewActive'>
                    활동성
                    <select onChange={handleSetActive} value={active}>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>

                </div>
                <div id='ReviewRecommendNum'>
                    추천 인원
                    <select onChange={handleRecommendNum} value={recommendNum}>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                    </select>
                        명
                </div>
                <div id='ReviewClearTime'>소요시간<input id='input2' onChange={handleSetClearTime} value={clearTime}/>분</div>
                
                <div id='ReviewHint'>사용 힌트 수<input id='input2' onChange={handleSetHint} value={hint }/>개</div>
                    
                <div id='ReviewCotent'>내용<input id='input1' onChange={handleSetCotent} value={cotent}></input></div>
                <button onClick={handleSubmit}>제출</button>
                
            </details>
        </div>
    )
}

export default ReviewWrite;