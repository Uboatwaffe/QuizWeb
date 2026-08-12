
function getCookie(name) {

    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);

    if (parts.length === 2) {
        return parts.pop().split(";").shift();
    }

    return null;
}

// this function is responsible for sending new user details
function submitNewUser() {

    let role = document.getElementById("wantToBeAdmin").checked;

    if (role === true) {
        role = "ADMIN";
    } else {
        role = "USER";
    }

    const data = {
        login: document.getElementById("loginInput").value,
        passwordOne: document.getElementById("passwordInput").value,
        passwordRepeat: document.getElementById("passwordInputRepeat").value,
        role: role
    };

    fetch("/api/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/home";
            } else if (response.status === 409) {
                alert("This username is already taken");
            } else if (response.status === 403) {
                alert("Request rejected");
            } else {
                alert("Something went wrong");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

// This function creates new set of specified name
function createNewSet() {
    const data = {
        name: document.getElementById("setNameInput").value
    }

    fetch("api/newSet", {
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
            console.error("Error:", error);
        })
}






































