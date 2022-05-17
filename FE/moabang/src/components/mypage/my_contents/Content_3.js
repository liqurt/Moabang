import React from 'react';
import { Card, Col, Badge } from 'react-bootstrap';

const Content_3 = (props) => {


    return (
        <div>
            {props.data ? <Card >
                {props.data.playDate ?
                    <Card.Body>
                        <Card.Title><Badge pill >{props.data.active}</Badge>{props.data.tname}</Card.Title>
                        <Card.Subtitle>{props.data.cname}</Card.Subtitle>
                        <Card.Text>{props.data.content}</Card.Text>
                        <Card.Footer className='card-footer'>
                            <small className="text-muted">{props.data.playDate}</small>
                        </Card.Footer>
                    </Card.Body>
                    :
                    <Card.Body>
                        <Card.Title><Badge pill >{props.data.header}</Badge>{props.data.title}</Card.Title>
                        <Card.Text>{props.data.content}</Card.Text>
                    </Card.Body>
                }
            </Card> :
                <Card.Body>
                    <Card.Title>찜한 테마가 없어요 ㅠㅠ</Card.Title>

                </Card.Body>
            }
        </div >
    );
};

export default Content_3;