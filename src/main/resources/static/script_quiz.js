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

            div.className = "question";

            div.innerHTML = ` 
              <h5>${item.question}</h5>  
            `;

            if (item.type === "ABCD"){
                div.innerHTML += `
                    <button id="A">A</button>
                    <button id="B">B</button>
                    <button id="C">C</button>
                    <button id="D">D</button>
                `;
            } else if (item.type === "DATE"){
                div.innerHTML += `
                    <div class="dateContainer">
                        <div class="dateInputs">
                            Day
                            <br>
                            <input type="number" id="dayInput" class="shortInput">
                        </div>
                        <div class="dateInputs">
                            Month
                            <br>
                            <input type="number" id="monthInput" class="shortInput">
                        </div>
                        <div class="dateInputs">
                            Year
                            <br>
                            <input type="number" id="yearInput" class="longerInput">
                        </div>
                    </div>
                `;

                document.addEventListener("input", function (e) {
                    if (e.target.classList.contains("shortInput")) {
                        if (e.target.value.length > 2) {
                            e.target.value = e.target.value.slice(0, 2);
                        }

                        if (e.target.value < 1) {
                            e.target.value = 1
                        } else if (e.target.value > 12) {
                            e.target.value = 12
                        }
                    }

                    if (e.target.classList.contains("longerInput")) {
                        if (e.target.value.length > 4) {
                            e.target.value = e.target.value.slice(0, 4);
                        }
                    }
                });
            } else if (item.type === "TF"){
                div.innerHTML += `
                    <button id="true">True</button>
                    <button id="false">False</button>
                `;
            } else if (item.type === "OPEN"){
                div.innerHTML += `
                    <input type="text" id="openInput">
                `;
            } else if (item.type === "YN"){
                div.innerHTML += `
                    <button id="yes">Yes</button>
                    <button id="no">No</button>
                `;
            }

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