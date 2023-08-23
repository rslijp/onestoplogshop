import {Alert, Badge, Button, Container, Table} from "react-bootstrap";
import React, {useState} from "react";
import {arrayOf, shape, string} from "prop-types";
import {faChevronDown, faChevronRight} from "@fortawesome/free-solid-svg-icons";
import DiscoverSearchForm from "./DiscoverySearchForm";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {csrfToken} from "../utils/Cookies";
import dayjs from "dayjs";

const BADGE_MAP = {
    'DEBUG': 'primary',
    'INFO': 'success',
    'WARN': 'warning',
    'ERROR': 'danger'
};

const DATE_FORMAT = 'DD-MM-YYYY HH:mm:ss.SSS';

function DiscoverLogs({additionalColumns, additionalFilter}) {
    const [logs, setLogs] = useState({state: 'uninitialized', form: {properties: {}}, read: true, data: []});
    const [expanded, setExpanded] = useState({});
    if(logs.read){
        let fromId = '';
        if(logs.data.length>0){
            fromId=logs.data[logs.data.length-1].id;
        }
        fetch(`/api/discover-logs/${fromId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken(),
                'Accept': 'application/json, text/javascript'
            },
            body: JSON.stringify(logs.form),
            redirect: "follow",
            referrerPolicy: "no-referrer"
        }).
            then(r=>r.json()).
            then(r=>setLogs({...logs, state: 'loaded', read: false, data: logs.data.concat(r)})).
            catch((r)=>{
                console.log(r);
                setLogs({...logs, read: false, state: 'error'});
            });
        setLogs({...logs, state: 'loading', read: false});
    }

    function loadMore(){
        setLogs({...logs, read: true});
    }
    function toggleExpansion(id){
        const expand = !expanded[id];
        const copy  = {...expanded};
        copy[id]=expand;
        setExpanded(copy);
    }

    function expandedRow(line){
        const date = new Date(line.at);
        const formattedDate = dayjs(date).format(DATE_FORMAT);
        return <p className={"detail"}>
            <h5>At</h5>
            {formattedDate}<br/>
            <h5>Level</h5>
            {line.level}<br/>
            <h5>Message</h5>
            {line.message}<br/>
            <h5>Thread</h5>
            {line.threadName}<br/>
            <h5>Location</h5>
            {line.fileName}:{line.lineNumber}<br/>
            <h5>Class</h5>
            {line.className}.{line.methodName}<br/>
            <h5>Stacktrace</h5>
            {line.stackTrace||"-"}<br/>
            {line.properties && Object.keys(line.properties).length>0 ?
                <table>
                    <thead>
                        <tr>
                            <th className={"log-property-key"}>Key</th>
                            <th className={"log-property-value"}>Value</th>
                        </tr>
                    </thead>
                    <tbody>
                        {Object.keys(line.properties).map(k=>
                            <tr key={k}><td>{k}</td><td>{line.properties[k]||"-"}</td></tr>
                        )}
                    </tbody>
                </table> :
                null}
        </p>;
    }

    function mapRow(line){
        const date = new Date(line.at);
        const formattedDate = dayjs(date).format(DATE_FORMAT);
        return <tr key={line.id}>
            <td className={"log-at"}>{formattedDate}</td>
            <td><Badge bg={BADGE_MAP[line.level]||"light"}>{line.level}</Badge></td>
            {additionalColumns.map(c=><td key={line.id+"-"+c.field}>{line.properties[c.field]}</td>)}
            <td style={{"cursor": "pointer"}} onClick={()=>toggleExpansion(line.id)}>{expanded[line.id]?expandedRow(line):line.message}</td>
            <td style={{"cursor": "pointer"}} onClick={()=>toggleExpansion(line.id)}>{   expanded[line.id] ?
                <FontAwesomeIcon icon={faChevronDown} fixedWidth title={"collapse"}/>:
                <FontAwesomeIcon icon={faChevronRight} fixedWidth title={"expand"}/>
            }
            </td>
        </tr>;
    }

    function messageRow(message){
        return <tr>
            <td colSpan={3+additionalColumns.length}>{message}</td>
        </tr>;
    }

    // function handleRefresh(e){
    //     const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
    //     if (bottom && !logs.read) {
    //         loadMore();
    //     }
    // }

    return <>
        <Container><DiscoverSearchForm form={logs.form}
            setForm={(data)=>{setLogs({state: 'uninitialized', form: data, read: true, data: []});}}
            additionalFilter={additionalFilter}
        /></Container>
        <Table className={"log-discovery"} >
            <thead>
                <tr>
                    <th className={"log-at"}>at</th>
                    <th className={"log-level"}>level</th>
                    {additionalColumns.map(c=><th key={c.field} className={"log-"+c.field}>{c.name}</th>)}
                    {/*<th className={"log-location"}>location</th>*/}
                    <th className={"log-message"}>message</th>
                    <th className={"log-action"}>{" "}</th>
                </tr>
            </thead>
            <tbody>
                {logs.data.length===0?messageRow("No results"):null}
                {logs.data.map((l)=>mapRow(l))}
                {logs.state==='loading' || logs.state==='unitialized'?messageRow("Loading"):null}
            </tbody>
        </Table>
        <Container>
            {logs.state==='loaded'?<Button onClick={loadMore} variant={"secondary"}>Load more</Button>:null}
            {logs.state==='error'?<Alert variant={"danger"} dismissible={false}>Something went wrong</Alert>:null}
        </Container>
    </>;
}
DiscoverLogs.propTypes = {
    additionalColumns:arrayOf(shape({
        name: string,
        field: string
    })),
    additionalFilter:arrayOf(shape({
        name: string,
        field: string
    }))
};
export default DiscoverLogs;