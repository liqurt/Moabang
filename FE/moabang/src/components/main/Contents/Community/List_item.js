import React from 'react';
import { ListGroup, Badge } from 'react-bootstrap';

const List_item = () => {
    return (
        <ListGroup.Item
            as="li"
            className="d-flex justify-content-between align-items-start"
        >
            <div className="ms-2 me-auto">
                <div className="fw-bold">Subheading</div>
                Cras justo odio
            </div>
            <Badge bg="primary" pill>
                14
            </Badge>
        </ListGroup.Item>
    );
};

export default List_item;