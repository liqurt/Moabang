import React from 'react';
import { Card, Button } from 'react-bootstrap';

const Theme_Card = (props) => {
    return (
        <div>
            <div className='pop-picture'>
                <img src={props.data.img} alt="img" />

                <Card className='theme-info'>
                    <Card.Body>
                        <Card.Title>{props.data.tname}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{props.data.cname}</Card.Subtitle>
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
            </div>
        </div>
    );
};

export default Theme_Card;