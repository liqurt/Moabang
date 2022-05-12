import React, { useEffect, useState } from 'react';
import { Card } from 'react-bootstrap';
import axios from 'axios';

const Content_4 = (props) => {


    return (
        <div>
            <Card className='content-card' >
                <Card.Body className=''>

                    <Card.Title className=''>반갑습니다</Card.Title>
                    <Card.Text className=''>
                        마이페이지 작성글 관리 탭입니다
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    );
};

export default Content_4;