import "react-datepicker/dist/react-datepicker.css";
import {Col, Form, Row} from "react-bootstrap";
import {any, func, shape, string} from "prop-types";
import DateFilter from "./DateFilter";
import React from "react";

const LEVELS = ['DEBUG', 'INFO', 'WARN', 'ERROR'];

function DiscoverSearchForm({form, setForm}) {

    return <Form>
        <Row>
            <Col>
                <Form.Group className="mb-3" controlId="formMessage">
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
            </Col>
            <Col>
                <Form.Group className="mb-3" controlId="formLogLevel">
                    <Form.Label>From</Form.Label>
                    <DateFilter value={form.startDate} limits={{min: null, max: form.endDate}}
                        onChange={value => setForm({...form, startDate: value})}/>
                    <Form.Text className="text-muted" onClick={() => setForm({...form, startDate: null})}>
                        clear
                    </Form.Text>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formLogLevel">
                    <Form.Label>Until</Form.Label>
                    <DateFilter value={form.endDate} limits={{min: form.startDate, max: Date.now()}}
                        onChange={value => setForm({...form, endDate: value})}/>
                    <Form.Text className="text-muted" onClick={() => setForm({...form, endDate: null})}>
                        clear
                    </Form.Text>
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
    setForm: func
};
export default DiscoverSearchForm;