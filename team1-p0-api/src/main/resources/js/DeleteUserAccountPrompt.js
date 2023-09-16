function deleteAcct() {
    document.getElementById("confirmDeletion").addEventListener("click", function(event) {
        event.preventDefault();

        fetch("/account", {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        })
        .then(response => {
            if (response.ok) {
                performLogout();
            } else {
                // Handle errors for the DELETE request
            }
        })
        .catch (error => {
            // Handle network errors here.
        });
    })
}

function performLogout() {
    fetch("/logout", {
        method: "GET"
    })
    .then(logoutResponse => {
        if (logoutResponse.ok) {
        window.location.href = '/logoutRedirect';
        } else {
            // Handle errors for the get request.
        }
    })
    .catch(error => {
        // Handle network errors here.
    })
}

deleteAcct();