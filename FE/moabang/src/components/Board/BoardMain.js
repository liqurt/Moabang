import BoardWrite from './BoardWrite';
import BoardList from './BoardList';
import { useState, useEffect } from 'react';
import axios from 'axios';

const BoardMain = () => {
    const [showList, setShowList] = useState(true);
    const [boardList, setBoardList] = useState([]);

    const getBoardList = () => {
        axios.get('/community',
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

    
    const WriteBtnHandler = () => {
        setShowList(e => !e); //작성 버튼을 누르면 작성지 보이게

    }

    return (
        <div>
            게시판
            {showList ?
                <span>
                    <button onClick={WriteBtnHandler}>글쓰기</button>
                    <BoardList boardList={boardList}/>
                </span>
                :
                <BoardWrite setShowList={setShowList} />
               

            }
            


        </div>  
    );
}

export default BoardMain;