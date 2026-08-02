
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











































