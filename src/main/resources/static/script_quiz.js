async function getQuestions() {
    const responseElement = document.getElementById("response");

    const setName = document.getElementById("setName").textContent.trim();

    if (!responseElement) {
        return;
    }

    try {
        const response = await fetch(
            "/api/quiz/" + encodeURIComponent(setName),
            {
                method: "GET"
            }
        );

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

        data.forEach(item => {
            const div = document.createElement("div");

            div.className = "question";
            div.dataset.id = item.id;

            div.innerHTML = `
                <h5>${item.question}</h5>
            `;

            if (item.type === "ABCD") {

                div.innerHTML += `
                    <button type="button" class="answerButton" data-answer="A">
                        A
                    </button>

                    <button type="button" class="answerButton" data-answer="B">
                        B
                    </button>

                    <button type="button" class="answerButton" data-answer="C">
                        C
                    </button>

                    <button type="button" class="answerButton" data-answer="D">
                        D
                    </button>
                `;

            } else if (item.type === "DATE") {

                div.innerHTML += `
                    <div class="dateContainer">

                        <div class="dateInputs">
                            Day
                            <br>
                            <input
                                type="number"
                                class="shortInput dayInput"
                                min="1"
                                max="31"
                            >
                        </div>

                        <div class="dateInputs">
                            Month
                            <br>
                            <input
                                type="number"
                                class="shortInput monthInput"
                                min="1"
                                max="12"
                            >
                        </div>

                        <div class="dateInputs">
                            Year
                            <br>
                            <input
                                type="number"
                                class="longerInput yearInput"
                                min="1"
                            >
                        </div>

                    </div>
                `;

            } else if (item.type === "TF") {

                div.innerHTML += `
                    <button type="button" class="answerButton" data-answer="TRUE">
                        True
                    </button>

                    <button type="button" class="answerButton" data-answer="FALSE">
                        False
                    </button>
                `;

            } else if (item.type === "OPEN") {

                div.innerHTML += `
                    <input
                        type="text"
                        class="openInput"
                    >
                `;

            } else if (item.type === "YN") {

                div.innerHTML += `
                    <button type="button" class="answerButton" data-answer="YES">
                        Yes
                    </button>

                    <button type="button" class="answerButton" data-answer="NO">
                        No
                    </button>
                `;
            }


            /*
             * Answer button handling.
             *
             * Only one button can be selected
             * for each question.
             */
            const answerButtons = div.querySelectorAll(
                ".answerButton"
            );

            answerButtons.forEach(button => {

                button.addEventListener("click", function () {

                    // Remove selected state from all buttons
                    // belonging to this question.
                    answerButtons.forEach(otherButton => {
                        otherButton.classList.remove("selected");
                    });

                    // Select the clicked button.
                    this.classList.add("selected");
                });
            });


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


/*
 * Date input validation.
 *
 * This listener is outside getQuestions(),
 * so it is only registered once.
 */
document.addEventListener("input", function (e) {

    /*
     * Day
     */
    if (e.target.classList.contains("dayInput")) {

        if (e.target.value.length > 2) {
            e.target.value =
                e.target.value.slice(0, 2);
        }

        if (e.target.value < 1) {
            e.target.value = 1;
        } else if (e.target.value > 31) {
            e.target.value = 31;
        }
    }


    /*
     * Month
     */
    if (e.target.classList.contains("monthInput")) {

        if (e.target.value.length > 2) {
            e.target.value =
                e.target.value.slice(0, 2);
        }

        if (e.target.value < 1) {
            e.target.value = 1;
        } else if (e.target.value > 12) {
            e.target.value = 12;
        }
    }


    /*
     * Year
     */
    if (e.target.classList.contains("longerInput")) {

        if (e.target.value.length > 4) {
            e.target.value =
                e.target.value.slice(0, 4);
        }
    }
});


window.addEventListener(
    "DOMContentLoaded",
    getQuestions
);