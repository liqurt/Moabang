import React from 'react';
import { Card, Button } from 'react-bootstrap';
import './Theme_Card.css'

const Theme_Card = (props) => {
    return (
        <div >
            <Card className='pop-theme-card' >
                <Card.Img className='pop-theme-card-img' variant="top" src={props.data.img} />
                <Card.Body>
                    <Card.Title>{props.data.tname}</Card.Title>
                    <Card.Subtitle>{props.data.cname}</Card.Subtitle>
                    <Card.Text>
                        {props.data.genre}
                        {props.data.time}
                        ☆ {props.data.grade}
                        <br />
                        난이도 : {props.data.difficulty ? props.data.difficulty : "정보없음"},
                        인원 : {props.data.rplayer ? props.data.rplayer : "정보없음"},
                        타입 : {props.data.type ? props.data.type : "정보없음"},
                        활동성 : {props.data.activity ? props.data.activity : "정보없음"}
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>

    );
};

export default Theme_Card;