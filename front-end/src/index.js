import 'bootstrap/dist/css/bootstrap.min.css';
import './css/main.css';
import OneStopLogShopApp from "./OneStopLogShopApp";
import React from "react";
import { createRoot } from "react-dom/client";

const container = document.getElementById("root");
const root = createRoot(container);
root.render(<>
    <OneStopLogShopApp/>
</>);