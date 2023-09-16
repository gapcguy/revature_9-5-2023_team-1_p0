document.addEventListener("DOMContentLoaded", function() {
// Get HTML elements by ID.
const usernameField         = document.getElementById("username")
const passwordField         = document.getElementById("password");
const passwordRepeatField   = document.getElementById("passwordRepeat");
const regBtn                = document.getElementById("registerBtn");

// Get references to error message placeholders
const usernameError         = document.getElementById("usernameErr");
const passwordError         = document.getElementById("passwordErr");
const passwordLenErr        = document.getElementById("passwordLengthErr");

// Minimum length requirement
const minlength             = 4;

// Handles errors and enable/disable action of the register button.
function throwError(element, errorMessage ) {
    if (errorMessage !== "") {
        element.textContent = errorMessage;
        element.style.display = "block";
        regBtn.disabled = true; }
    else {
        element.textContent = "";
        element.style.display = "none";
        regBtn.disabled = false;
    }
}

// Check Username Length
function checkUsernameLength() {
    const uname        = usernameField.value;
    const unameLength  = uname.length;
    const errorMessage = unameLength < minlength ? "Your username must have more than 4 characters" : "";
    throwError(usernameError, errorMessage);
}

// Check password length.
function checkPasswordLength() {
    const pass                  = passwordField.value;
    const confirmPass           = passwordRepeatField.value;
    const passLength            = pass.length;
    const confirmPassLength     = confirmPass.length;
    const errorMessage = passLength < minlength ? "Your password must have more than 4 characters" : "";
    throwError(passwordLengthErr, errorMessage);
}

// Check that the 'password' and 'confirm password' fields match.
function checkPasswordEquals() {
    const pass                  = passwordField.value;
    const confirmPass           = passwordRepeatField.value;
    const errorMessage = pass !== confirmPass ? "Password mismatch. Try again!" : "";
    throwError(passwordErr, errorMessage);
}

// Event Listeners which trigger validation functions.
usernameField.addEventListener("input", checkUsernameLength);
passwordField.addEventListener("input", checkPasswordEquals);
passwordField.addEventListener("input", checkPasswordLength);
passwordRepeatField.addEventListener("input", checkPasswordEquals);
passwordRepeatField.addEventListener("input", checkPasswordLength);
});