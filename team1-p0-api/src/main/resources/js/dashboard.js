function getSessionData() {
    fetch("/session-data")
        .then(response => response.json())
        .then(jsonData => {
            const userNameElement = document.getElementById("username");
            userNameElement.textContent = jsonData;
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
    // Call the function when the page loads
    getSessionData();
    getToys();