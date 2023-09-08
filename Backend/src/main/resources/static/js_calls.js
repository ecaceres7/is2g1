function llamarMetodoJava() {
    // WARNING: For POST requests, body is set to null by browsers.
    var data = JSON.stringify({
        "username": "mod",
        "password": "12345678"
    });

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            console.log(this.responseText);
        }
    });

    xhr.open("POST", "http://localhost:8080/api/auth/signin");
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.send(data);
}