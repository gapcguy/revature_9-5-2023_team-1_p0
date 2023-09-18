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
    fetch("/toybox")
        .then(response => response.json())
        .then(jsonData => {
            const toyListContainer = document.getElementById("toyList");

            for (const item of jsonData) {
                // Create a container for each item
                const itemContainer = document.createElement("div");
                itemContainer.classList.add("item-container");

                // Create an image element (<img>) for the toy image
                const toyImageRender = document.createElement("img");
                toyImageRender.src = item.toyImage;
                toyImageRender.style.width="150px";

                // Create a list item (<li>) for the toy name
                const listItem = document.createElement("span");
                listItem.style.color = "#ffffff";
                listItem.textContent = item.toyName;



                // Create a paragraph element (<p>) for the quantity
                const quantityParagraph = document.createElement("p");
                quantityParagraph.style.color = "#ffffff";
                quantityParagraph.textContent = `Quantity: ${item.quantity}`;

                // Append the toyImageRender, listItem, and quantityParagraph to the container
                itemContainer.appendChild(toyImageRender);
                itemContainer.appendChild(listItem);
                itemContainer.appendChild(quantityParagraph);

                // Append the item container to the toyListContainer
                toyListContainer.appendChild(itemContainer);
            }
        })
        .catch(error => {
            console.error("Error fetching data:", error);
        });
}
    // Call the function when the page loads
    getUsername();
    getToys();