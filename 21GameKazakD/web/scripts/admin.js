/**
 * Created by Коля on 13.03.2017.
 */
function banUser(userId) {
    var xhttp = new XMLHttpRequest();
    var isBanned = document.getElementById(userId).getElementsByTagName("td")[3].textContent;
    if (isBanned == "false") {
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var tds = document.getElementById(userId).getElementsByTagName("td");
                tds[3].textContent = "true";
            }
        };
        xhttp.open("POST", "http://localhost:8080/controller?command=banUser&value=true&id=" + userId, true);
        xhttp.send();
    } else {
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var tds = document.getElementById(userId).getElementsByTagName("td");
                tds[3].textContent = "false";
            }
        };
        xhttp.open("POST", "http://localhost:8080/controller?command=banUser&value=false&id=" + userId, true);
        xhttp.send();
    }
}

function deleteUser(userId) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var user = document.getElementById(userId);
            user.parentNode.removeChild(user);
        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=deleteUser&id=" + userId,true);
    xhttp.send();
}
