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
import "./Map.css";

class Map extends Component {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    constructor(props) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    redraw = () => {
        this.drawBackgroundImage()
    };

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        let ctx = canvas.getContext("2d");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
            this.drawPath(ctx, canvas);
        }
    }

    drawPath(ctx) {
        if (this.props.parsedCoordinates.length > 0) {
            let coordinates = this.props.parsedCoordinates.split(" ");
            let lastX = 0.0;
            let lastY = 0.0;
            ctx.strokeStyle = "red";
            ctx.lineWidth = 6.0;
            for (let i = 0; i < coordinates.length-2; i+=2) {
                ctx.beginPath();
                ctx.moveTo(parseInt(coordinates[i]), parseInt(coordinates[i+1]));
                ctx.lineTo(parseInt(coordinates[i+2]), parseInt(coordinates[i+3]));
                lastX = coordinates[i+2];
                lastY = coordinates[i+3];
                ctx.stroke();
            }
            ctx.beginPath();
            ctx.moveTo(lastX, lastY);
            ctx.moveTo(coordinates[coordinates.length-2], coordinates[coordinates.length-1]);
        } else {
            ctx.beginPath();
        }
    };

    render() {
        return (
            <canvas ref={this.canvas} width={this.props.width} height={this.props.height}/>
        )
    }
}

export default Map;