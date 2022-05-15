import { useState, useEffect } from 'react';
import "./BoardCSS/BoardList.css";
import Paging from './BoardPaging';
import BoardDetail from './BoardDetail';

const BoardList = ({ boardList }) => {

    console.log(boardList)
     //페이징 처리
    const [page, setPage] = useState(1);
    const [pageCnt, setPageCnt] = useState(45);
    
    const handlePageChange = (page) => {
        setPage(page);
        
    };

    const indexOfLast = page * 10;
    const indexOfFirst = indexOfLast - 10;
    function currentPosts(tmp) {
        let currentPosts = 0;
        currentPosts = tmp.slice(indexOfFirst, indexOfLast);
        return currentPosts;
    }

    useEffect(() => {
        setPageCnt((cnt) =>cnt = boardList.length );
        
    }, [boardList]);


    //디테일 페이지 이동
    const goBoardDetail = (e) => {
        console.log(e)
        window.location.href = `/board/detail/?rid=${e.rid}`;
    }
    
    return (
        <div className='List-container'>
            <table >
                <thead>
                    <tr>
                    <th>번호</th><th>제목</th><th>날짜</th><th>구분</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        currentPosts(boardList).map((item, index) => (
                            <tr key={index} onClick={()=>(goBoardDetail(item))}>
                                <td>{index + 1}</td>
                                <td>{item.title}</td>
                                <td>{item.updateDate.split('T', 1)}</td>
                                <td>{item.header}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
            <div>
                <Paging page={page} count={pageCnt} setPage={handlePageChange }/>
            </div>
        </div>
    );
}

export default BoardList;