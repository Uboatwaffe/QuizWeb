async function getQuestions() {
    const responseElement = document.getElementById("response");

    const setName = document.getElementById("setName").textContent.trim();

    if (!responseElement) {
        return;
    }

    try {
        const response = await fetch("/api/quiz/" + encodeURIComponent(setName), {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }

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
                <label>Question:</label><br>
                <input class="questionInput" value="${item.question}"><br>

                <hr>

                <label>Type:</label><br>
                <select class="typeSelect">
                    <option value="ABCD">ABCD</option>
                    <option value="TF">True or False</option>
                    <option value="OPEN">Open question</option>
                    <option value="DATE">Date question</option>
                    <option value="YN">Yes or No</option>
                </select>

                <div class="typeOptions"></div>

                <hr>

                <label>Points:</label><br>
                <input
                    type="number"
                    class="pointsInput"
                    value="${item.points}"
                >

                <br>
                <hr>

                <button type="button" class="deleteQuestion">
                    Delete
                </button>
            `;

            const select = div.querySelector(".typeSelect");
            const optionsContainer = div.querySelector(".typeOptions");
            const deleteButton = div.querySelector(".deleteQuestion");

            /*
             * Creates the inputs/options depending on question type.
             */
            function updateOptions(type) {
                optionsContainer.innerHTML = "";

                if (type === "YN") {

                    optionsContainer.innerHTML = `
                        <label>
                            <input type="checkbox" value="YES">
                            Yes
                        </label>

                        <label>
                            <input type="checkbox" value="NO">
                            No
                        </label>
                    `;

                } else if (type === "ABCD") {

                    optionsContainer.innerHTML = `
                        <label>
                            <input type="checkbox" value="A">
                            A
                        </label>

                        <label>
                            <input type="checkbox" value="B">
                            B
                        </label>

                        <label>
                            <input type="checkbox" value="C">
                            C
                        </label>

                        <label>
                            <input type="checkbox" value="D">
                            D
                        </label>
                    `;

                } else if (type === "TF") {

                    optionsContainer.innerHTML = `
                        <label>
                            <input type="checkbox" value="TRUE">
                            True
                        </label>

                        <label>
                            <input type="checkbox" value="FALSE">
                            False
                        </label>
                    `;

                } else if (type === "OPEN") {

                    optionsContainer.innerHTML = `
                        <input
                            type="text"
                            class="openAnswer"
                            placeholder="Answer"
                        >
                    `;

                } else if (type === "DATE") {

                    optionsContainer.innerHTML = `
                        <div class="dateContainer">

                            <div class="dateInputs">
                                Day<br>
                                <input
                                    type="number"
                                    class="shortInput dayInput"
                                    min="1"
                                    max="31"
                                >
                            </div>

                            <div class="dateInputs">
                                Month<br>
                                <input
                                    type="number"
                                    class="shortInput monthInput"
                                    min="1"
                                    max="12"
                                >
                            </div>

                            <div class="dateInputs">
                                Year<br>
                                <input
                                    type="number"
                                    class="longerInput yearInput"
                                    min="1"
                                >
                            </div>

                        </div>
                    `;
                }
            }

            select.addEventListener("change", function () {
                updateOptions(this.value);
            });

            select.value = item.type;

            optionsContainer.addEventListener("change", function (event) {
                if (event.target.type === "checkbox") {
                    const checkboxes = optionsContainer.querySelectorAll(
                        'input[type="checkbox"]'
                    );

                    checkboxes.forEach(checkbox => {
                        if (checkbox !== event.target) {
                            checkbox.checked = false;
                        }
                    });
                }
            });

            updateOptions(item.type);


            deleteButton.addEventListener("click", async function () {

                try {
                    const deleteResponse = await fetch(`/api/deleteQuestion/${item.id}`, {
                        method: "DELETE",
                    })

                    if (deleteResponse.ok) {
                        div.remove();
                    } else {
                        console.error("Delete failed");
                    }
                } catch (error) {
                    console.error("Error deleting question", error);
                }
            });

            document.addEventListener("input", function (e) {

                if (e.target.classList.contains("dayInput")) {
                    if (e.target.value.length > 2) {
                        e.target.value = e.target.value.slice(0, 2);
                    }

                    if (e.target.value < 1) {
                        e.target.value = 1;
                    } else if (e.target.value > 31) {
                        e.target.value = 31;
                    }
                }

                if (e.target.classList.contains("monthInput")) {
                    if (e.target.value.length > 2) {
                        e.target.value = e.target.value.slice(0, 2);
                    }

                    if (e.target.value < 1) {
                        e.target.value = 1;
                    } else if (e.target.value > 12) {
                        e.target.value = 12;
                    }
                }

                if (e.target.classList.contains("yearInput")) {
                    if (e.target.value.length > 4) {
                        e.target.value = e.target.value.slice(0, 4);
                    }
                }
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

window.addEventListener("DOMContentLoaded", getQuestions);



function newQuestion(){
    const responseElement = document.getElementById("newDivQuestion");


    const div = document.createElement("div");
    div.className = "question";



    div.innerHTML = `
        <label>Question:</label><br>
        <input class="questionInput" value="Insert new question here"><br>

            <hr>

                <label>Type:</label><br>
                <select class="typeSelect">
                    <option value="ABCD">ABCD</option>
                    <option value="TF">True or False</option>
                    <option value="OPEN">Open question</option>
                    <option value="DATE">Date question</option>
                    <option value="YN">Yes or No</option>
                </select>

                <div class="typeOptions"></div>

                <hr>

                <label>Points:</label><br>
                <input
                    type="number"
                    class="pointsInput"
                    value="0"
                >

                <br>
                <hr>

                <button type="button" class="deleteQuestion">
                    Delete
                </button>
                `;

                const select = div.querySelector(".typeSelect");
                const optionsContainer = div.querySelector(".typeOptions");
                const deleteButton = div.querySelector(".deleteQuestion");

                function updateOptions(type) {
                    optionsContainer.innerHTML = "";

                    if (type === "YN") {
                        optionsContainer.innerHTML = `
                            <label>
                                <input type="checkbox" value="YES">
                                Yes
                            </label>
    
                            <label>
                                <input type="checkbox" value="NO">
                                No
                            </label>
                        `;

                    } else if (type === "ABCD") {
                        optionsContainer.innerHTML = `
                            <label>
                                <input type="checkbox" value="A">
                                A
                            </label>
    
                            <label>
                                <input type="checkbox" value="B">
                                B
                            </label>
    
                            <label>
                                <input type="checkbox" value="C">
                                C
                            </label>
    
                            <label>
                                <input type="checkbox" value="D">
                                D
                            </label>
                        `;

                    } else if (type === "TF") {
                        optionsContainer.innerHTML = `
                            <label>
                                <input type="checkbox" value="TRUE">
                                True
                            </label>
    
                            <label>
                                <input type="checkbox" value="FALSE">
                                False
                            </label>
                        `;

                    } else if (type === "OPEN") {
                        optionsContainer.innerHTML = `
                            <input
                                type="text"
                                class="openAnswer"
                                placeholder="Answer"
                            >
                        `;

                    } else if (type === "DATE") {
                        optionsContainer.innerHTML = `
                            <div class="dateContainer">
    
                                <div class="dateInputs">
                                    Day<br>
                                    <input
                                        type="number"
                                        class="shortInput dayInput"
                                        min="1"
                                        max="31"
                                    >
                                </div>
    
                                <div class="dateInputs">
                                    Month<br>
                                    <input
                                        type="number"
                                        class="shortInput monthInput"
                                        min="1"
                                        max="12"
                                    >
                                </div>
    
                                <div class="dateInputs">
                                    Year<br>
                                    <input
                                        type="number"
                                        class="longerInput yearInput"
                                        min="1"
                                    >
                                </div>
    
                            </div>
                        `;
                    }
                }

                updateOptions("ABCD");

                select.addEventListener("change", function () {
                    updateOptions(this.value);
                });

                optionsContainer.addEventListener("change", function (event) {
                    if (event.target.type === "checkbox") {
                        const checkboxes = optionsContainer.querySelectorAll(
                            'input[type="checkbox"]'
                        );

                        checkboxes.forEach(checkbox => {
                            if (checkbox !== event.target) {
                                checkbox.checked = false;
                            }
                        });
                    }
                });


                deleteButton.addEventListener("click", async function () {
                    div.remove();
                });

                document.addEventListener("input", function (e) {

                    if (e.target.classList.contains("dayInput")) {
                        if (e.target.value.length > 2) {
                            e.target.value = e.target.value.slice(0, 2);
                        }

                        if (e.target.value < 0) {
                            e.target.value = 1;
                        } else if (e.target.value > 31) {
                            e.target.value = 31;
                        }
                    }

                    if (e.target.classList.contains("monthInput")) {
                        if (e.target.value.length > 2) {
                            e.target.value = e.target.value.slice(0, 2);
                        }

                        if (e.target.value < 0) {
                            e.target.value = 1;
                        } else if (e.target.value > 12) {
                            e.target.value = 12;
                        }
                    }

                    if (e.target.classList.contains("yearInput")) {
                        if (e.target.value.length > 4) {
                            e.target.value = e.target.value.slice(0, 4);
                        }
                    }
                });

                responseElement.appendChild(div);
}

function getNewQuestions() {
    const newQuestionContainer = document.getElementById("newDivQuestion");
    const setName = document.getElementById("setName").textContent.trim()

    if (!newQuestionContainer) {
        console.error("Could not find #newDivQuestion");
        return [];
    }

    const questionDivs = newQuestionContainer.querySelectorAll(".question");
    const questions = [];

    questionDivs.forEach(div => {
        const questionData = {
            question: div.querySelector(".questionInput")?.value ?? "",
            type: div.querySelector(".typeSelect")?.value ?? "",
            points: Number(div.querySelector(".pointInput")?.value ?? 0),
            set: setName,
            answer: null
        };

        const type = questionData.type;

        if (type === "ABCD" || type === "TF" || type === "YN") {
            const checked = div.querySelector(
                ".typeOptions input[type='checkbox']:checked"
            );

            if (checked) {
                questionData.answer = checked.value;
            }

        } else if (type === "OPEN") {
            const open = div.querySelector(
                ".openAnswer"
            );

            // TODO: fix it
            questionData.answer = open.value.trim();


        } else if (type === "DATE") {
            const day = div.querySelector(".dayInput");
            const month = div.querySelector(".monthInput");
            const year = div.querySelector(".yearInput");

            questionData.answer = {
                day: day?.value || null,
                month: month?.value || null,
                year: year?.value || null
            };
        }

        questions.push(questionData);
    });

    console.log(questions);
    return questions;
}







