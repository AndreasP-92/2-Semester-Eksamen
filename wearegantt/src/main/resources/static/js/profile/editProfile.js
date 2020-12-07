var myInput = document.getElementById("pass");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
myInput.onfocus = function() {
    document.getElementById("checksField").style.display = "block";
};
myInput.onblur = function() {
    document.getElementById("checksField").style.display = "none";
};
myInput.onkeyup = function() {
    var lowerCaseLetters = /[a-z]/g;
    if (myInput.value.match(lowerCaseLetters)) {
        letter.classList.remove("wrong");
        letter.classList.add("correct");
    } else {
        letter.classList.remove("correct");
        letter.classList.add("wrong");
    }
    var upperCaseLetters = /[A-Z]/g;
    if (myInput.value.match(upperCaseLetters)) {
        capital.classList.remove("wrong");
        capital.classList.add("correct");
    } else {
        capital.classList.remove("correct");
        capital.classList.add("wrong");
    }
    var numbers = /[0-9]/g;
    if (myInput.value.match(numbers)) {
        number.classList.remove("wrong");
        number.classList.add("correct");
    } else {
        number.classList.remove("correct");
        number.classList.add("wrong");
    }
};