/*
 * Copyright ©2019 Dan Grossman.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

/**
 * Props:
 *
 * onChange - a listener for when the size text area has a keyboard event
 * value - the value to display in the text area
 */
class BuildingSelection extends Component {
    render() {
        return (
            <div id="building-selection">
                Enter any 2 abbreviated or full building names below, separated by one line. <br/>
                Press clear to empty the textbox and remove any path displayed on the map. <br/>
                For a list of all valid buildings, please see the bottom of the page. <br/>
                <textarea
                    rows={5}
                    cols={30}
                    value={this.props.value}
                    onChange={this.props.onChange}
                /> <br/>
                <button onClick={() => this.makeRequestLong(this.props.value)}>Show the path!</button>
                <button onClick={() => {this.props.refreshTextArea("")}}>Clear</button>
            </div>
        );
    }

    makeRequestLong = async (buildings) => {  // <- Syntax for making async arrow functions.
        let splitBuildings = buildings.split("\n");
        if (splitBuildings.length !== 2) {
            alert("Incorrect building format!\nFor reference, the correct format for buildings is:\nBUILDING1\nBUILDING2")
            return;
        }
        try {
            let lineBreaks = (buildings.match(/\n/g) || []).length;
            if (lineBreaks !== 1) {
                alert("Incorrect building format!\nFor reference, the correct format for buildings is:\nBUILDING1\nBUILDING2")
                return;
            }
            let building1 = splitBuildings[0];
            let building2 = splitBuildings[1];

            let url = "http://localhost:4567/find-path?building1=" + building1 + "&building2=" + building2;
            let responsePromise = fetch(url);
            let response = await responsePromise;

            // Now that we have a response, we should check the status code, make sure it's OK=200:
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            let textPromise = response.text();

            // The text() function actually returns a Promise<string>, so we need to wait for
            // it to resolve before we can have the string.
            let text = await textPromise;
            //alert(text);
            // Now that we have the string, let's stick it in state so it'll be displayed
            // to the user.
            this.props.processPath(text);
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);  // Logging the error can be nice for debugging.
        }
    };
}

export default BuildingSelection;
