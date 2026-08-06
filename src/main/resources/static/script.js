
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

const responseElement = document.getElementById("response");

window.addEventListener("DOMContentLoaded", getSets);

async function getSets() {
    const response = await fetch("/api/choose_set");
    const data = await response.json();

    responseElement.innerHTML = "";

    data.forEach(item => {
        const div = document.createElement("div");
        div.className = "item";
        div.innerHTML = `<h3>${item.name}</h3>`;
        responseElement.appendChild(div);
    });
}






































