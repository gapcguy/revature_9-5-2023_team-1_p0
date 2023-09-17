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

function updateBalanceOnDashboard(newBalance) {
    const currencyDisplay = document.getElementById("balance");
    currencyDisplay.textContent = `${newBalance}`;
}

function increaseBalance(event) {
    //event.preventDefault(); -- This prevents updating of the database.

    const form = event.target;
    const amountInput = form.querySelector("#amount");

    fetch("/account/deposit", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ amount: amountInput.value }),
    })
        .then((response) => response.json())
        .then((data) => {
            updateBalanceOnDashboard(data.newBalance);
        })
        .catch((error) => {
            console.error("Error depositing funds:", error);
        });
}

document.addEventListener("DOMContentLoaded", function () {
    const depositForm = document.getElementById("depositForm");
    if (depositForm) {
        depositForm.addEventListener("submit", increaseBalance); // Use the increaseBalance function as the event handler
    } else {
        console.error("Element with ID 'depositForm' not found.");
    }

    // Call the function when the page loads
    getUsername();
    getBalance();
    getToys();
});