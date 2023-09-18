/* toypull-from-json:
    Extends HTML to implement JSON decoding associated with the pull handler defined in GatchaController.java.
 */
function pull() {
const content = {};
// Fetch data from the server.
// Initiate an HTTP GET request to the toybox/pull endpoint. It expects to receive JSON data in response.
fetch('/toybox/pull', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    body: JSON.stringify(content)
})
.then((response) => {
    console.log('POST Response.status: ', response.status);

    if (response.status === 200) {
        return response.json();
    } else {
        throw new Error('Request failed with status: ' + response.status);
    }
})
.then((jsonData) => {
    // Handle successful JSON response here
})
.catch((error) => {
    console.error("An error occurred:", error);
});
}