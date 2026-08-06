
function submitData(){

    const data = {
        login: document.getElementById("loginInput").value,
        password: document.getElementById("passwordInput").value
    }

    fetch("api/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/home";
            } else {
                alert("Wrong login or password");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}


function submitNewUser(){
    let role = document.getElementById("wantToBeAdmin").checked

    if (role === true){
        role = "ADMIN"
    } else {
        role = "USER"
    }



    const data = {
        login: document.getElementById("loginInput").value,
        passwordOne: document.getElementById("passwordInput").value,
        passwordRepeat: document.getElementById("passwordInputRepeat").value,
        role: role
    }

    fetch("api/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/home";
            } else {
                alert("This username is already taken");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

function createNewSet() {
    const data = {
        name: document.getElementById("setNameInput").value
    }

    fetch("api/new_set", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            window.location.href = "/home";
            if (response.ok) {
                alert("Set created successfully")
            } else {
                alert("Something went wrong")
            }
        })
        .catch(error => {
            conole.error("Error:", error);
        })
}

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




































