import React from 'react';
import Slider from "react-slick";
import { Card } from 'react-bootstrap';
import './Popular_theme.css'

const Popular_theme = () => {
    const settings = {
        dots: true,             // 스크롤바 아래 점으로 페이지네이션 여부
        infinite: true, 	    //무한 반복 옵션	 
        slidesToShow: 1,		// 한 화면에 보여질 컨텐츠 개수
        slidesToScroll: 1,		//스크롤 한번에 움직일 컨텐츠 개수
        speed: 500,	            // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows: true,
        draggable: true, 	//드래그 가능 여부 
        autoplay: false,
    };
    return (
        <div className="container">
            <div className="pop">
                <div className='pop-theme'>

                    <p> 인기 테마 </p>
                </div>
                <Slider {...settings}>
                    <div className='pop-picture'>
                        <img src="http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC.jpg" alt="img" />

                        <Card className='theme-info'>
                            <Card.Body>
                                <Card.Title>넥스트 에디션 강남 1호점</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">꼬레아 우레</Card.Subtitle>
                                <Card.Text>
                                    공포 스릴러 75분 ☆ 4.9
                                    <br />
                                    난이도 5 인원 2~4 타입 장치 활동성 보통
                                </Card.Text>
                                <Card.Text>
                                    개요
                                </Card.Text>
                                <Card.Link href="#">테마 바로가기</Card.Link>
                            </Card.Body>
                        </Card>
                    </div>
                    <div className='pop-picture'>
                        <img src="http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC.jpg" alt="img" />

                        <Card className='theme-info'>
                            <Card.Body>
                                <Card.Title>넥스트 에디션 강남 1호점</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">꼬레아 우레</Card.Subtitle>
                                <Card.Text>
                                    공포 스릴러 75분 ☆ 4.9
                                    <br />
                                    난이도 5 인원 2~4 타입 장치 활동성 보통
                                </Card.Text>
                                <Card.Text>
                                    개요
                                </Card.Text>
                                <Card.Link href="#">테마 바로가기</Card.Link>
                            </Card.Body>
                        </Card>
                    </div>
                    <div className='pop-picture'>
                        <img src="http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC.jpg" alt="img" />

                        <Card className='theme-info'>
                            <Card.Body>
                                <Card.Title>넥스트 에디션 강남 1호점</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">꼬레아 우레</Card.Subtitle>
                                <Card.Text>
                                    공포 스릴러 75분 ☆ 4.9
                                    <br />
                                    난이도 5 인원 2~4 타입 장치 활동성 보통
                                </Card.Text>
                                <Card.Text>
                                    개요
                                </Card.Text>
                                <Card.Link href="#">테마 바로가기</Card.Link>
                            </Card.Body>
                        </Card>
                    </div>
                    <div className='pop-picture'>
                        <img src="http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC.jpg" alt="img" />

                        <Card className='theme-info'>
                            <Card.Body>
                                <Card.Title>넥스트 에디션 강남 1호점</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">꼬레아 우레</Card.Subtitle>
                                <Card.Text>
                                    공포 스릴러 75분 ☆ 4.9
                                    <br />
                                    난이도 5 인원 2~4 타입 장치 활동성 보통
                                </Card.Text>
                                <Card.Text>
                                    개요
                                </Card.Text>
                                <Card.Link href="#">테마 바로가기</Card.Link>
                            </Card.Body>
                        </Card>
                    </div>
                    <div className='pop-picture'>
                        <img src="http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC.jpg" alt="img" />

                        <Card className='theme-info'>
                            <Card.Body className='theme-info-body'>
                                <Card.Title className='theme-info-title'>넥스트 에디션 강남 1호점</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">꼬레아 우레</Card.Subtitle>
                                <Card.Text className='theme-info-type'>
                                    공포 스릴러 75분 ☆ 4.9
                                    <br />
                                    난이도 5 인원 2~4 타입 장치 활동성 보통
                                </Card.Text>
                                <Card.Text className='theme-info-text'>
                                    개요
                                </Card.Text>
                                <Card.Link className='theme-info-link' href="#">테마 바로가기</Card.Link>
                            </Card.Body>
                        </Card>
                    </div>
                    <div className='pop-picture'>
                        <img src="http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC.jpg" alt="img" />

                        <Card className='theme-info'>
                            <Card.Body>
                                <Card.Title>넥스트 에디션 강남 1호점</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">꼬레아 우레</Card.Subtitle>
                                <Card.Text>
                                    공포 스릴러 75분 ☆ 4.9
                                    <br />
                                    난이도 5 인원 2~4 타입 장치 활동성 보통
                                </Card.Text>
                                <Card.Text>
                                    개요
                                </Card.Text>
                                <Card.Link href="#">테마 바로가기</Card.Link>
                            </Card.Body>
                        </Card>
                    </div>
                </Slider >
            </div>
        </div >
    );
};

export default Popular_theme;