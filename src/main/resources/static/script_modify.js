// This function returns all available sets
async function getSets() {
    const responseElement = document.getElementById("response");

    if (!responseElement) {
        return;
    }

    try {
        const response = await fetch("/api/chooseSet", {
            method: "GET"
        });

        const data = await response.json();

        responseElement.innerHTML = "";

        if (data === null || data.length === 0) {
            responseElement.innerHTML = `
                <p class="no-sets">
                    No quiz sets available.
                </p>
            `;
            return;
        }

        // creates <div class="item"> for every set
        data.forEach(item => {
            const div = document.createElement("div");

            div.className = "item";

            div.innerHTML = `
                <button class="set-button">
                    ${item.name}
                </button>
            `;

            const button = div.querySelector(".set-button");

            // setting up a listener to send user to modify_set.html with correct set
            button.addEventListener("click", async function () {
                const setName = encodeURIComponent(item.name.trim());

                window.location.href = "/modify/" + setName;

            })

            responseElement.appendChild(div);
        });
    } catch (error) {
        console.error("Error loading sets:", error);

        responseElement.innerHTML = `
           <p class="no-sets">
               Failed to load quiz sets.
           </p>
        `;
    }
}
window.addEventListener("DOMContentLoaded", getSets);