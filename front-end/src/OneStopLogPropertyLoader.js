import DiscoverLogs from "./pages/DiscoverLogs";
import FooterBar from "./layout/FooterBar";
import HeaderBar from "./layout/HeaderBar";
import React, {useState} from "react";
import {csrfToken} from "./utils/Cookies";

function OneStopLogPropertyLoader() {
    const [properties, setProperties] = useState( null);
    if(!properties){

        fetch(`/api/properties`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken(),
                'Accept': 'application/json, text/javascript'
            },
             redirect: "follow",
            referrerPolicy: "no-referrer"
        }).
        then(r=>r.json()).
        then(r=>setProperties(r)).
        catch((r)=>{
            console.log(r);
            setProperties(null);
        });
        return <i>Loadig settings</i>
    }

    return <DiscoverLogs additionalColumns={properties.additionalColumns} additionalFilter={properties.additionalFilter}/>;
}

export default OneStopLogPropertyLoader;