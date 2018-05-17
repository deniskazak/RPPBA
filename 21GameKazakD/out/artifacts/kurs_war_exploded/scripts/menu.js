/**
 * Created by Коля on 13.03.2017.
 */
function logOut() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8080/controller?command=logOut", true);
    xhttp.send();
}

function changeLanguage(lang) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            window.location.reload(true);
        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=changeLanguage&language=" + lang, true);
    xhttp.send();
}
