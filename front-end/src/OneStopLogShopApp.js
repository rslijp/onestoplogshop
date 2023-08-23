import FooterBar from "./layout/FooterBar";
import HeaderBar from "./layout/HeaderBar";
import React from "react";
import OneStopLogPropertyLoader from "./OneStopLogPropertyLoader";

function OneStopLogShopApp() {
    return <>
        <HeaderBar/>
        <OneStopLogPropertyLoader/>
        <FooterBar/>
    </>;
}

export default OneStopLogShopApp;