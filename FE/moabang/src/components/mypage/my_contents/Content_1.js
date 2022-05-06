import React from 'react';
import { Card } from 'react-bootstrap';
import './Content_1.css'

const Content_1 = () => {
    return (
        <div>
            <Card className='content-card' >
                <Card.Body className='content-card-1-body'>

                    <Card.Title className=''>안녕하세요</Card.Title>
                    <Card.Text className=''>
                        마이페이지 선호정보수정 탭입니다
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    );
};

export default Content_1;