import {Nav, Navbar} from "react-bootstrap";
import React from 'react';

function FooterBar() {

    return (
        <Navbar sticky="bottom" collapseOnSelect expand="md" bg="light" variant="light">
            <Nav className="justify-content-center" style={{"width":"100%"}}>
                SoftCause (c) 2023
            </Nav>
        </Navbar>
    );
}

export default FooterBar;