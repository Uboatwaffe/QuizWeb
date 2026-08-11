// This function gets all question from specified set
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

        // creates <div class="question"> for each item
        data.forEach(item => {
            const div = document.createElement("div");

            div.className = "question";
            div.dataset.id = item.id;

            div.innerHTML = `
                <h3>${item.question}</h3>
                <h5>Points: ${item.points}</h5>
            `;

            // sets up what should be visible depending on what type of question this is
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
             * Only one button can be selected for each question.
             */
            const answerButtons = div.querySelectorAll(
                ".answerButton"
            );

            answerButtons.forEach(button => {

                button.addEventListener("click", function () {

                    // Remove selected state from all buttons belonging to this question.
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


// This function submits all answers (id and answer only)
async function submitAnswers() {

    const setNameElement = document.getElementById("setName");

    if (!setNameElement) {
        return;
    }

    const setName = setNameElement.textContent.trim();
    const questions = document.querySelectorAll(".question");

    const answers = [];

    questions.forEach(question => {

        const questionId = question.dataset.id;

        let answer = null;

        // getting correct type of answer
        /*
         * ABCD, TF and YN
         */
        const selectedButton = question.querySelector(
            ".answerButton.selected"
        );

        if (selectedButton) {
            answer = selectedButton.dataset.answer;
        }

        /*
         * OPEN
         */
        const openInput = question.querySelector(".openInput");

        if (openInput) {
            answer = openInput.value.trim();
        }

        /*
         * DATE
         */
        const dayInput = question.querySelector(".dayInput");
        const monthInput = question.querySelector(".monthInput");
        const yearInput = question.querySelector(".yearInput");

        // merging date data
        if (dayInput && monthInput && yearInput) {

            const day = dayInput.value.trim();
            const month = monthInput.value.trim();
            const year = yearInput.value.trim();

            if (day && month && year) {
                answer = `${day}/${month}/${year}`;
            }
        }

        answers.push({
            id: questionId,
            answer: answer
        });
    });


    try {

        const response = await fetch(
            "/api/submitAnswers/" + encodeURIComponent(setName),
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(answers)
            }
        );

        if (!response.ok) {
            throw new Error(
                `Failed to submit answers: ${response.status}`
            );
        }

        const data = await response.json();

        // redirecting user to score.html with their score in url
        window.location.href =
            "/score?score=" +
            encodeURIComponent(data.scored) +
            "&maxScore=" +
            encodeURIComponent(data.outOf);


    } catch (error) {

        console.error("Error submitting answers:", error);

        alert("Failed to submit quiz. Please try again.");
    }
}

