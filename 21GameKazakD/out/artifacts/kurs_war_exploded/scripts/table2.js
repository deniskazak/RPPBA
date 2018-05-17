/**
 * Created by Коля on 02.04.2017.
 */

(function searchGame() {
    var timer = setInterval(function () {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            var response = JSON.parse(this.responseText);
            if (response.isGame == "true") {
                document.getElementById("waitModal").style.display = "none";
                window.clearInterval(timer);
                return;
            }

        };
        xhttp.open("POST", "http://localhost:8080/controller?command=checkGame", true);
        xhttp.send();
    }, 2000);
})();

function waitSecondPlayer() {
    document.getElementById("waitModal").style.display = "block";
    var timer = setInterval(function () {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            var response = JSON.parse(this.responseText);
            if (response.isReady == "true") {
                document.getElementById("waitModal").style.display = "none";
                window.clearInterval(timer);
                showSecondPlayerCards();
                return;
            }
        };
        xhttp.open("POST", "http://localhost:8080/controller?command=waitSecondPlayer", true);
        xhttp.send();
    }, 2000);
}

function showSecondPlayerCards() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        var game = JSON.parse(this.responseText);
        for (var i = 0; i < game.secondPlayerCardSize; i++) {
            if (i == 0) {
                document.getElementById("secondCard1").setAttribute("src", game.secondPlayerCards[i]);
            } else if (i == 1) {
                document.getElementById("secondCard2").setAttribute("src", game.secondPlayerCards[i]);
            } else {
                var check = document.getElementById("secondCard" + (i + 1));
                if (check != null) break;
                var card = createCard("secondCard" + (i + 1));
                document.getElementById("secondPlayer").appendChild(card);
                document.getElementById("secondCard" + (i + 1)).setAttribute("src", game.secondPlayerCards[i]);
                document.getElementById("secondCard" + (i + 1)).style.border = "solid rgb(40,40,48)";
            }

        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=showSecondPlayerCards", true);
    xhttp.send();
}


function bet2(value) {
    clearTable();
    var cash = document.getElementById("cashField").value;
    var bet = document.getElementById("betField").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            document.getElementById("firstLegend").innerHTML = response.firstPlayer;
            document.getElementById("secondLegend").innerHTML = response.secondPlayer;
            document.getElementById("betField").value = response.bet;
            document.getElementById("cashField").value = response.cash;
            if (response.bet != "0") {
                document.getElementById("deal").style.display = "block";
            }
        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=bet" +
        "&value=" + value + "&cash=" + cash + "&bet=" + bet, true);
    xhttp.send();

}

function clearTable() {
    var info = document.getElementsByClassName("info");
    var i;
    for (i = 0; i < info.length; i++) {
        info[i].style.display = "none";
    }
    var firstCardPane = document.getElementById("firstPlayer");
    var secondCardPane = document.getElementById("secondPlayer");
    var enemyCardPane = document.getElementById("enemyCardPane");
    var firstCardPaneNodes = firstCardPane.getElementsByTagName("div");
    for (var i = 0; i < firstCardPaneNodes.size; i++) {
        firstCardPane.removeChild(firstCardPaneNodes[i]);
    }
    var secondCardPaneNodes = secondCardPane.getElementsByTagName("div");
    for (var i = 0; i < secondCardPaneNodes.size; i++) {
        secondCardPane.removeChild(secondCardPaneNodes[i]);
    }
    while (enemyCardPane.firstChild) {
        enemyCardPane.removeChild(enemyCardPane.firstChild);
    }

    var myCard1 = createCard("myCard1");
    document.getElementById("firstPlayer").appendChild(myCard1);
    var myCard2 = createCard("myCard2");
    document.getElementById("firstPlayer").appendChild(myCard2);
    var secondPlayerCard1 = createCard("secondCard1");
    document.getElementById("secondPlayer").appendChild(secondPlayerCard1);
    var secondPlayerCard2 = createCard("secondCard2");
    document.getElementById("secondPlayer").appendChild(secondPlayerCard2);
    var enemyCard1 = createCard("enemyCard1");
    document.getElementById("enemyCardPane").appendChild(enemyCard1);
    var enemyCard2 = createCard("enemyCard2");
    document.getElementById("enemyCardPane").appendChild(enemyCard2);
    firstCardPane.style.visibility = "hidden";
    secondCardPane.style.visibility = "hidden";
    enemyCardPane.style.visibility = "hidden";
}

