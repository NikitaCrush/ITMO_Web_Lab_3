//showNotification.js

/**
 * showNotification displays a notification message on the page and hides it
 * after a specified duration.
 *
 * @param {string} message - The notification message to display.
 */
function showNotification(message) {
    const notification = document.getElementById("notification");
    notification.innerHTML = message;
    notification.style.display = "block";

    // Hide the notification after 3 seconds (3000 milliseconds)
    setTimeout(() => {
        notification.style.display = "none";
    }, 3000);
}