function getMessages() {
    let username = document.querySelector('input.form1[name="username"]').value;
    let password = document.querySelector('input.form1[name="password"]').value;

    let url = `http://localhost:8080/message?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;

    fetch(url, {
        method: "GET"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(json => {
        var stringMessages = "";
        for (let i = 0; i < json.length; i++){
          stringMessages += json[i].text;
          stringMessages += "\n";
          stringMessages += "\n";
        }
        emptyForms("form1");
        alert(stringMessages);
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
}


function sendMessage() {
    let user = document.querySelector('input.form2[name="user"]').value;
    let text = document.querySelector('input.form2[name="text"]').value;

    let url = `http://localhost:8080/message?user=${encodeURIComponent(user)}&text=${encodeURIComponent(text)}`;

    fetch(url, {
        method: "POST"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        alert("Message was sent successfully!")
        emptyForms("form2");
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
}

function register(){
    let username = document.querySelector('input.form3[name="username"]').value;
    let password = document.querySelector('input.form3[name="password"]').value;

    let url = `http://localhost:8080/user?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;

    fetch(url, {
        method: "POST"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        alert("Registered successfully!")
        emptyForms("form3");
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
}

function emptyForms(id){
    let form = document.getElementById(id);
    let inputs = form.querySelectorAll("input");
    let inputsArray = Array.from(inputs);

    for (let i = 0; i < inputsArray.length; i++){
        inputsArray[i].value = "";
    }
}

