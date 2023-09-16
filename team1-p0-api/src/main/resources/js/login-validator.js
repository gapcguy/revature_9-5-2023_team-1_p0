document.addEventListener("DOMContentLoaded", function() {
    // Get HTML elements by ID.
    const usernameField         = document.getElementById("username")
    const passwordField         = document.getElementById("password");
    const loginBtn              = document.getElementById("loginBtn");

    // Get references to error message placeholders
    const usernameErr         = document.getElementById("usernameErr");
    const passwordLenErr      = document.getElementById("passwordLengthErr");

    // Minimum length requirement
    const minLength             = 4;

    // Handles errors and enable/disable action of the register button.
    function throwError(element, errorMessage ) {
        if (errorMessage !== "") {
            element.textContent = errorMessage;
            element.style.display = "block";
            loginBtn.disabled = true; }
        else {
            element.textContent = "";
            element.style.display = "none";
            loginBtn.disabled = false;
        }
    }

    // Check Username Length
    function checkUsernameLength() {
        const uname                 = usernameField.value;
        const unameLength           = uname.length;
        let   errorMessage = "";

        if (unameLength === 0) {
            errorMessage = "You have not entered a username.";
        } else if (unameLength < minLength) {
            errorMessage = "Your username must contain more than 4 characters.";
        }

        throwError(usernameErr, errorMessage);
    }

    // Check password length.
    function checkPasswordLength() {
       const pass                  = passwordField.value;
       const passLength            = pass.length;
       let errorMessage = "";

       if (passLength === 0) { errorMessage = "You have not entered a password."; }
       else if (passLength < minLength) { errorMessage = "Your password must contain more than 4 characters."; }

       throwError(passwordLenErr, errorMessage);
    }

    // Event Listeners which trigger validation functions.
    usernameField.addEventListener("input", checkUsernameLength);
    passwordField.addEventListener("input", checkPasswordLength);
});