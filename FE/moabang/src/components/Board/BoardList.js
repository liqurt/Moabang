import { useState, useEffect } from 'react';
import "./BoardCSS/BoardList.css";
import Paging from './BoardPaging';

const BoardList = ({ boardList }) => {
     //페이징 처리
    const [page, setPage] = useState(1);
    const [pageCnt, setPageCnt] = useState(45);
    
    const handlePageChange = (page) => {
        setPage(page);
        
    };
    console.log(boardList);
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
    const changeDate = (date) => {
        let res = date.replace('T', ' ');
        let right = res.split('.', 1); 
        return right;
    }
    return (
        <div className='List-container'>
            <table >
                <thead>
                    <tr>
                    <th>번호</th><th>제목</th><th>날짜</th><th>구분</th><th>작성자</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        currentPosts(boardList).map((item, index) => (
                            <tr key={index} onClick={()=>(goBoardDetail(item))}>
                                <td>{index + 1}</td>
                                <td>{item.title}<span id='BoardListCommentCount'>[{item.count}]</span></td>
                                <td>{changeDate(item.updateDate)}</td>
                                <td>{item.header}</td>
                                <td>{item.user.nickname}</td>
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