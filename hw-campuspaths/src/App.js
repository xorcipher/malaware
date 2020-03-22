/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import Map from "./Map";
import BuildingSelection from "./BuildingSelection";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            buildings: "",
            parsedCoordinates: []
        };
    }

    refreshTextArea = (val) => {
        this.setState({
            buildings: val,
            parsedCoordinates: []
        })
    };

    updateBuildings = (event) => {
        this.setState({
            buildings: event.target.value,
        })
    };

    processPath = (text) => {
        if (text === "100") {
            alert("Buildings entered do not exist!")
            text = "";
        } else if (text === "200") {
            alert("Buildings entered are null!");
            text = "";
        } else if (text === "300") {
            alert("No path found!");
            text = "";
        }
        text = text.trim();
        this.setState({
            parsedCoordinates: text
        })
    };

    render() {
        return (
            <div>
                <p id="app-title">Campus Path Finder!</p>
                <BuildingSelection refreshTextArea={this.refreshTextArea} processPath={this.processPath} value={this.state.buildings} onChange={this.updateBuildings}/>
                <Map parsedCoordinates={this.state.parsedCoordinates}/>
                <p id="building-list-title">Building List: </p>
                <p> BAG=Bagley Hall (East Entrance) | BAG (NE)=Bagley Hall (Northeast Entrance) | BGR=By George | CHL=Chemistry Library (West Entrance) | CHL (NE)=Chemistry Library (Northeast Entrance)</p>
                <p> CHL (SE)=Chemistry Library (Southeast Entrance) | CMU=Communications Building | CSE=Paul G. Allen Center for Computer Science & Engineering | DEN=Denny Hall | EEB=Electrical Engineering Building (North Entrance)</p>
                <p>EEB (S)=Electrical Engineering Building (South Entrance) | FSH=Fishery Sciences Building | GWN=Gowen Hall | HUB=Student Union Building (Main Entrance) | HUB (Food | S)=Student Union Building (South Food Entrance)</p>
                <p>HUB (Food | W)=Student Union Building (West Food Entrance) | IMA=Intramural Activities Building | KNE=Kane Hall (North Entrance) | KNE (E)=Kane Hall (East Entrance) | KNE (S)=Kane Hall (South Entrance)</p>
                <p>KNE (SE)=Kane Hall (Southeast Entrance) | KNE (SW)=Kane Hall (Southwest Entrance) | LOW=Loew Hall | MCC=McCarty Hall (Main Entrance) | MCC (S)=McCarty Hall (South Entrance)</p>
                <p>MCM=McMahon Hall (Northwest Entrance) | MCM (SW)=McMahon Hall (Southwest Entrance) | MGH=Mary Gates Hall (North Entrance) | MGH (E)=Mary Gates Hall (East Entrance) | MGH (S)=Mary Gates Hall (South Entrance)</p>
                <p>MGH (SW)=Mary Gates Hall (Southwest Entrance) | MLR=Miller Hall | MNY=Meany Hall (Northeast Entrance) | MNY (NW)=Meany Hall (Northwest Entrance) | MOR=Moore Hall | MUS=Music Building (Northwest Entrance)</p>
                <p>MUS (E)=Music Building (East Entrance) | MUS (S)=Music Building (South Entrance) | MUS (SW)=Music Building (Southwest Entrance) | OUG=Odegaard Undergraduate Library | PAA=Physics/Astronomy Building A</p>
                <p>PAB=Physics/Astronomy Building | PAR=Parrington Hall | RAI=Raitt Hall (West Entrance) | RAI (E)=Raitt Hall (East Entrance) | ROB=Roberts Hall | SAV=Savery Hall | SUZ=Suzzallo Library | T65=Thai 65</p>
                <p>UBS=University Bookstore | UBS (Secret)=University Bookstore (Secret Entrance)</p>
            </div>
        );
    }

}

export default App;
