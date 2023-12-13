//validate.js

/**
 * validate checks the form inputs for validity and displays error notifications.
 *
 * @returns {boolean} - True if the form inputs are valid; otherwise, false.
 */
function validate() {
    let isValid = true;

    const x = document.getElementById("hiddenX").value;
    const y = document.getElementById("checkForm:y").value;
    const r = document.getElementById("checkForm:r").value;

    if (x === "") {
        showNotification("Please select an X value.");
        isValid = false;
    }
    if (isNaN(y) || y < -5 || y > 3) {
        showNotification("Y value must be between -5 and 3.");
        isValid = false;
    }
    if (isNaN(r) || r < 2 || r > 5) {
        showNotification("R value must be between 2 and 5.");
        isValid = false;
    }

    return isValid;
}
