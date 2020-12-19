document.addEventListener("DOMContentLoaded", function(event) {
    button();

});
function button(){
    document.getElementById('deleteSubmitButton').addEventListener('click',function(event){
        event.preventDefault();
        const registerForm  = document.querySelector('#deleteForm');

        if (confirm('Are you sure?')) {
            registerForm.submit();
        }
    });
}