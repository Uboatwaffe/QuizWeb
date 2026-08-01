const button = document.getElementById("getDataBtn");
const response = document.getElementById("response");


function submitData(){

    const data = {
        login: document.getElementById("loginInput").value,
        password: document.getElementById("passwordInput").value
    }

    fetch("api/login", {
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




















