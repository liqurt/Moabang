import React from 'react';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';

const Navibar = () => {
    const handleLogoutClick = () => {
        if (localStorage.getItem('myToken')) {
            localStorage.removeItem('myToken');
            localStorage.removeItem('username');
            localStorage.removeItem('email');
            localStorage.removeItem('p_img');
            console.log("쿠키에 토큰 삭제");
            document.location.href = '/';
        }
    };

    return (
        <Navbar className='navbar' bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/">모아방</Navbar.Brand>
                <Nav className="justify-content-end">
                    {localStorage.getItem('myToken') ?
                        <div className='username'>
                            {localStorage.getItem('username')}님 안녕하세요 <Button variant="secondary" onClick={handleLogoutClick}>Logout</Button>
                        </div>
                        :
                        <Button hidden={true} ></Button>
                    }
                    <Nav.Link href="/home">홈</Nav.Link>
                    <Nav.Link href="#features">테마</Nav.Link>
                    <Nav.Link href="#pricing">카페</Nav.Link>
                    <Nav.Link href="#pricing">커뮤니티</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
};

export default Navibar;