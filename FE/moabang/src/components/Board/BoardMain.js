import BoardWrite from './BoardWrite';
import BoardList from './BoardList';
import { useState, useEffect } from 'react';
import axios from 'axios';
import "./BoardCSS/BoardMain.css"

const BoardMain = () => {
    const [showList, setShowList] = useState(true);
    const [boardList, setBoardList] = useState([]);
    const [category, setCategory] = useState("전체");


    const getBoardList = () => {
        axios.get('/community/read/list',
            {
                headers: {
                    'Authorization': localStorage.getItem("myToken")
                }
            }
        ).then(res => {
			setBoardList(res.data);
		}).catch(error => {
			console.error(error);
		});
    }
    useEffect(() => {
        getBoardList();
    },[showList]);

    const filteredProducts = boardList.filter((res) => {
        if (category === "전체") {
            return true;
        }
        else {
            return res.header===category;
        }
    })
    
    const WriteBtnHandler = () => {
        setShowList(e => !e); //작성 버튼을 누르면 작성지 보이게

    }
    const Categoryhandler = (e) => {
        console.log(e.target.value);
        setCategory(e.target.value);
    }

    return (
        <div className='board-container'>
            <div className='boardName'>게시판</div>
            {showList ?
                <div>
                    <div className='btnLocaion'>
                    구분:
                        <select onChange={Categoryhandler} value={category}>
                            <option value="전체">전체</option>
                            <option value="구인">구인</option>
                            <option value="자유">자유</option>
                        </select>
                        <button className='mainWriteBtn' onClick={WriteBtnHandler}>글쓰기</button>
                    </div>
                    <BoardList boardList={filteredProducts}/>
                </div>
                :
                <BoardWrite setShowList={setShowList} />
            }
        </div>  
    );
}

export default BoardMain;