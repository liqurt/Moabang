import React, { useEffect, useState } from 'react';
import './Mypage.css'
import { Card, Tab, Row, Nav, Col, Accordion, Pagination } from 'react-bootstrap';
import Content_1 from '../mypage/my_contents/Content_1'
import Content_2 from '../mypage/my_contents/Content_2'
import Content_3 from '../mypage/my_contents/Content_3'
import axios from 'axios';


const Mypage = () => {

    const Token = localStorage.getItem('myToken');
    const [myReview, setMyReview] = useState([]);
    const [reviewList, setReviewList] = useState([]);
    const [myLike, setMyLike] = useState([]);
    const [myLikeCnt, setMyLikeCnt] = useState(0);
    const [myCommunity, setMyCommunity] = useState([]);




    //유저가 이용한 테마 데이터 배열에 저장
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

            })
            .catch((error) => {
                console.error(error);
                // alert("error");
            });
    }
    //유저가 찜한 테마 데이터 배열에 저장
    function getlikeData() {

        axios.get('/mypage/theme/list', {
            headers: {
                Authorization: Token
            }
        })
            .then((res) => {

                console.log(res);
                setMyLike(res.data);
                setMyLikeCnt(res.data.length);

            })
            .catch((error) => {
                console.error(error);
                // alert("error");
            });
    }

    //유저가 작성한 글 데이터 배열에 저장
    function getCommunityData() {

        axios.get('/mypage/reviewcommunity', {
            headers: {
                Authorization: Token
            }
        })
            .then((res) => {

                // setThemeData(res.data);
                // setSortData(res.data.length);
                console.log(res);
                setMyCommunity(res.data.communityList);
                setReviewList(res.data.reviewList);

            })
            .catch((error) => {
                console.error(error);
                // alert("error");
            });
    }

    //한번만 실행
    useEffect(() => {
        getReviewData();
        getlikeData();
        getCommunityData();
    }, []);

    const indexOfLast = myLike * 9;
    const indexOfFirst = indexOfLast - 9;
    function currentPosts(tmp) {
        let currentPosts = 0;
        currentPosts = tmp.slice(indexOfFirst, indexOfLast);
        return currentPosts;
    }


    return (
        <div className='my_page'>

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
                                    찜한 테마
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item className='profile-tab-item-2'>
                                <Nav.Link className='profile-tab-item-2-text' eventKey="second">
                                    이용한 테마
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item className='profile-tab-item-3'>
                                <Nav.Link className='profile-tab-item-3-text' eventKey="third">
                                    작성글 관리
                                </Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col sm={8}>
                        <Tab.Content>
                            <Tab.Pane eventKey="first">
                                <Card className='content-card' >
                                    <Card.Body className='content-card-1-body'>

                                        <Card.Title className='content-card-1-title'>찜한 테마</Card.Title>

                                        <Row xs={1} md={3}>

                                            {myLike.map((data) => {
                                                return <Content_1 data={data} key={data.cid} />
                                            })}
                                        </Row>
                                    </Card.Body>

                                    {/* <Paging page={myLike} count={myLikeCnt} setPage={setMyLike} /> */}
                                </Card>
                            </Tab.Pane>
                            <Tab.Pane eventKey="second">
                                <Card className='content-card' >
                                    <Card.Body className='content-card-2-body'>

                                        <Card.Title className='content-card-2-title'>이용한 테마</Card.Title>
                                        <Card>
                                            <Card.Body>
                                                <Row xs={1} md={3}>

                                                    {myReview.map((data) => {
                                                        return <Content_2 data={data} key={data.cid} />
                                                    })}
                                                </Row>
                                            </Card.Body>
                                        </Card>
                                    </Card.Body>
                                </Card>
                            </Tab.Pane>
                            <Tab.Pane eventKey="third">
                                <Card className='content-card' >
                                    <Accordion defaultActiveKey={['0']} flush>
                                        <Accordion.Item eventKey="0">
                                            <Accordion.Header>작성 글</Accordion.Header>
                                            <Accordion.Body>
                                                {myCommunity.map((data) => {
                                                    return <Content_3 data={data} key={data.rid} />
                                                })}
                                            </Accordion.Body>
                                        </Accordion.Item>
                                        <Accordion.Item eventKey="1">
                                            <Accordion.Header>작성한 리뷰</Accordion.Header>
                                            <Accordion.Body>
                                                {reviewList.map((data) => {
                                                    return <Content_3 data={data} key={data.rid} />
                                                })}
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    </Accordion>
                                </Card>
                            </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container >

        </div >
    );
};

export default Mypage;