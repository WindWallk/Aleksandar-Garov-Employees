import React from 'react';
import { FormGroup, Input } from "reactstrap";

function UploadCsv() {
    //posting the csv file to api, for data parsing
    const fileHandler = ({event}: { event: any }) => {
        const formData = new FormData();
        formData.append("file", event.target.files[0]);
        formData.append("format", "yyyy-MM-dd");
        console.log(event.target.files[0]);

        fetch("http://localhost:8080/api/csv/longest-lasting-colleagues", {
            method: "POST",
            body: formData
        })
            .then(response => response.json())
    };

    return(
        <div>
            <FormGroup>
                <Input
                    type="file"
                    name="file"
                    id="file"
                    onChange={(e) => fileHandler({event: e})}/>
            </FormGroup>
        </div>
    )
}

export default UploadCsv;