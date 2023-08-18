import {Button, Nav, Navbar} from "react-bootstrap";
import React, {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {csrfToken} from "../utils/Cookies";
import {faRefresh} from "@fortawesome/free-solid-svg-icons";
function HeaderBar() {
    const [user, setUser] = useState({state: 'uninitialized', data: null});
    if(user.state ===  'uninitialized'){
        fetch("/api/oidc-user", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken()
            },
            redirect: "follow", // manual, *follow, error
            referrerPolicy: "no-referrer"
        }).
            then(r=>r.json()).
            then(r=>setUser({state: 'loaded', data: r})).
            catch((r)=>{
                console.log(r);
                setUser({state: 'error', data: null});
            });
        setUser({state: 'loading', data: null});
    }
    return <Navbar sticky="top"
        collapseOnSelect expand="md"
        bg="dark"
        variant="dark"
        className={"navbar-expand"}>
        <Navbar.Brand onClick={() => document.location.hash="/"}>
            <img src="/content/logo.png"
                className={"d-inline-block align-top headerLogo"}
                alt={"Powered by SoftCause"}
            />
        </Navbar.Brand>
        <div style={{"flex": "auto"}}>
            <Navbar.Text><span className={"headerText"}>One Stop Log Shop</span></Navbar.Text>
        </div>
        <Nav className="justify-content-end"
            style={{"flex": "max-content"}}>
            <Button variant={"link"} onClick={()=>document.location.reload()}><FontAwesomeIcon icon={faRefresh}></FontAwesomeIcon></Button>
            {user.data?<>
                <Navbar.Text className={"headerUserName"}>{user.data.name||""}</Navbar.Text>
            </>:null
            }
        </Nav>
    </Navbar>;
}

export default HeaderBar;