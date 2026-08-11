async function getUsers() {
    const responseElement = document.getElementById('response');

    if (!responseElement) {
        return;
    }

    try {
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
                <h3>Login: ${user.login}</h3>
                
                <hr>
                
                <label>Role:</label>
                <select class="roleSelect">
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                </select>    
            `;

            responseElement.appendChild(div);

        })
    } catch (error) {
        console.log(error);
    }
}

window.addEventListener("DOMContentLoaded", getUsers);