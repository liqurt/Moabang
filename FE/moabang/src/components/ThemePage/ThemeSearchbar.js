import React, { useEffect, useState } from "react"
import ThemeList from './ThemeList';
import Paging from './Paging';

import "./ThemeCSS/Theme.css"
import "./ThemeCSS/Paging.css"
import "./ThemeCSS/SearchBar.css";
 
const ThemeSearchbar = (props) => {
    const products = props.searchItems;
    const [searchValue, setSearchValue] = useState("");
    const [joinNum, setJoinNum] = useState(0); //참가 인원
    const [star, setStar] = useState(0); //평점
    const [active, setActive] = useState(0); //활동성

    const handleInputChange = (event) => {      
        setSearchValue(event.target.value)//입력된 텍스트 답아옴
    }

    const shouldDisplayButton = searchValue.length > 0;

    const handleInputClear = () => {//텍스트 초기화
        setSearchValue("")
    }
    

    const filteredProducts = products.filter((product) => { 
        return product.tname.includes(searchValue);//검색창에 입력된 내용과 비교하여 출력
    })

    //필터 체크 박스 영역 ---------------------------------
    const handleSetJoinNum = (event) => {      
        setJoinNum(event.target.value)//입력된 텍스트 답아옴

        const checkboxes = document.getElementsByName("joinNum");
        checkboxes.forEach((cb) => {
            cb.checked = false;
        })
        
        event.target.checked = true;
    }
    const handleSetStar = (event) => {      
        setStar(event.target.value)//입력된 텍스트 답아옴
        const checkboxes = document.getElementsByName("star");
        checkboxes.forEach((cb) => {
            cb.checked = false;
        })
        
        event.target.checked = true;
    }
    const handleSetActive= (event) => {      
        setActive(event.target.value)//입력된 텍스트 답아옴
    }


    //위로 체크 박스 영역-----------------------------
    //페이징 처리
    const [page, setPage] = useState(1);
    const [pageCnt, setPageCnt] = useState(45);
    
    const handlePageChange = (page) => {
        setPage(page);
        
    };

    const indexOfLast = page * 9;
    const indexOfFirst = indexOfLast - 9;
    function currentPosts(tmp) {
        let currentPosts = 0;
        currentPosts = tmp.slice(indexOfFirst, indexOfLast);
        return currentPosts;
    }

    useEffect(() => {
        setPageCnt((cnt) => cnt = filteredProducts.length);
        
    }, [searchValue]);

    

    return(
        <div className="searchBar">
            <div className='filter'>
                <div>
                    {shouldDisplayButton && <button className='button4' onClick={handleInputClear}>검색 초기화</button>}
                    <input type="text" className="search__input" value={searchValue} placeholder='검색어 입력'  onChange={handleInputChange}/>
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
                    <input type={"checkbox"} name='star' value={0} onClick={handleSetStar}/>1이하
                    <input type={"checkbox"} name='star' value={1} onClick={handleSetStar}/>1이상
                    <input type={"checkbox"} name='star' value={2} onClick={handleSetStar}/>2이상
                    <input type={"checkbox"} name='star' value={3} onClick={handleSetStar}/>3이상
                    <input type={"checkbox"} name='star' value={4} onClick={handleSetStar}/>4이상
                    <input type={"checkbox"} name='star' value={5} onClick={handleSetStar}/>5이상
                    
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


            </div>
            <br></br>
            <div>
                난이도
                <img id='DiffKey' src='https://www.emojiall.com/images/240/htc/1f511.png' alt='keyimg' />
                &nbsp;&nbsp;&nbsp;인원
                <img id='Conan' src='https://us.123rf.com/450wm/iconmama/iconmama1512/iconmama151200208/49892585-%EC%9D%B8%EA%B0%84-%EC%95%84%EC%9D%B4%EC%BD%98.jpg?ver=6' alt='conanMax' />
            </div>

            <div >
                <ThemeList Theme={currentPosts(filteredProducts)} />
                
            </div>

            <div>
                <Paging page={page} count={pageCnt} setPage={handlePageChange }/>
            </div>
        </div>

    )

    

}

 

export default ThemeSearchbar;