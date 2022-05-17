import React, { useState } from 'react';
import { Navbar, Nav, Container, Button, NavDropdown } from 'react-bootstrap';
import './Navibar.css'

const Navibar = () => {

    const handleLogoutClick = () => {
        if (localStorage.getItem('myToken')) {
            localStorage.removeItem('myToken');
            localStorage.removeItem('username');
            localStorage.removeItem('email');
            localStorage.removeItem('p_img');
            console.log("토큰 삭제");
            document.location.href = '/';
        }
    };


    return (
        <Navbar className='navbar' bg="dark" variant="dark">
            <Container>
                <Navbar.Brand className='brand' href="/">모아방</Navbar.Brand>
                <Nav className="justify-content-end">
                    {localStorage.getItem('myToken') ?
                        <Navbar.Collapse className='justify-content-center'>
                            <Container>
                                <Navbar.Text className='username'>
                                    <a href='/mypage'>{localStorage.getItem('username')}</a>
                                    님 안녕하세요
                                </Navbar.Text>
                            </Container>
                            <Nav.Link className='logout' bg="secondary" onClick={handleLogoutClick}>Logout</Nav.Link>
                        </Navbar.Collapse>
                        :
                        <Button hidden={true} ></Button>
                    }
                    <Nav.Link href="/home">홈</Nav.Link>
                    <Nav.Link href="/theme">테마</Nav.Link>
                    <Nav.Link href="/cafe">카페</Nav.Link>
                    <Nav.Link href="/board">커뮤니티</Nav.Link>
                    <Nav.Link href="/compare">비교하기</Nav.Link>
                    <Nav.Link href="https://drive.google.com/uc?export=download&id=12Vb7eHOqJiGvC90cWkBu9ycjzidQubfC&confirm=t">앱 다운로드</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
};

export default Navibar;