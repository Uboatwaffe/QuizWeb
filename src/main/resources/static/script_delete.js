async function getSets() {
    const responseElement = document.getElementById("response");

    if (!responseElement) {
        return;
    }

    try {
        const response = await fetch("/api/choose_set", {
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

        data.forEach(item => {
            const div = document.createElement("div");

            div.className = "item";

            div.innerHTML = `
                <button class="set-button">
                    ${item.name}
                </button>
            `;

            const button = div.querySelector(".set-button");

            button.addEventListener("click", async function () {
                const setName = encodeURIComponent(this.textContent.trim());

                try {
                    const deleteResponse = await fetch(`/api/delete/${setName}`, {
                        method: "DELETE"
                    });

                    if (deleteResponse.ok) {
                        div.remove();
                    } else {
                        console.error("Delete failed");
                    }

                } catch (error) {
                    console.error("Error deleting set:", error);
                }
            });

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