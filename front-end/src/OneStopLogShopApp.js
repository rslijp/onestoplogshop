import FooterBar from "./layout/FooterBar";
import HeaderBar from "./layout/HeaderBar";
import OneStopLogPropertyLoader from "./OneStopLogPropertyLoader";
import React from "react";

function OneStopLogShopApp() {
    return <>
        <HeaderBar/>
        <OneStopLogPropertyLoader/>
        <FooterBar/>
    </>;
}

export default OneStopLogShopApp;