function createCard(cardId) {
    var div = document.createElement("div");
    div.className = "inner";
    var img = document.createElement("img");
    img.className = "card";
    img.id = cardId;
    img.style.width = "8vw";
    img.style.height = "20vh";
    div.appendChild(img);
    return div;
}

function deal2() {
    var xhttp = new XMLHttpRequest();
    var bet = document.getElementById("betField").value;
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var game = JSON.parse(this.responseText);
            document.getElementById("firstPlayer").style.visibility = "visible";
            document.getElementById("secondPlayer").style.visibility = "visible";
            document.getElementById("enemyCardPane").style.visibility = "visible";
            document.getElementById("secondCard1").setAttribute("src", "/images/cards/cardobr.jpg");
            document.getElementById("secondCard1").style.border = "4px solid #282830";
            document.getElementById("secondCard2").setAttribute("src", "/images/cards/cardobr.jpg");
            document.getElementById("secondCard2").style.border = "4px solid #282830";
            document.getElementById("myCard1").setAttribute("src", game.myCards[0]);
            document.getElementById("myCard1").style.border = "4px solid #282830";
            document.getElementById("myCard2").setAttribute("src", game.myCards[1]);
            document.getElementById("myCard2").style.border = "4px solid #282830";
            document.getElementById("enemyCard1").setAttribute("src", game.enemyCards[0]);
            document.getElementById("enemyCard1").style.border = "4px solid #282830";
            document.getElementById("enemyCard2").setAttribute("src", game.enemyCards[1]);
            document.getElementById("enemyCard2").style.border = "4px solid #282830";
            document.getElementById("tokenForm").style.display = "none";
            document.getElementById("deal").style.display = "none";
            document.getElementById("deposit").style.visibility = "hidden";
            document.getElementById("hit").style.display = "inline";
            document.getElementById("stand").style.display = "inline";
            document.getElementById("myPointsLabel").style.display = "inline";

            if (game.isMyBlackJack == "true") {
                document.getElementById("myPointsLabel").style.display = "none";
                if (game.isEnemyBlackJack == "true") {
                    showEnemyCards(game);
                    document.getElementById("drawBlackJackLabel").style.display = "inline";
                    document.getElementById("hit").style.display = "none";
                    document.getElementById("stand").style.display = "none";
                    var money = document.getElementById("cashField").value;
                    var bet = document.getElementById("betField").value;
                    var res = 1 * money + 1 * bet;
                    document.getElementById("cashField").value = res;
                    document.getElementById("betField").value = 0;
                    waitSecondPlayer();
                }
                else {
                    document.getElementById("winBlackJackLabel").style.display = "inline";
                    document.getElementById("hit").style.display = "none";
                    document.getElementById("stand").style.display = "none";
                    var money = document.getElementById("cashField").value;
                    var bet = document.getElementById("betField").value;
                    var res = 1 * money + 3 * bet;
                    document.getElementById("cashField").value = res;
                    document.getElementById("betField").value = 0;
                    waitSecondPlayer();
                }
            }
            else {
                document.getElementById("myPointsLabel").innerHTML = game.myPoints;
            }
        }
    };
    xhttp.open("POST", "http://localhost:8080/controller?command=deal" + "&bet=" + bet + "&isMultiplayer=true", true);
    xhttp.send();
}

