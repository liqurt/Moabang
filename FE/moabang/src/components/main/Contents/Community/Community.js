import React, { useEffect, useState } from 'react';
import './Community.css'
import { ListGroup, Badge } from 'react-bootstrap';
import axios from 'axios';
import List_item from './List_item'


const Community = () => {

    const Token = localStorage.getItem('myToken');
    const [newlist, setNewlist] = useState([]);
    //카페 전체 데이터 배열에 저장
    function getThemeData() {

        axios.get('/community/new', {
            headers: {
                Authorization: Token
            }
        })
            .then((res) => {

                console.log(res);
                setNewlist(res)


            })
            .catch((error) => {
                console.error(error);
            });
    }

    //한번만 실행
    useEffect(() => {
        getThemeData();
    }, []);

    return (
        <div className='community'>
            <div className='community-new'>
                <p> 커뮤니티 새 글 </p>
            </div>
            <ListGroup as="ol" numbered>
                {newlist.map((data) => {
                    return <List_item data={data} key={data.cid} />
                })}

            </ListGroup>
        </div>
    );
};

export default Community;