const button = document.getElementById("getDataBtn");
const responseElement = document.getElementById("response");

button.addEventListener("click", async () => {
    responseElement.textContent = "Loading...";

    try {
        const response = await fetch("/test");

        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }

        const data = await response.json();

        responseElement.innerHTML = "";

        data.forEach(item => {
            const element = document.createElement("div");

            element.className = "item";

            element.innerHTML = `
                <h3>ID: ${item.id}</h3>
                <p>Name: ${item.name}</p>
            `;

            responseElement.appendChild(element);
        });

    } catch (error) {
        responseElement.textContent = "Error: " + error.message;
    }
});