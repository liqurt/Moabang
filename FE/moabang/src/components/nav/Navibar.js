import React, { useState } from 'react';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import { getCookie, removeCookie } from '../utils/Cookie';
import { Navigate } from "react-router-dom";

const Navibar = () => {
    const [auth, setAuth] = useState("false");
    const handleLogoutClick = () => {
        console.log(auth)
        // if (auth == "true") {
        //     setAuth("false");
        // }
        if (getCookie('myToken')) {
            removeCookie('myToken')
            console.log("쿠키에 토큰 삭제")
            document.location.href = '/'
        }
    };
    const handleLoginClick = () => {
        console.log(auth)
        if (auth == "false") {
            setAuth("true");
        }
    };

    return (
        <Navbar className='navbar' bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/">모아방</Navbar.Brand>
                <Nav className="justify-content-end">
                    <Nav.Link href="/home">홈</Nav.Link>
                    <Nav.Link href="#features">테마</Nav.Link>
                    <Nav.Link href="#pricing">카페</Nav.Link>
                    <Nav.Link href="#pricing">커뮤니티</Nav.Link>
                </Nav>
                {getCookie('myToken') ?
                    <Button variant="secondary" onClick={handleLogoutClick}>Logout</Button>
                    :
                    <div></div>
                }
            </Container>
        </Navbar>
    );
};

export default Navibar;