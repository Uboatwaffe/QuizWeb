
function getCookie(name) {

    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);

    if (parts.length === 2) {
        return parts.pop().split(";").shift();
    }

    return null;
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






































