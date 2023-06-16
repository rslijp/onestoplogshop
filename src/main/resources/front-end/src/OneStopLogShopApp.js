import {Container} from "react-bootstrap";
import DiscoverLogs from "./pages/DiscoverLogs";
import FooterBar from "./layout/FooterBar";
import HeaderBar from "./layout/HeaderBar";
import React from "react";

function OneStopLogShopApp() {
    return <>
        <HeaderBar/>
        <DiscoverLogs/>
        <FooterBar/>
    </>;
}

export default OneStopLogShopApp;