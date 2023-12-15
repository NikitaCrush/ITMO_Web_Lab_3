//drawCoordinatePlane.js


/**
 * drawCoordinatePlane draws the coordinate plane and predefined areas for the graph.
 * It also labels axes, quadrants, and provides R markers.
 */
let currentR = 2;  // Default value, update as needed

function drawCoordinatePlane(r) {
    currentR = r || currentR;  // Update R if a new value is provided
    const canvas = document.getElementById("coordinateCanvas");
    const context = canvas.getContext("2d");

    // Clear the canvas
    context.clearRect(0, 0, canvas.width, canvas.height);

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;

    // Draw the areas in each quadrant
    context.fillStyle = "blue";

    // First quadrant
    context.fillRect(centerX, centerY, 0.35 * centerX, -centerY * 0.7);

    // Third quadrant (triangle)
    context.beginPath();
    context.moveTo(centerX - 0.35 * centerX, centerY);
    context.lineTo(centerX, centerY);
    context.lineTo(centerX, centerY + 0.7 * centerY);
    context.closePath();
    context.fill();

    // Fourth quadrant (circle)
    context.beginPath();
    context.arc(centerX, centerY, 0.7 * centerX, 0, 0.5 * Math.PI);
    context.lineTo(centerX, centerY);
    context.closePath();
    context.fill();

    // Divisions marked at R, R/2, -R, -R/2
    context.fillStyle = "black";
    context.fillText("R", centerX + 0.7 * centerX, centerY - 5);
    context.fillText("R/2", centerX + 0.35 * centerX, centerY - 5);
    context.fillText("-R", centerX - 0.7 * centerX, centerY - 5);
    context.fillText("-R/2", centerX - 0.35 * centerX, centerY - 5);

    context.fillText("R", centerX + 5, centerY - 0.7 * centerY);
    context.fillText("R/2", centerX + 5, centerY - 0.35 * centerY);
    context.fillText("-R", centerX + 5, centerY + 0.7 * centerY);
    context.fillText("-R/2", centerX + 5, centerY + 0.35 * centerY);

    // Draw the coordinate axes
    context.beginPath();
    context.moveTo(centerX, 0);
    context.lineTo(centerX, canvas.height);
    context.moveTo(0, centerY);
    context.lineTo(canvas.width, centerY);
    context.strokeStyle = "black";
    context.lineWidth = 2;
    context.stroke();

    // Labels for X and Y axes
    context.font = "bold 12px Arial";
    context.fillText("X", canvas.width - 10, centerY - 5);
    context.fillText("Y", centerX + 5, 15);

    // Arrowheads for X and Y axes
    drawArrowhead(context, canvas.width, centerY, -Math.PI / -2);
    drawArrowhead(context, centerX, 0, 0);

}



/**
 * drawArrowhead draws an arrowhead at a given position with a specified angle.
 *
 * @param {CanvasRenderingContext2D} context - The canvas context to draw on.
 * @param {number} x - X-coordinate for the arrowhead.
 * @param {number} y - Y-coordinate for the arrowhead.
 * @param {number} angle - The angle at which to draw the arrowhead.
 */
function drawArrowhead(context, x, y, angle) {
    const arrowSize = 10;
    context.save();
    context.translate(x, y);
    context.rotate(angle);

    context.beginPath();
    context.moveTo(0, 0);
    context.lineTo(-arrowSize, arrowSize);
    context.lineTo(arrowSize, arrowSize);
    context.closePath();
    context.fill();

    context.restore();
}


function onCanvasClick(event) {
    const canvas = document.getElementById('coordinateCanvas');
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const radius = document.getElementById('mainForm:inputR').value;
    const scaleFactor = 100; // Adjust this factor to match your canvas size and coordinate system

    const planeX = (x - canvas.width / 2) / scaleFactor;
    const planeY = -(y - canvas.height / 2) / scaleFactor;

    // Send the coordinates and radius to the server
    sendPointToServer(planeX, planeY, radius);
}


window.onload = function() {
    drawCoordinatePlane();
    document.getElementById('coordinateCanvas').addEventListener('click', onCanvasClick);
};

function onRChange() {
    const r = parseFloat(document.getElementById('mainForm:inputR').value);
    drawCoordinatePlane(r);
}

// Additional function to draw a point based on the server response
function drawPoint(x, y, hit) {
    const canvas = document.getElementById('coordinateCanvas');
    const context = canvas.getContext('2d');
    const scaleFactor = 100; // This should match the scaling factor used in onCanvasClick

    const canvasX = x * scaleFactor + canvas.width / 2;
    const canvasY = -y * scaleFactor + canvas.height / 2;

    context.beginPath();
    context.arc(canvasX, canvasY, 3, 0, 2 * Math.PI, false);
    context.fillStyle = hit ? 'green' : 'red';
    context.fill();
}
function sendPointToServer(x, y, r) {
    document.getElementById('mainForm:inputR').value = r;
    document.getElementById('mainForm:submitPoint').click();
}