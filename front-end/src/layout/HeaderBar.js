import {Button, Nav, Navbar, OverlayTrigger, Tooltip} from "react-bootstrap";
import React, {useState} from "react";
import {faRefresh, faWeightScale} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {csrfToken} from "../utils/Cookies";
function HeaderBar() {
    const [user, setUser] = useState({state: 'uninitialized', data: null});
    const [stats, setStats] = useState(null);
    if(!stats){
        fetch(`/api/statistics`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken(),
                'Accept': 'application/json, text/javascript'
            },
            referrerPolicy: "no-referrer"
        }).then(r => r.json()).then(r => {
            setStats(r);
        }).catch((r) => {
            console.log(r);
        });
    }
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

    function formatSize(k,s){
        if(k.indexOf("Size")>0){
            let postFix = "b";
            if(s>1024){
                postFix = "kb";
                s=Math.round(s/1024);
                if(s>1024){
                    postFix = "mb";
                    s=Math.round(s/1024);
                }
            }
            return s+postFix;
        }
        return s;
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
        <div style={{"flex": "fit"}}>
            <Navbar.Text><span className={"headerText"}>One Stop Log Shop</span></Navbar.Text>
        </div>
        <Nav className="justify-content-start"
            style={{"flex": "max-content"}}>
            <OverlayTrigger
                placement={'bottom'}
                overlay={
                    <Tooltip id={`tooltip-bottom`}>
                        Database <strong>statistics</strong>.
                        {stats?
                            <table className="table">
                                <thead>
                                    <tr>
                                        <th scope="col">name</th>
                                        <th scope="col">size</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {Object.keys(stats).map((k)=>
                                        <tr key={k}>
                                            <th scope="row" style={{"textAlign": "left"}}>{k}</th>
                                            <td>{formatSize(k, stats[k])}</td>
                                        </tr>)}
                                </tbody>
                            </table>
                            : null}
                    </Tooltip>
                }
            >
                <Button variant="link"><FontAwesomeIcon icon={faWeightScale}></FontAwesomeIcon></Button>
            </OverlayTrigger>
            <Button variant={"link"} onClick={()=>document.location.reload()}><FontAwesomeIcon icon={faRefresh}></FontAwesomeIcon></Button>
            {user.data?<>
                <Navbar.Text className={"headerUserName"}>{user.data.name||""}</Navbar.Text>
            </>:null
            }
        </Nav>
    </Navbar>;
}

export default HeaderBar;