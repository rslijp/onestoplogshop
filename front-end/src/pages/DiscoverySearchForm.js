import "react-datepicker/dist/react-datepicker.css";
import {Col, Form, Row} from "react-bootstrap";
import React, {useState} from "react";
import {any, arrayOf, func, shape, string} from "prop-types";
import DateFilter from "./DateFilter";
import {csrfToken} from "../utils/Cookies";

const LEVELS = ['DEBUG', 'INFO', 'WARN', 'ERROR'];

function DiscoverSearchForm({form, setForm, additionalFilter}) {
    const [filterLists, setFilterLists] = useState({});

    function loadFilter(field){
        fetch(`/api/property-values/${field}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken(),
                'Accept': 'application/json, text/javascript'
            },
            referrerPolicy: "no-referrer"
        }).then(r => r.json()).then(r => {
            let copy = {...filterLists};
            copy[field] = r;
            setFilterLists(copy);
        }).catch((r) => {
            console.log(r);
        });
    }

    additionalFilter.forEach(f=>{
        if(!filterLists[f.field]) {
            loadFilter(f.field);
        }
    });

    function mapAdditional(field, name){
        return <Form.Group className="mb-3" key={"form-"+field} controlId={"form-"+field}>
            <Form.Label>{name}</Form.Label>
            <Form.Select value={form.properties[field] || ''}  type="level"
                disabled={!filterLists[field]}
                onChange={(e) => {
                    let copy = {...form};
                    let value = e.target.value;
                    if(value === '') value=null;
                    copy.properties={...form.properties};
                    copy.properties[field]=value;
                    setForm(copy);
                }}>
                <option value={''}>-</option>
                {form.properties[field]?<option key={"selected"} value={form.properties[field]}>{form.properties[field]}</option>:null}
                {(filterLists[field]||[]).filter(v=>v!==form[field]).map(p => <option value={p} key={p}>{p}</option>)}
            </Form.Select>
        </Form.Group>;
    }

    return <Form className={"filters"}>
        <Row>
            <Col>
                <Form.Group className="mb-3 message" controlId="formMessage">
                    <Form.Label>Messages containing</Form.Label>
                    <Form.Control type="text" placeholder="Enter keywords" value={form.message || ''}
                        autoComplete={"none"}
                        autoCorrect={"none"}
                        onChange={(e) => {
                            setForm({...form, message: e.target.value});
                        }}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formLogLevel">
                    <Form.Label>Log level</Form.Label>
                    <Form.Select value={form.level} defaultValue={null} type="level" onChange={(e) => {
                        setForm({...form, level: e.target.value});
                    }}>
                        <option value={''}>-</option>
                        {LEVELS.map(p => <option value={p} key={p}>{p}</option>)}
                    </Form.Select>
                    <Form.Text className="text-muted">
                        Or more severe
                    </Form.Text>
                </Form.Group>
                {additionalFilter.filter((f,i)=>i%2===0).map(f=>mapAdditional(f.field,f.field))}
            </Col>
            <Col>
                <Form.Group className="mb-3" controlId="formLogLevel">
                    <Form.Label>From</Form.Label>
                    <DateFilter value={form.startDate} limits={{min: null, max: form.endDate}}
                        onChange={value => setForm({...form, startDate: value})}/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formLogLevel">
                    <Form.Label>Until</Form.Label>
                    <DateFilter value={form.endDate} limits={{min: form.startDate, max: Date.now()}}
                        onChange={value => setForm({...form, endDate: value})}/>
                    {additionalFilter.filter((f,i)=>i%2===1).map(f=>mapAdditional(f.field,f.field))}
                </Form.Group>
            </Col>
        </Row>
    </Form>;
}
DiscoverSearchForm.propTypes = {
    form: shape({
        message: string,
        level: string,
        fromDate: any,
        toDate: any
    }),
    additionalFilter:arrayOf(shape({
        name: string,
        field: string
    })),
    setForm: func
};
export default DiscoverSearchForm;