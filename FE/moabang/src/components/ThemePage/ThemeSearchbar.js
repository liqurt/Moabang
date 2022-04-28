import React, { useEffect, useState } from "react"
import ThemeList from './ThemeList';
import Paging from './Paging';

import "./ThemeCSS/Theme.css"
import "./ThemeCSS/Paging.css"
import "./ThemeCSS/SearchBar.css";
 
const ThemeSearchbar = (props) => {
    const products = props.searchItems;
    const [searchValue, setSearchValue] = useState("");

    const handleInputChange = (event) => {      
        setSearchValue(event.target.value)//입력된 텍스트 답아옴
    }

    const shouldDisplayButton = searchValue.length > 0;

    const handleInputClear = () => {//텍스트 초기화
        setSearchValue("")
    }
    

    const filteredProducts = products.filter((product) => { 
        return product.name.includes(searchValue);//검색창에 입력된 내용과 비교하여 출력
    })


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
        setPageCnt((c) => c = filteredProducts.length);
        
    }, [searchValue]);

    

    return(
        <div className="searchBar">
            <div className='filter'>
                <div>
                    {shouldDisplayButton && <button className='button4' onClick={handleInputClear}>검색 초기화</button>}
                    <input type="text" className="search__input" value={searchValue} placeholder='검색어 입력'  onChange={handleInputChange}/>
                </div>
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