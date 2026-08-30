document.addEventListener("DOMContentLoaded", () => {

    const questions = document.querySelectorAll(".question");


    questions.forEach(question => {

        const answerButtons =
            question.querySelectorAll(".answerButton");


        /*
         * ABCD / YN / TF buttons
         */
        answerButtons.forEach(button => {

            button.addEventListener("click", () => {

                const isMultipleChoice =
                    question.querySelector(".ABCD") !== null;


                if (isMultipleChoice) {

                    /*
                     * ABCD allows multiple answers.
                     *
                     * Example:
                     * A + C
                     */
                    button.classList.toggle("selected");

                } else {

                    /*
                     * YN and TF allow only one answer.
                     */
                    answerButtons.forEach(otherButton => {
                        otherButton.classList.remove("selected");
                    });

                    button.classList.add("selected");
                }


                updateSubmittedAnswers(question);
            });

        });


        /*
         * OPEN question
         */
        const openInput =
            question.querySelector(".openInput");

        if (openInput) {

            openInput.addEventListener("input", () => {
                updateSubmittedAnswers(question);
            });
        }


        /*
         * DATE question
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


function updateSubmittedAnswers(question) {

    /*
     * Remove previously generated inputs.
     */
    question
        .querySelectorAll(".submittedAnswer")
        .forEach(input => input.remove());


    /*
     * Find question ID.
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
     * Create:
     *
     * answers[0].answers
     */
    const answerName =
        questionIdInput.name.replace(
            ".questionId",
            ".answers"
        );


    /*
     * =================================
     * BUTTON ANSWERS
     * =================================
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

        input.value = button.dataset.answer;

        input.classList.add("submittedAnswer");

        question.appendChild(input);
    });


    /*
     * =================================
     * OPEN ANSWER
     * =================================
     */

    const openInput =
        question.querySelector(".openInput");


    if (openInput && openInput.value.trim() !== "") {

        const input =
            document.createElement("input");

        input.type = "hidden";

        input.name = answerName;

        input.value = openInput.value.trim();

        input.classList.add("submittedAnswer");

        question.appendChild(input);
    }


    /*
     * =================================
     * DATE ANSWER
     * =================================
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


        if (day && month && year) {

            const date =
                `${day}/${month}/${year}`;


            const input =
                document.createElement("input");

            input.type = "hidden";

            input.name = answerName;

            input.value = date;

            input.classList.add("submittedAnswer");

            question.appendChild(input);
        }
    }
}