import {Alert, Badge, Button, Container, Form, Table} from "react-bootstrap";
import React, {useState} from "react";

const LEVELS = ['DEBUG','INFO','WARN', 'ERROR'];

const DATE_FORMAT = 'DD-MM-YYYY HH:mm:ss.SSS';

function DiscoverSearchForm({form, setForm}) {

    return <Form>
        <Form.Group className="mb-3" controlId="formMessage">
            <Form.Label>Messages containing</Form.Label>
            <Form.Control type="text" placeholder="Enter keywords" value={form.message||''}
                          autoComplete={"none"}
                          autoCorrect={"none"}
                          onChange={(e)=>{
                              setForm({...form, message: e.target.value});
                          }}
            />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formLogLevel">
            <Form.Label>Log level</Form.Label>
            <Form.Select value={form.level} defaultValue={null} type="level" onChange={(e)=>{
                console.log(e.target.value);
                setForm({...form, level: e.target.value});
            }}>
                <option value={''}>-</option>
                {LEVELS.map(p=><option  value={p} key={p}>{p}</option>)}
            </Form.Select>
            <Form.Text className="text-muted">
                Or more severe
            </Form.Text>

        </Form.Group>
    </Form>;
}

export default DiscoverSearchForm;