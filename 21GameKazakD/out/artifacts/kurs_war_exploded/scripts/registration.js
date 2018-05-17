/**
 * Created by Коля on 14.03.2017.
 */
function validateForm() {
    clearInfo();
    if (validateEmail() && validateNickname() && validatePassword()) {
        return true;
    } else {
        return false;
    }
}
function validateEmail() {
    var pattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var email = document.querySelector("input[type=email]");
    var res = pattern.test(email.value);
    if (res == false) {
        document.getElementById("emailError").style.display = "inline";
        return false;
    } else {
        return true;
    }
}

function validateNickname() {
    var nickname = document.registrationForm.nickName.value;
    var pattern = /^[A-Za-z][A-Za-z0-9]+$/;
    if (nickname.match(pattern)) {
        return true;
    }
    else {
        document.getElementById("nicknameError").style.display = "inline";
        document.registrationForm.nickName.focus();
        return false;
    }
}

function validatePassword() {
    var pass = document.registrationForm.password.value;
    var repeat = document.registrationForm.repeat.value;
    var pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    if (pass.match(pattern)) {
        if (pass == repeat) {
            return true;
        } else {
            document.getElementById("passwordMatchError").style.display = "inline";
            document.registrationForm.password.focus();
            return false;
        }
    }
    else {
        document.getElementById("passwordError").style.display = "inline";
        document.registrationForm.password.focus();
        return false;
    }
}

function clearInfo() {
    var info = document.getElementsByClassName("info");
    var i;
    for (i = 0; i < info.length; i++) {
        info[i].style.display = "none";
    }
    document.getElementById("serverError").innerHTML = "";
    document.getElementById("serverError").style.display = "inline";
}



