/**
 * selectX updates the selected X value in the input field and highlights the
 * corresponding X button.
 *
 * @param {number} value - The X value to be selected.
 */
let selectedX = null; // Store the currently selected X value

function selectX(value) {
    // Deselect the previously selected button (if any)
    const prevButton = document.getElementById("x_button_" + selectedX);
    if (prevButton) {
        prevButton.style.backgroundColor = "#4287f5"; // Standard color
    }

    // Select the new button and set the selectedX value
    selectedX = value;
    const button = document.getElementById("x_button_" + value);
    if (button) {
        button.style.backgroundColor = "#f00"; // New color
    }

    // Set the selected X value in the hidden input field
    const xInput = document.getElementsByName("x")[0];
    if (xInput) {
        xInput.value = value;
    }
}