document.addEventListener("DOMContentLoaded", () => {

    const container = document.getElementById("questionsContainer");
    const addButton = document.getElementById("newQuestion");
    const form = document.getElementById("questionForm");

    let questionIndex =
        container.querySelectorAll(".question").length;


    setupExistingQuestions();


    /*
     * ==========================
     * ADD QUESTION
     * ==========================
     */

    addButton.addEventListener("click", () => {
        createNewQuestion();
    });


    /*
     * ==========================
     * EXISTING QUESTIONS
     * ==========================
     */

    function setupExistingQuestions() {

        const questions =
            container.querySelectorAll(".question");

        questions.forEach(question => {

            setupAnswerButtons(question);
            setupTypeSelector(question);
            setupDeleteButton(question);

            /*
             * DATE answer
             */

            const type =
                question.querySelector(".typeSelect")?.value;

            if (type === "DATE") {
                loadDateAnswer(question);
            }

        });

    }


    /*
     * ==========================
     * TYPE SELECTOR
     * ==========================
     */

    function setupTypeSelector(question) {

        const select =
            question.querySelector(".typeSelect");

        if (!select) {
            return;
        }

        select.addEventListener("change", () => {

            updateAnswerOptions(
                question,
                select.value
            );

        });

    }


    /*
     * ==========================
     * ANSWER BUTTONS
     * ==========================
     */

    function setupAnswerButtons(question) {

        const buttons =
            question.querySelectorAll(".answerButton");

        buttons.forEach(button => {

            /*
             * Avoid adding the same listener twice.
             */

            if (button.dataset.listenerAttached === "true") {
                return;
            }

            button.dataset.listenerAttached = "true";


            button.addEventListener("click", () => {

                const type =
                    question.querySelector(".typeSelect")?.value;

                const answerButtons =
                    question.querySelectorAll(".answerButton");


                /*
                 * ==========================
                 * ABCD
                 * ==========================
                 *
                 * Multiple answers are allowed.
                 *
                 * Example:
                 *
                 * A,B,D
                 */

                if (type === "ABCD") {

                    button.classList.toggle("selected");

                }


                /*
                 * ==========================
                 * TF / YN
                 * ==========================
                 *
                 * Only one answer is allowed.
                 */

                else {

                    answerButtons.forEach(other => {
                        other.classList.remove("selected");
                    });

                    button.classList.add("selected");

                }

            });

        });

    }


    /*
     * ==========================
     * DELETE QUESTION
     * ==========================
     */

    function setupDeleteButton(question) {

        const deleteButton =
            question.querySelector(".deleteQuestion");

        if (!deleteButton) {
            return;
        }

        if (deleteButton.dataset.listenerAttached === "true") {
            return;
        }

        deleteButton.dataset.listenerAttached = "true";


        deleteButton.addEventListener("click", async () => {

            const id =
                deleteButton.dataset.id;


            /*
             * ==========================
             * NEW QUESTION
             * ==========================
             *
             * It has not been saved yet,
             * so there is nothing to delete
             * from the database.
             */

            if (!id) {

                question.remove();

                updateIndexes();

                return;
            }


            /*
             * ==========================
             * EXISTING QUESTION
             * ==========================
             */

            const csrfToken =
                document.querySelector(
                    'meta[name="_csrf"]'
                )?.content;

            const csrfHeader =
                document.querySelector(
                    'meta[name="_csrf_header"]'
                )?.content;


            try {

                const response =
                    await fetch(
                        `/delete_question/${id}`,
                        {
                            method: "POST",

                            headers: {
                                [csrfHeader]: csrfToken
                            }
                        }
                    );


                if (!response.ok) {

                    throw new Error(
                        `Delete failed: ${response.status}`
                    );

                }


                const deleted =
                    await response.json();


                if (deleted) {

                    question.remove();

                    updateIndexes();

                } else {

                    alert(
                        "Could not delete the question."
                    );

                }

            } catch (error) {

                console.error(error);

                alert(
                    "An error occurred while deleting the question."
                );

            }

        });

    }


    /*
     * ==========================
     * CREATE NEW QUESTION
     * ==========================
     */

    function createNewQuestion() {

        const index =
            questionIndex;

        questionIndex++;


        const div =
            document.createElement("div");

        div.className = "question";


        div.innerHTML = `

<input
type="hidden"
name="questions[${index}].id"
value=""
    >

    <input
type="hidden"
name="questions[${index}].set"
value="${getSetName()}"
    >


    <label>
    Question:
</label>

<br>

    <input
        type="text"
        name="questions[${index}].question"
        class="questionInput"
        value="Insert new question here"
        required
    >


        <hr>


            <label>
                Type:
            </label>

            <br>

                <select
                    name="questions[${index}].type"
                    class="typeSelect"
                >

                    <option value="ABCD">
                        ABCD
                    </option>

                    <option value="TF">
                        True or False
                    </option>

                    <option value="OPEN">
                        Open question
                    </option>

                    <option value="DATE">
                        Date question
                    </option>

                    <option value="YN">
                        Yes or No
                    </option>

                </select>


                <hr>


                    <label>
                        Answer:
                    </label>

                    <br>


                        <div class="answerContainer"></div>


                        <hr>


                            <label>
                                Points:
                            </label>

                            <br>

                                <input
                                    type="number"
                                    name="questions[${index}].points"
                                    class="pointsInput"
                                    value="0"
                                    min="0"
                                    required
                                >


                                    <hr>


                                        <button
                                            type="button"
                                            class="deleteQuestion"
                                        >
                                            Delete
                                        </button>

                                        `;


        container.appendChild(div);


        const select =
            div.querySelector(".typeSelect");


        /*
        * Default type = ABCD
        */

        updateAnswerOptions(
            div,
            "ABCD"
        );


        select.addEventListener("change", () => {

            updateAnswerOptions(
                div,
                select.value
            );

        });


        setupDeleteButton(div);

    }


    /*
    * ==========================
    * ANSWER OPTIONS
    * ==========================
    */

    function updateAnswerOptions(
        question,
        type
    ) {

        const answerContainer =
            question.querySelector(".answerContainer");


        answerContainer.innerHTML = "";


        /*
         * ==========================
         * ABCD
         * ==========================
         */

        if (type === "ABCD") {

            answerContainer.innerHTML = `

                <div class="answerButtons">

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="A"
                    >
                        A
                    </button>

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="B"
                    >
                        B
                    </button>

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="C"
                    >
                        C
                    </button>

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="D"
                    >
                        D
                    </button>

                </div>

            `;

        }


        /*
         * ==========================
         * TRUE / FALSE
         * ==========================
         */

        else if (type === "TF") {

            answerContainer.innerHTML = `

                <div class="answerButtons">

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="TRUE"
                    >
                        True
                    </button>

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="FALSE"
                    >
                        False
                    </button>

                </div>

            `;

        }


        /*
         * ==========================
         * YES / NO
         * ==========================
         */

        else if (type === "YN") {

            answerContainer.innerHTML = `

                <div class="answerButtons">

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="YES"
                    >
                        Yes
                    </button>

                    <button
                        type="button"
                        class="answerButton"
                        data-answer="NO"
                    >
                        No
                    </button>

                </div>

            `;

        }


        /*
         * ==========================
         * OPEN
         * ==========================
         */

        else if (type === "OPEN") {

            answerContainer.innerHTML = `

                <input
                    type="text"
                    class="openAnswer"
                    placeholder="Answer"
                    required
                >

            `;

        }


        /*
         * ==========================
         * DATE
         * ==========================
         */

        else if (type === "DATE") {

            answerContainer.innerHTML = `

                <div class="dateContainer">

                    <div class="dateInputs">

                        <label>
                            Day
                        </label>

                        <input
                            type="number"
                            class="dayInput"
                            min="1"
                            max="31"
                        >

                    </div>


                    <div class="dateInputs">

                        <label>
                            Month
                        </label>

                        <input
                            type="number"
                            class="monthInput"
                            min="1"
                            max="12"
                        >

                    </div>


                    <div class="dateInputs">

                        <label>
                            Year
                        </label>

                        <input
                            type="number"
                            class="yearInput"
                            min="1"
                            max="9999"
                        >

                    </div>

                </div>

            `;

        }


        /*
         * Attach button listeners to newly
         * generated answer buttons.
         */

        setupAnswerButtons(question);

    }


    /*
    * ==========================
    * LOAD DATE ANSWER
    * ==========================
    */

    function loadDateAnswer(question) {

        const date =
            question.dataset.answer;


        if (!date) {
            return;
        }


        const parts =
            date.split("/");


        if (parts.length !== 3) {
            return;
        }


        const day =
            question.querySelector(".dayInput");

        const month =
            question.querySelector(".monthInput");

        const year =
            question.querySelector(".yearInput");


        if (day) {
            day.value = parts[0];
        }

        if (month) {
            month.value = parts[1];
        }

        if (year) {
            year.value = parts[2];
        }

    }


    /*
    * ==========================
    * FORM SUBMIT
    * ==========================
    */

    form.addEventListener("submit", (event) => {

        const questions =
            container.querySelectorAll(".question");


        /*
         * ==========================
         * VALIDATE ALL QUESTIONS
         * ==========================
         */

        for (const question of questions) {

            const type =
                question.querySelector(".typeSelect")?.value;


            /*
             * ==========================
             * ABCD / TF / YN
             * ==========================
             */

            if (
                type === "ABCD" ||
                type === "TF" ||
                type === "YN"
            ) {

                const selected =
                    question.querySelectorAll(
                        ".answerButton.selected"
                    );


                /*
                 * At least one answer
                 * must be selected.
                 *
                 * ABCD:
                 * multiple answers allowed.
                 *
                 * TF / YN:
                 * only one answer is possible.
                 */

                if (selected.length === 0) {

                    event.preventDefault();


                    alert(
                        "Please select at least one answer for every question."
                    );


                    question.scrollIntoView({
                        behavior: "smooth",
                        block: "center"
                    });


                    return;
                }

            }


            /*
             * ==========================
             * OPEN
             * ==========================
             */

            else if (type === "OPEN") {

                const open =
                    question.querySelector(".openAnswer");


                if (
                    !open ||
                    !open.value.trim()
                ) {

                    event.preventDefault();


                    alert(
                        "Please provide an answer for every open question."
                    );


                    open?.focus();


                    return;
                }

            }


            /*
             * ==========================
             * DATE
             * ==========================
             */

            else if (type === "DATE") {

                const day =
                    question.querySelector(
                        ".dayInput"
                    )?.value;

                const month =
                    question.querySelector(
                        ".monthInput"
                    )?.value;

                const year =
                    question.querySelector(
                        ".yearInput"
                    )?.value;


                if (
                    !day ||
                    !month ||
                    !year
                ) {

                    event.preventDefault();


                    alert(
                        "Please provide a complete date for every date question."
                    );


                    return;
                }

            }

        }


        /*
         * ==========================
         * GENERATE ANSWER INPUTS
         * ==========================
         */

        questions.forEach(question => {

            const type =
                question.querySelector(
                    ".typeSelect"
                )?.value;


            /*
             * Remove previous generated
             * answer input.
             */

            const old =
                question.querySelector(
                    ".generatedAnswer"
                );


            if (old) {
                old.remove();
            }


            /*
             * ==========================
             * ABCD / TF / YN
             * ==========================
             */

            if (
                type === "ABCD" ||
                type === "TF" ||
                type === "YN"
            ) {

                const selected =
                    question.querySelectorAll(
                        ".answerButton.selected"
                    );


                const answers =
                    Array.from(selected)
                        .map(button =>
                            button.dataset.answer
                        );


                const input =
                    document.createElement("input");


                input.type = "hidden";


                input.name =
                    findAnswerName(question);


                input.value =
                    answers.join(",");


                input.className =
                    "generatedAnswer";


                question.appendChild(input);

            }


            /*
             * ==========================
             * OPEN
             * ==========================
             */

            else if (type === "OPEN") {

                const open =
                    question.querySelector(
                        ".openAnswer"
                    );


                const input =
                    document.createElement("input");


                input.type = "hidden";


                input.name =
                    findAnswerName(question);


                input.value =
                    open?.value ?? "";


                input.className =
                    "generatedAnswer";


                question.appendChild(input);

            }


            /*
             * ==========================
             * DATE
             * ==========================
             */

            else if (type === "DATE") {

                const day =
                    question.querySelector(
                        ".dayInput"
                    )?.value ?? "";


                const month =
                    question.querySelector(
                        ".monthInput"
                    )?.value ?? "";


                const year =
                    question.querySelector(
                        ".yearInput"
                    )?.value ?? "";


                const input =
                    document.createElement("input");


                input.type = "hidden";


                input.name =
                    findAnswerName(question);


                input.value =
                    `${day}/${month}/${year}`;


                input.className =
                    "generatedAnswer";


                question.appendChild(input);

            }

        });

    });


    /*
    * ==========================
    * FIND ANSWER NAME
    * ==========================
    */

    function findAnswerName(question) {

        const questionInput =
            question.querySelector(
                'input[name$=".question"]'
            );


        return questionInput.name.replace(
            ".question",
            ".answer"
        );

    }


    /*
    * ==========================
    * UPDATE INDEXES
    * ==========================
    */

    function updateIndexes() {

        const questions =
            container.querySelectorAll(".question");


        questions.forEach((question, index) => {

            question
                .querySelectorAll("[name]")
                .forEach(input => {

                    input.name =
                        input.name.replace(
                            /questions\[\d+\]/,
                            `questions[${index}]`
                        );

                });

        });


        questionIndex =
            questions.length;

    }


    /*
    * ==========================
    * SET NAME
    * ==========================
    */

    function getSetName() {

        return document
            .getElementById("setName")
            .textContent
            .trim();

    }

});

