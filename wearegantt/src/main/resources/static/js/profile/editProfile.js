document.addEventListener("DOMContentLoaded", function(event) {
    button();



});
function button(){
    document.querySelector("#submitButton").addEventListener('click',function(event){
        event.preventDefault();
        const registerForm1      = document.getElementById('registerForm');
        console.log(registerForm1)

        const getPassword01     = document.querySelector("#password");
        const getPassword02     = document.querySelector("#confirm_password");
        if(getPassword01.value != getPassword02.value) {

            document.getElementById('passwordValid').innerHTML = "Password has to be Identical";
        }else if(getPassword01.value == getPassword02.value){
            registerForm1.submit()
        }
    });
}