import {Button, Dropdown, Form, FormControl, InputGroup} from "react-bootstrap";
import React, {useState} from 'react';
import {any, func, shape} from "prop-types";
import {faAngleDown,faAngleUp} from "@fortawesome/free-solid-svg-icons";
import DatePicker from "react-datepicker";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import moment from "moment";

function DateFilter({value, limits, onChange}) {
    const [expanded, setExpanded] = useState(false);
    const pickerFormat = 'dd-MM-yyyy hh:mm:ss';
    const format = 'DD[-]MM[-]YYYY[ ]HH[:]mm';
    const formatted = value?moment(value).format(format):'-';
    const range = {
        selectsStart: !!limits['max'],
        startDate: limits['min']||value,
        selectsEnd: !!limits['min'],
        endDate: limits['max']||value
    };
    const onDateChange = (date)=>{
        onChange(date);
    };

    const itemContent = expanded?
        <div className={"react-datepicker-wrapper"}><DatePicker
            inline
            onChange={onDateChange}
            dateFormat={pickerFormat}
            selected={value}
            selectsStart={range.selectsStart}
            startDate={range.startDate}
            selectsEnd={range.selectsEnd}
            endDate={range.endDate}
            showTimeSelect={true}
            timeIntervals={5}
            showYearDropdown
            showMonthDropdown
        /></div> :null;

    return (<><Dropdown.Item as={"span"}>
        <InputGroup className="mb-3" onClick={()=>setExpanded(!expanded)}>
            <FormControl
                style={{cursor: 'pointer'}}
                type={"text"}
                readOnly={true}
                value={formatted}
            />

            <Button><FontAwesomeIcon icon={expanded?faAngleUp:faAngleDown}/></Button>
        </InputGroup>
        {itemContent}
    </Dropdown.Item>
    <Form.Text className="text-muted" onClick={() => {setExpanded(false);onDateChange(null);}}>
            clear
    </Form.Text></>);
}
DateFilter.propTypes = {
    value: any,
    limits: shape({
        min: any,
        max: any
    }),
    onChange: func
};
export default DateFilter;