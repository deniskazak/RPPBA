function showReceiveBox(sender, message) {
    document.getElementById("responseField").style.border = "1px black solid";
    document.getElementById("toBox").style.border = "1px black solid";
    document.getElementById("myModal").style.display = "block";
    document.getElementById("receiveField").style.display = "block";
    document.getElementById("receiveField").innerHTML = message;
    document.getElementById("responseLabel").style.display = "block";
    document.getElementById("toBox").value = sender;
    document.getElementById("toBox").disabled = true;

}

function showSendBox() {
    document.getElementById("responseField").style.border = "1px black solid";
    document.getElementById("toBox").style.border = "1px black solid";
    document.getElementById("myModal").style.display = "block";
    document.getElementById("receiveField").style.display = "none";
    document.getElementById("responseLabel").style.display = "none";
    document.getElementById("toBox").disabled = false;
    document.getElementById("toBox").value = "";
}

function closeModal() {
    document.getElementById("success").style.display = "none";
    document.getElementById("info").style.display = "block";
    document.getElementById("myModal").style.display = "none";
}

function sendMessage() {
    var to = document.getElementById("toBox").value;
    if (to.trim() == "") {
        document.getElementById("toBox").style.border = "1px red solid";
        return;
    } else {
        document.getElementById("toBox").style.border = "1px black solid";
    }
    var message = encodeURIComponent(document.getElementById("responseField").value);
    if (message.trim() == "") {
        document.getElementById("responseField").style.border = "1px red solid";
        return;
    } else {
        document.getElementById("responseField").style.border = "1px black solid";
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("responseField").value = "";
            if (this.responseText == ""){
                document.getElementById("success").style.display = "block";
                document.getElementById("info").style.display = "none";
                return;
            } else {
                var response = JSON.parse(this.responseText);
                var error = response.error;
                document.getElementById("toBox").value = error;
                return;
            }
        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=sendMessage&message=" + message + "&to=" + to, true);
    xhttp.send();
}


function deleteMessage(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var message = document.getElementById(id);
            message.parentNode.removeChild(message);
        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=deleteMessage&id=" + id, true);
    xhttp.send();
}


