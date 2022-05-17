import React from 'react';
import { Card, Button } from 'react-bootstrap';
import './Theme_Card.css'

const Theme_Card = (props) => {
    return (
        <div className='flip-card'>
            <Card className='flip-card-inner'>
                <Card.Img className='flip-card-front' variant="top" src={props.data.img} />
                <Card className='flip-card-back'>
                    <Card.Body>
                        <Card.Title>{props.data.tname}</Card.Title>
                        <Card.Subtitle>{props.data.cname}</Card.Subtitle>
                        <Card.Text>
                            {props.data.genre} {props.data.time} ☆ {props.data.grade}
                            <br />
                            난이도 : {props.data.difficulty ? props.data.difficulty : "정보없음"},
                            인원 : {props.data.rplayer ? props.data.rplayer : "정보없음"},
                            타입 : {props.data.type ? props.data.type : "정보없음"},
                            활동성 : {props.data.activity ? props.data.activity : "정보없음"}
                        </Card.Text>
                        <Button variant="primary" onClick={() => window.open(props.data.url, '_blank')}>카페 홈페이지</Button>
                    </Card.Body>
                </Card>
            </Card >
        </div>

    );
};

export default Theme_Card;