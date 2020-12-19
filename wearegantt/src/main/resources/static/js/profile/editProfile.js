document.addEventListener("DOMContentLoaded", function(event) {
    button();
    validateDelete()


});
function button(){
    document.querySelector("#submitButton").addEventListener('click',function(event){
        event.preventDefault();
        const registerForm1      = document.getElementById('registerForm');
        const getPassword01     = document.querySelector("#password");
        const getPassword02     = document.querySelector("#confirm_password");
        const formValid         = document.forms["registerForm"].checkValidity();

        if(getPassword01.value != getPassword02.value) {
            document.getElementById('passwordValid').innerHTML = "Password has to be Identical";
        } if(formValid == false){
            document.getElementById('notFilled').innerHTML = "Please fill all fields";
        }
        if(formValid){
            registerForm.submit()
        }
    });
}
function validateDelete(){
    document.getElementById('deleteSubmitButton').addEventListener('click',function(event){
        event.preventDefault();
        const registerForm  = document.querySelector('#deleteForm');

        if (confirm('Are you sure?')) {
            registerForm.submit();
        }
    });

}