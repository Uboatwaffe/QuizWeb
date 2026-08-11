// This function retrieves every user from backend
async function getUsers() {
    const responseElement = document.getElementById('response');

    if (!responseElement) {
        return;
    }

    try {
        const response = await fetch(`/api/getUsers`, {
            method: 'GET',
        });

        const data = await response.json();

        responseElement.innerHTML = "";

        if (data === null || data.length === 0) {
            responseElement.innerHTML = `
                <p class="no-sets">
                    No users
                </p>
            `;
            return;
        }

        // Sets up fields for every user
        data.forEach(user => {
            const div = document.createElement("div");
            div.className = "question"
            div.dataset.id = user.id;

            div.innerHTML = `
                <h3>Login:</h3>
                <input type="text" class="loginInput" value="${user.login}" />
                
                <hr>
                
                <label>Role:</label>
                <select class="roleSelect">
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                </select>    
            `;

            const select = div.querySelector(".roleSelect");

            select.value = user.role;

            responseElement.appendChild(div);

        })
    } catch (error) {
        console.log(error);
    }
}

window.addEventListener("DOMContentLoaded", getUsers);

// This function retrieves data from ui
function getChanges() {
    const changesContainer = document.getElementById("response");

    if (!changesContainer) {
        console.error("Could not find #response");
        return [];
    }

    const userDivs = changesContainer.querySelectorAll(".question");
    const users = [];

    userDivs.forEach(div => {
        const userData = {
            id: div.dataset.id,
            login: div.querySelector(".loginInput").value,
            role: div.querySelector(".roleSelect").value,
        }

        users.push(userData);
    })
    return users
}

// This function submits the changes to backend to update users
async function submitChanges() {
    const data = await getChanges();

    try {
        const response = await fetch("/api/updateUsers", {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: {
                "Content-Type": "application/json",
            }
        })

        if (response.ok) {
            window.location.href = "/home"
        } else {
            alert("Could not update users")
            window.location.replace("/home");
        }
    } catch (error) {
        console.log(error);
    }
}













