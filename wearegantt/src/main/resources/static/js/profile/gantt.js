/* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "300px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

taskNumber();

function taskNumber(){
    const number = document.getElementsByClassName("number").length;

    for(i = 0; i < number; i++){
        document.getElementsByClassName("number")[i].innerHTML = i+1;
    }
}