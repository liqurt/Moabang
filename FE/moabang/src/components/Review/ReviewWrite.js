
import { useState } from 'react'
import "./ReviewCSS/ReviewWrite.css"

import { succAndfailBtn } from './ReviewData';

const ReviewWrite = () => {
    const [title, setTitle] = useState(""); //제목
    const [joinNum, setJoinNum] = useState(-1); //참가 인원
    const [isCleared, setIsCleared] = useState(""); //성공 여부
    const [star, setStar] = useState(-1); //평점
    const [clearTime, setClearTime] = useState(""); //클리어 시간
    const [active, setActive] = useState(-1); //활동성
    const [hint, setHint] = useState(""); //사용 힌트 수
    const [recommendNum, setRecommendNum] = useState(-1); //추천 인원
    const [cotent, setCotent] = useState("");

    const handleSetTitle = (event) => {      
        setTitle(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetJoinNum = (event) => {      
        setJoinNum(event.target.value)//입력된 텍스트 답아옴
        console.log(event.target.value)


    }
    
    const handleCleared = (event) => {    
        setIsCleared(event.target.value)//입력된 텍스트 답아옴

        //클릭시 색 변경
        const nameId = document.getElementsByClassName('filterBtn5');
        if (event.target.classList[1] === "clicked") {
            event.target.classList.remove("clicked");
        } else {
            for (var i = 0; i < nameId.length; i++) {
                nameId[i].classList.remove("clicked");
            }

            event.target.classList.add("clicked");
        }
        

    }
    
    const handleSetStar = (event) => {      
      
        setStar(event.target.value)//입력된 텍스트 답아옴
    }
    const handleSetClearTime = (event) => {
        console.log(event);
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
        // console.log(
        //     "제목: ", title,
        //     " 성공여부: ", isCleared,
        //     " 참여인원:  ", joinNum,
        //     " 평점: ", star,
        //     " 클리어 타임: ", clearTime,
        //     " 활동성: ", active,
        //     " 사용 힌트 수: ", hint,
        //     " 추천 인원: ", recommendNum
        // );
        
        //빈칸 없는지 체크
        if (title == "") {
            alert("제목을 입력해 주세요");
            return;
        } else if (isCleared == "") {
            alert("성공 여부를 클릭해 주세요");
        }else if (clearTime == "") {
            alert("소요시간");
        }
        else if (cotent == "") {
            alert("내용");
        }
        else if (hint == "") {
            alert("힌트 수");
        }
        else if (joinNum == "-1") {
            alert("참여 인원");
        }
        else if (star == "-1") {
            alert("평점");
        }
        else if (active == "-1") {
            alert("활동성");
        }
        else if (recommendNum == "-1") {
            alert("추천인원");
        } else {
             //axios로 결과를 쏴주고

            //초기화
            setTitle("");
            setIsCleared("");
            setCotent("")

            setClearTime("");
            setHint("");

            setJoinNum(-1);
            setStar(-1);
            setActive(-1);
            setRecommendNum(-1);

        }

        
    }
    
    function checkNumber(event) {
        if(event.key === '.' 
           || event.key === '-'
           || event.key >= 0 && event.key <= 9) {
          return true;
        }
        
        return false;
      }
   

    return (
        <div >
            <details className='toggle-container'>
            <summary >
                <img id='reviewImg' src='https://cdn-icons-png.flaticon.com/512/651/651191.png' alt='reviewImg' />
            </summary>
                <div id='ReviewWriteTitle'>제목:<input id='inputTitle' type="text" value={title} onChange={handleSetTitle} /></div>
                
                <div><span>성공 여부</span>
                {succAndfailBtn &&
                    succAndfailBtn.map((type, index) => (
                        <button className='filterBtn5' key={index} value={type.value} onClick={handleCleared}>
                            {type.name}
                        </button>
                    ))
                    }
                    &nbsp; &nbsp; &nbsp; &nbsp;
                    <span id='ReviewClearTime'>소요시간<input id='input2' type={"number"} onChange={handleSetClearTime} value={clearTime}/>분</span>
                    &nbsp; &nbsp; &nbsp; &nbsp;
                    <span id='ReviewHint'>사용 힌트 수<input id='input2' type={"number"}  onChange={handleSetHint} value={hint }/>개</span>
                </div>
                <div>
                    <span id='ReviewJoinNum'>
                        참가 인원:
                        <select onChange={handleSetJoinNum} value={joinNum}>
                            <option value="-1">선택</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                        </select>명
                    </span>
                    &nbsp; &nbsp; &nbsp; &nbsp;   
                    <span id='ReviewStar'>
                        평점
                        
                        <select onChange={handleSetStar} value={star}>
                        <option value="-1">선택</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>점
                        
                    </span>
                    &nbsp; &nbsp; &nbsp; &nbsp;
                    <span id='ReviewActive'>
                        활동성
                        <select onChange={handleSetActive} value={active}>
                            <option value="-1">선택</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>

                    </span>
                    &nbsp; &nbsp; &nbsp; &nbsp;
                    <span id='ReviewRecommendNum'>
                        추천 인원
                        <select onChange={handleRecommendNum} value={recommendNum}>
                            <option value="-1">선택</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                        </select>
                            명
                    </span>
                </div> 
                <div>
                   
                </div> 
                <div id='ReviewCotent'>
                    <textarea id='inputContent' placeholder='내용을 입력해 주세요' onChange={handleSetCotent} value={cotent}>
                    </textarea>
                </div>
                
                <button className='writeSumitBtn' onClick={handleSubmit}>제출</button>
                
            </details>
        </div>
    )
}

export default ReviewWrite;