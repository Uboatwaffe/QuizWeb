async function getUsers() {
    const responseElement = document.getElementById('response');

    if (!responseElement) {
        return;
    }

    try {
        // TODO: make api request follow same pattern
        const response = await fetch(`/api/get_users`, {
            method: 'GET',
        });

        const data = await response.json();

        console.log(data);

        responseElement.innerHTML = "";

        if (data === null || data.length === 0) {
            responseElement.innerHTML = `
                <p class="no-sets">
                    No users
                </p>
            `;
            return;
        }

        data.forEach(user => {
            const div = document.createElement("div");
            div.className = "question"
            div.dataset.id = user.id;

            div.innerHTML = `
                <h3>Login:</h3>
                <input type="text" class="loginInput" value="${user.username}" />
                
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

function submitChanges() {
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
            login: div.querySelector(".loginInput").textContent.trim(),
            role: div.querySelector(".roleSelect").value,
        }

        users.push(userData);
    })
    console.log(users);
    return users
}













