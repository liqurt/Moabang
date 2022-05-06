import React from 'react';
import './Mypage.css'
import { Card, Tab, Row, Nav, Col } from 'react-bootstrap';
import Content_1 from '../mypage/my_contents/Content_1'
import Content_2 from '../mypage/my_contents/Content_2'
import Content_3 from '../mypage/my_contents/Content_3'
import Content_4 from '../mypage/my_contents/Content_4'

const Mypage = () => {
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
                                <Content_2 />
                            </Tab.Pane>
                            <Tab.Pane eventKey="fourth">
                                <Content_2 />
                            </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </div>
    );
};

export default Mypage;