const button = document.getElementById("getDataBtn");
const response = document.getElementById("response");

button.addEventListener("click", async () => {
    try {
        const result = await fetch("/api/test");

        const data = await result.json();

        response.innerHTML = "";

        data.forEach(item => {
            response.innerHTML += `
                <div class="item">
                    <h3>ID: ${item.id}</h3>
                    <p>Name: ${item.name}</p>
                </div>
            `;
        });

    } catch (error) {
        response.textContent = "Error: " + error.message;
    }
});