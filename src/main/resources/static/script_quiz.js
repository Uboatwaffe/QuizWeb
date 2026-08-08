async function getQuestions() {
    const responseElement = document.getElementById("response");

    const setName = document.getElementById("setName").textContent.trim();

    if (!responseElement) {
        return;
    }

    try {
        const response = await fetch("/api/quiz/" + setName, {
            method: "GET"
        });

        const data = await response.json();

        responseElement.innerHTML = "";

        if (data === null || data.length === 0) {
            responseElement.innerHTML = `
                <p class="no-sets">
                    No questions are in this quiz
                </p>
            `;
            return;
        }

        // TODO: make it display correct type of ui depending on the type of question
        data.forEach(item => {
            const div = document.createElement("div");

            div.className = "item";

            div.innerHTML = `
                <button class="set-button">
                    ${item.question}
                </button>
            `;

            responseElement.appendChild(div);
        });
    } catch (error) {
        console.error("Error loading questions:", error);

        responseElement.innerHTML = `
           <p class="no-sets">
               Failed to load quiz.
           </p>
        `;
    }
}
window.addEventListener("DOMContentLoaded", getQuestions);