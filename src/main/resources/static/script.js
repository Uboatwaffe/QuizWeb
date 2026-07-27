console.log("script loaded");


const button = document.getElementById("getDataBtn");
const responseElement = document.getElementById("response");

button.addEventListener("click", async () => {
    responseElement.textContent = "Loading...";

    try {
        const response = await fetch("http://localhost:8080/test", {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error(`HTTP Error: ${response.status}`);
        }

        // If your API returns JSON
        const data = await response.json();

        // Display formatted JSON
        responseElement.textContent = JSON.stringify(data, null, 2);

        // If your API returns plain text instead, use:
        // const data = await response.text();
        // responseElement.textContent = data;

    } catch (error) {
        responseElement.textContent = `Error: ${error.message}`;
    }
});