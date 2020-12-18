document.addEventListener("DOMContentLoaded", function(event) {
    button();


});
function button(){
    document.getElementById('submitButton').addEventListener('click',function(event){
        event.preventDefault();
        const registerForm      = document.querySelector('#registerForm');
        const getPassword01     = document.querySelector("#password");
        const getPassword02     = document.querySelector("#confirm_password");
        if(getPassword01.value != getPassword02.value) {

            document.getElementById('passwordValid').innerHTML = "Password has to be Identical";
        }else{
            registerForm.submit()
        }
    });
}