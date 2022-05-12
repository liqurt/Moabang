import React, { useEffect, useState } from 'react';
import './Mypage.css'
import { Card, Tab, Row, Nav, Col } from 'react-bootstrap';
import Content_1 from '../mypage/my_contents/Content_1'
import Content_2 from '../mypage/my_contents/Content_2'
import Content_3 from '../mypage/my_contents/Content_3'
import Content_4 from '../mypage/my_contents/Content_4'
import axios from 'axios';

const Mypage = () => {

    const Token = localStorage.getItem('myToken');
    const [myReview, setMyReview] = useState([]);

    //카페 전체 데이터 배열에 저장
    function getReviewData() {

        axios.get('/mypage/theme/review', {
            headers: {
                Authorization: Token
            }
        })
            .then((res) => {

                // setThemeData(res.data);
                // setSortData(res.data.length);
                console.log(res);
                setMyReview(res.data);
                console.log(myReview);

            })
            .catch((error) => {
                console.error(error);
                // alert("error");
            });
    }

    //한번만 실행
    useEffect(() => {
        getReviewData();
    }, []);

    //테마데이터가 저장되면 실행
    useEffect(() => {
        slice_ReviewData();
    }, [myReview]);

    function slice_ReviewData() {
        myReview.map((data) => {
            return console.log(data);
        })
        // console.log(myReview[0]);
        return 0;
    }

    return (
        <div>

            <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                <Row>
                    <Col sm={4}>
                        <Card className='profile-card' >
                            <Card.Body className='profile-card-body'>
                                <Card.Img className='profile-card-img' variant="top" src={localStorage.getItem('p_img')} />
                                <Card.Title className='profile-card-username'>{localStorage.getItem('username')}</Card.Title>
                                <Card.Text className='profile-card-email'>
                                    {localStorage.getItem('email')}
                                </Card.Text>
                            </Card.Body>
                        </Card>
                        <Nav variant="tabs" className="flex-column">
                            <Nav.Item className='profile-tab-item-1'>
                                <Nav.Link className='profile-tab-item-1-text' eventKey="first">
                                    선호정보 수정
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item className='profile-tab-item-2'>
                                <Nav.Link className='profile-tab-item-2-text' eventKey="second">
                                    찜한 테마
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item className='profile-tab-item-3'>
                                <Nav.Link className='profile-tab-item-3-text' eventKey="third">
                                    이용한 테마
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item className='profile-tab-item-4'>
                                <Nav.Link className='profile-tab-item-4-text' eventKey="fourth">
                                    작성글 관리
                                </Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col sm={8}>
                        <Tab.Content>
                            <Tab.Pane eventKey="first">
                                <Content_1 />
                            </Tab.Pane>
                            <Tab.Pane eventKey="second">
                                <Content_2 />
                            </Tab.Pane>
                            <Tab.Pane eventKey="third">
                                <Content_3 />
                            </Tab.Pane>
                            <Tab.Pane eventKey="fourth">
                                {myReview.map((data) => {
                                    return <Content_4 data={data} key={data.cid} />
                                })}
                            </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>

        </div>
    );
};

export default Mypage;