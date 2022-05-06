import React from 'react';
import './Community.css'
import { Tabs, Tab } from 'react-bootstrap';

const Community = () => {
    return (
        <div>
            <Tabs
                defaultActiveKey="home"
                transition={false}
                id="noanim-tab-example"
                className="mb-3"
            >
                <Tab eventKey="home" title="Home">
                </Tab>
                <Tab eventKey="profile" title="Profile">
                </Tab>
                <Tab eventKey="contact" title="Contact" disabled>
                </Tab>
            </Tabs>
        </div>
    );
};

export default Community;