function hit2() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var game = JSON.parse(this.responseText);
            var div = document.createElement("div");
            div.className = "inner";
            var img = document.createElement("img");
            img.className = "card";
            img.id = "myCard" + game.cardId;
            img.setAttribute("src", game.myCard);
            img.style.border = "4px solid #282830";
            div.appendChild(img);
            document.getElementById("firstPlayer").appendChild(div);
            var points = game.myPoints;
            if (points > 21) {
                document.getElementById("myPointsLabel").style.display = "none";
                document.getElementById("bustedLabel").style.display = "inline-block";
                document.getElementById("tokenForm").style.display = "none";
                document.getElementById("hit").style.display = "none";
                document.getElementById("stand").style.display = "none";
                document.getElementById("betField").value = 0;
                waitSecondPlayer();
            } else if (points == 21) {
                document.getElementById("myPointsLabel").innerHTML = points.toString();
                stand2();
            } else {
                document.getElementById("myPointsLabel").innerHTML = points;
            }
        }
    };
    xhttp.open("GET", "http://localhost:8080/controller?command=hit", true);
    xhttp.send();
}

function stand2() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var game = JSON.parse(this.responseText);
            document.getElementById("myPointsLabel").style.display = "none";
            if (game.isEnough == "true") {
                showEnemyCards(game);
                if (game.enemyPoints > 21) {
                    document.getElementById("winLabel").style.display = "inline";
                    document.getElementById("hit").style.display = "none";
                    document.getElementById("stand").style.display = "none";
                    var money = document.getElementById("cashField").value;
                    var bet = document.getElementById("betField").value;
                    var res = 1 * money + 2 * bet;
                    document.getElementById("cashField").value = res;
                    document.getElementById("betField").value = 0;
                    waitSecondPlayer();
                }
                else if (game.enemyPoints > game.myPoints) {
                    document.getElementById("loseLabel").style.display = "inline";
                    document.getElementById("hit").style.display = "none";
                    document.getElementById("stand").style.display = "none";
                    document.getElementById("betField").value = 0;
                    waitSecondPlayer();
                }
                else if (game.enemyPoints == game.myPoints) {
                    if (game.isEnemyBlackJack == "false") {
                        document.getElementById("drawLabel").style.display = "inline";
                        document.getElementById("hit").style.display = "none";
                        document.getElementById("stand").style.display = "none";
                        var money = document.getElementById("cashField").value;
                        var bet = document.getElementById("betField").value;
                        var res = 1 * money + 1 * bet;
                        document.getElementById("cashField").value = res;
                        document.getElementById("betField").value = 0;
                        waitSecondPlayer();
                    }
                    else {
                        document.getElementById("loseBlackJackLabel").style.display = "inline";
                        document.getElementById("hit").style.display = "none";
                        document.getElementById("stand").style.display = "none";
                        document.getElementById("betField").value = 0;
                        waitSecondPlayer();
                    }

                }
                else if (game.enemyPoints < game.myPoints) {
                    document.getElementById("winLabel").style.display = "inline";
                    document.getElementById("hit").style.display = "none";
                    document.getElementById("stand").style.display = "none";
                    var money = document.getElementById("cashField").value;
                    var bet = document.getElementById("betField").value;
                    var res = 1 * money + 2 * bet;
                    document.getElementById("cashField").value = res;
                    document.getElementById("betField").value = 0;
                    waitSecondPlayer();
                }
                return;
            }
            else {
                xhttp.open("GET", "http://localhost:8080/controller?command=stand", true);
                xhttp.send();
            }
        }
    };
    xhttp.open("GET", "http://localhost:8080/controller?command=stand", true);
    xhttp.send();
}

function showEnemyCards(game) {
    for (var i = 0; i < game.enemyCardSize - 1; i++) {
        if (i == 0) {
            document.getElementById("enemyCard2").setAttribute("src", game.enemyCards[i]);
        }
        else {
            var card = createCard("enemyCard" + (i + 2));
            document.getElementById("enemyCardPane").appendChild(card);
            document.getElementById("enemyCard" + (i + 2)).setAttribute("src", game.enemyCards[i]);
            document.getElementById("enemyCard" + (i + 2)).style.border = "solid rgb(40,40,48)";
        }
    }
}






