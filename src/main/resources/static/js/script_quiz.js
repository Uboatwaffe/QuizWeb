document.addEventListener("DOMContentLoaded", () => {

    const questions = document.querySelectorAll(".question");


    questions.forEach(question => {

        const answerButtons =
            question.querySelectorAll(".answerButton");


        /*
         * Answer buttons
         */
        answerButtons.forEach(button => {

            button.addEventListener("click", () => {

                const questionContainer =
                    button.closest(".question");

                const isMultipleChoice =
                    questionContainer.querySelector(".ABCD") !== null;


                /*
                 * ABCD
                 *
                 * Multiple answers are allowed.
                 *
                 * Example:
                 * A + C
                 */
                if (isMultipleChoice) {

                    button.classList.toggle("selected");

                }


                /*
                 * YN / TF
                 *
                 * Only one answer is allowed.
                 *
                 * Example:
                 * YES
                 */
                else {

                    answerButtons.forEach(otherButton => {

                        otherButton.classList.remove("selected");

                    });

                    button.classList.add("selected");

                }


                updateSubmittedAnswers(questionContainer);

            });

        });


        /*
         * Open question
         */
        const openInput =
            question.querySelector(".openInput");

        if (openInput) {

            openInput.addEventListener("input", () => {

                updateSubmittedAnswers(question);

            });

        }


        /*
         * Date question
         */
        const dayInput =
            question.querySelector(".dayInput");

        const monthInput =
            question.querySelector(".monthInput");

        const yearInput =
            question.querySelector(".yearInput");


        if (dayInput && monthInput && yearInput) {

            dayInput.addEventListener(
                "input",
                () => updateSubmittedAnswers(question)
            );

            monthInput.addEventListener(
                "input",
                () => updateSubmittedAnswers(question)
            );

            yearInput.addEventListener(
                "input",
                () => updateSubmittedAnswers(question)
            );

        }

    });


    /*
     * Reset
     */
    const form = document.querySelector("form");

    if (form) {

        form.addEventListener("reset", () => {

            setTimeout(() => {

                document
                    .querySelectorAll(".answerButton")
                    .forEach(button => {
                        button.classList.remove("selected");
                    });


                document
                    .querySelectorAll(".submittedAnswer")
                    .forEach(input => {
                        input.remove();
                    });

            }, 0);

        });

    }

});


/*
 * Update the hidden inputs for one question.
 */
function updateSubmittedAnswers(question) {

    /*
     * Remove previously generated answer inputs.
     */
    question
        .querySelectorAll(".submittedAnswer")
        .forEach(input => input.remove());


    /*
     * Find the question ID input.
     *
     * Example:
     *
     * answers[0].questionId
     */
    const questionIdInput =
        question.querySelector(
            'input[name$=".questionId"]'
        );


    if (!questionIdInput) {
        return;
    }


    /*
     * Convert:
     *
     * answers[0].questionId
     *
     * into:
     *
     * answers[0].answers
     */
    const answerName =
        questionIdInput.name.replace(
            ".questionId",
            ".answers"
        );


    /*
     * ==========================
     * ABCD / YN / TF
     * ==========================
     */

    const selectedButtons =
        question.querySelectorAll(
            ".answerButton.selected"
        );


    selectedButtons.forEach(button => {

        const input =
            document.createElement("input");

        input.type = "hidden";

        input.name = answerName;

        input.value =
            button.dataset.answer;

        input.classList.add(
            "submittedAnswer"
        );

        question.appendChild(input);

    });


    /*
     * ==========================
     * OPEN
     * ==========================
     */

    const openInput =
        question.querySelector(".openInput");


    if (openInput && openInput.value.trim() !== "") {

        const input =
            document.createElement("input");

        input.type = "hidden";

        input.name = answerName;

        input.value =
            openInput.value;

        input.classList.add(
            "submittedAnswer"
        );

        question.appendChild(input);

    }


    /*
     * ==========================
     * DATE
     * ==========================
     */

    const dayInput =
        question.querySelector(".dayInput");

    const monthInput =
        question.querySelector(".monthInput");

    const yearInput =
        question.querySelector(".yearInput");


    if (dayInput && monthInput && yearInput) {

        const day = dayInput.value;
        const month = monthInput.value;
        const year = yearInput.value;


        /*
         * Only submit the date if
         * all three fields are filled.
         */
        if (day && month && year) {

            const date =
                `${day}-${month}-${year}`;


            const input =
                document.createElement("input");

            input.type = "hidden";

            input.name = answerName;

            input.value = date;

            input.classList.add(
                "submittedAnswer"
            );

            question.appendChild(input);

        }

    }

}