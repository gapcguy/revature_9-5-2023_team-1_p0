function getUsername() {
    fetch("/session-data")
        .then(response => response.json())
        .then(jsonData => {
            const userNameElement = document.getElementById("username");
            userNameElement.textContent = jsonData.username;
        })
        .catch(error => {
            console.error("Error fetching username:", error);
        });
}

function getToys() {
fetch("/toybox/myToys")
    .then(response => response.json())
    .then(jsonData => {
        const toyListContainer = document.getElementById("toyList");

         toyListContainer.innerHTML = "";

        for (const item of jsonData) {
            // Create a list item (<li>) for each item in the JSON data
            const listItem = document.createElement("li");
            listItem.style.color="#ffffff";

            // Create a heading element (<h2>) for the toy name
            const toyNameHeading = document.createElement("span");
            toyNameHeading.textContent = item.toyName;
            toyNameHeading.style.color="#ffffff";

            // Create a paragraph element (<p>) for the quantity
            const quantityParagraph = document.createElement("span");
            quantityParagraph.textContent = ` Quantity: ${item.quantity}`;
            quantityParagraph.style.color="#ffffff";

            // Append the heading and paragraph to the list item
            listItem.appendChild(toyNameHeading);
            listItem.appendChild(quantityParagraph);

            // Append the list item to the container
            toyListContainer.appendChild(listItem);
        }
    })
    .catch(error => {
        console.error("Error fetching data:", error);
    });
}
function getBalance() {
    fetch("/session-data")
        .then(response => response.json())
        .then(jsonData => {
            const userBalance = document.getElementById("balance");
            userBalance.textContent = jsonData.coin_balance;
        })
        .catch(error => {
            console.error("Error fetching balance:", error);
        });
}

function updateBalanceOnDashboard() {
    // Get the values of the HTML elements that store the current balance and what we are looking to add to it.
    const currentBalance = document.getElementById("balance");
    const addToBalance = document.getElementById("amount").value;

    // Ensure they're integers
    const currentBalanceValue = parseInt(currentBalance.textContent, 10);
    const addToBalanceValue = parseInt(addToBalance, 10);

    // Add the two and store it in newBalance.
    const newBalance = currentBalanceValue + addToBalanceValue;

    // Display it.
    currentBalance.textContent = newBalance;


}
function pullToy() {
    fetch("http://localhost:8080/toybox/pull", {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
        .then((response) => {
            console.log('Post Response.status: ', response.status);
            if (response.status !== 204) {
                getBalance();
                getToys();
                return response.json();
            } else {
                return response.statusText;
            }
        })
        .catch((error) => {
            console.error("An error occurred:", error);
        });

}


function increaseBalance() {
    const amountInput = document.getElementById("amount");
    const amount = parseInt(amountInput.value);

    // Validate the input
    if (isNaN(amount) || amount <= 0) {
        alert("Please enter a valid positive number.");
        return;
    }

    const content = {
        amount: amount
    };

    fetch("http://localhost:8080/account/deposit", {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(content)
    })
    .then((response) => {
        console.log('PATCH Response.status: ', response.status);
        if (response.status !== 204) {
            getBalance();
            return response.json();
        } else {
            return response.statusText;
        }
    })
    .catch((error) => {
        console.error("An error occurred:", error);
    });
}


document.addEventListener("DOMContentLoaded", function () {
    const depositForm = document.getElementById("depositForm");
    if (depositForm) {
        depositForm.addEventListener("submit", increaseBalance); // Use the increaseBalance function as the event handler
    } else {
        console.error("Element with ID 'depositForm' not found.");
    }

    // Call the functions on page loads
    getUsername();
    getBalance();
    getToys();
});