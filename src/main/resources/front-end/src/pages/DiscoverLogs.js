import {Alert, Badge, Button, Container, Table} from "react-bootstrap";
import {faChevronDown, faChevronRight} from "@fortawesome/free-solid-svg-icons";
import React, {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import dayjs from "dayjs";

const BADGE_MAP = {
    'DEBUG': 'primary',
    'INFO': 'success',
    'WARN': 'warning',
    'ERROR': 'danger'
}

const DATE_FORMAT = 'DD-MM-YYYY HH:mm:ss.SSS';

function DiscoverLogs() {
    const [logs, setLogs] = useState({state: 'uninitialized', read: true, data: []});
    const [expanded, setExpanded] = useState({});
    if(logs.read){
        console.log(logs);
        let fromId = '';
        if(logs.data.length>0){
            fromId=logs.data[logs.data.length-1].id;
        }
        fetch(`/api/discover-logs/${fromId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
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
        </p>;
    }

    function mapRow(line){
        const date = new Date(line.at);
        const formattedDate = dayjs(date).format(DATE_FORMAT);
        return <tr key={line.id}>
            <td className={"log-at"}>{formattedDate}</td>
            <td><Badge bg={BADGE_MAP[line.level]||"light"}>{line.level}</Badge></td>
            {/*<td className={"log-location"}><span>{line.fileName}</span></td>*/}
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
            <td colSpan={3}>{message}</td>
        </tr>;
    }

    function handleRefresh(e){
        const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
        if (bottom && !logs.read) {
            loadMore();
        }
    }

    return <>
        <Table className={"log-discovery"} >
            <thead>
                <tr>
                    <th className={"log-at"}>at</th>
                    <th className={"log-level"}>level</th>
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

export default DiscoverLogs;