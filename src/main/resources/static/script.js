// This function submits data for logging in
function submitData(){

    const data = {
        login: document.getElementById("loginInput").value,
        password: document.getElementById("passwordInput").value
    }

    // request
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

// this function is responsible for sending new user details
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

    // request
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

// This function creates new set of specified name
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






































