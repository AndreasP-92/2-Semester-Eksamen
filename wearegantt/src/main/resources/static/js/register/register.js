document.addEventListener("DOMContentLoaded", function(event) {
    button();


});
function button(){
    document.getElementById('submitButton').addEventListener('click',function(event){
        event.preventDefault();
        const registerForm      = document.querySelector('#registerForm');
        const getPassword01     = document.querySelector("#password");
        const getPassword02     = document.querySelector("#confirm_password");
        const formValid         = document.forms["registerForm"].checkValidity();

        if(getPassword01.value != getPassword02.value) {
            document.getElementById('passwordValid').innerHTML = "Password has to be Identical";
        } if(formValid == false){
            document.getElementById('notFilled').innerHTML = "Please fill all fields";
        }
        if(formValid && getPassword01.value == getPassword02.value){
            registerForm.submit()
        }
    });
}