import React from 'react';
import { Card, Badge } from 'react-bootstrap';
import './Theme_Card.css'
import logo from '../../../../image/Main_logo.png';
import activity_1 from '../../../../image/activity_1.png';
import activity_2 from '../../../../image/activity_2.png';
import difficulty_on from '../../../../image/difficulty_on.png';
import difficulty_off from '../../../../image/difficulty_off.png';
import genre from '../../../../image/genre.png';
import rplayer from '../../../../image/rplayer.png';
import time from '../../../../image/time.png';
import type_1 from '../../../../image/type.png';

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
                <hr />
                <Card.Subtitle className='pop-theme-card-subtitle'>{props.data.cname}</Card.Subtitle>
                <Card.Text className='pop-theme-card-textbox' >
                    <br />
                    <Badge className='badge' bg="dark">
                        {props.data.genre}
                    </Badge>
                    {' '}
                    <Badge className='badge' bg="warning" text="dark">
                        {props.data.time}
                    </Badge>
                    <br />
                    <Badge className='badge' bg="danger">
                        난이도 : {props.data.difficulty ? props.data.difficulty : "정보없음"}
                    </Badge>
                    {' '}
                    <Badge className='badge' bg="secondary">
                        타입 : {props.data.type ? props.data.type : "정보없음"}
                    </Badge>
                    <br />
                    <Badge className='badge' bg="success">
                        인원 : {props.data.rplayer ? props.data.rplayer : "정보없음"}명
                    </Badge>
                    {' '}
                    <Badge className='badge' bg="light" text="dark">
                        활동성 {props.data.activity ? props.data.activity : "정보없음"}
                    </Badge>
                </Card.Text>
            </Card.Body>
        </Card>

    );
};

export default Theme_Card;