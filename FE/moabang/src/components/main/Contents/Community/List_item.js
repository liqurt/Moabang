import React from 'react';
import { ListGroup, Badge } from 'react-bootstrap';
import './List_item.css'

const List_item = (props) => {
    return (
        <ListGroup.Item
            as="li"
            className="d-flex justify-content-between align-items-start"
        >
            <div className="ms-2 me-auto">
                <div className="fw-bold">
                    <p className='header'>{props.data.header}</p>
                    <p className='title'>{props.data.title}</p>
                </div>
            </div>
            <Badge bg="primary" pill>
                new
            </Badge>
        </ListGroup.Item>
    );
};

export default List_item;