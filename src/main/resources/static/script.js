const button = document.getElementById("getDataBtn");
const response = document.getElementById("response");


function submitData(){

    const data = {
        login: document.getElementById("loginInput").value,
        password: document.getElementById("passwordInput").value
    }

    fetch("api/login/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            console.log(result);
        });
}




















