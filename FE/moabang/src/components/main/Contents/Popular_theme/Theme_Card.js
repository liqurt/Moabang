import React from 'react';
import { Card, Button } from 'react-bootstrap';
import './Theme_Card.css'
import logo from '../../../../image/Main_logo.png';

const Theme_Card = (props) => {
    const onErrorImg = (e) => {
        e.target.src = logo;
        // e.target.className = 'pop-theme-card-img-default';
    }
    return (
        <Card className='pop-theme-card' >
            <Card.Img className='pop-theme-card-img' variant="top" src={props.data.img} onError={onErrorImg} />
            <Card.Body className='pop-theme-card-body'>
                <hr />
                <Card.Title className='pop-theme-card-title'>{props.data.tname}</Card.Title>
                <Card.Subtitle className='pop-theme-card-subtitle'>{props.data.cname}</Card.Subtitle>
                <Card.Text >
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

    );
};

export default Theme_Card;