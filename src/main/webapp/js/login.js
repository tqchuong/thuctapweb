// SHOW/HIDE PASSWORD FUNCTION
const togglePasswordVisibility = (passwordId, eyeIconId) => {
    const input = document.getElementById(passwordId),
          iconEye = document.getElementById(eyeIconId);
 
    if (input && iconEye) {
        iconEye.addEventListener('click', () => {
            input.type = input.type === 'password' ? 'text' : 'password';
            iconEye.classList.toggle('ri-eye-fill');
            iconEye.classList.toggle('ri-eye-off-fill');
        });
    }
 };
 
 // Apply show/hide password for login and register forms
 togglePasswordVisibility('login-password', 'loginPassword');         // Login password visibility toggle
 togglePasswordVisibility('passwordCreate', 'loginPasswordCreate');   // Register password visibility toggle
 
 // SWITCH BETWEEN LOGIN, REGISTER, AND FORGOT PASSWORD FORMS
 const loginForm = document.getElementById('loginForm');
 const registerForm = document.getElementById('registerForm');
 const forgotPasswordForm = document.getElementById('forgotPasswordForm');
 
 if (loginForm && registerForm && forgotPasswordForm) {
     // Show Register Form
     document.getElementById('loginButtonRegister')?.addEventListener('click', (e) => {
        e.preventDefault();
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
        forgotPasswordForm.style.display = 'none';
     });
 
     // Show Login Form
     document.getElementById('loginButtonAccess')?.addEventListener('click', (e) => {
        e.preventDefault();
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        forgotPasswordForm.style.display = 'none';
     });
 
     // Show Forgot Password Form
     document.getElementById('forgotPasswordLink')?.addEventListener('click', (e) => {
        e.preventDefault();
        loginForm.style.display = 'none';
        registerForm.style.display = 'none';
        forgotPasswordForm.style.display = 'block';
     });
 
     // Back to Login from Forgot Password Form
     document.getElementById('backToLoginButton')?.addEventListener('click', (e) => {
        e.preventDefault();
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        forgotPasswordForm.style.display = 'none';
     });
 }
document.addEventListener("DOMContentLoaded", function () {
    const login__button = document.getElementById("signup-button");
    const otpForm = document.getElementById("otpForm");

    document.getElementById("signup-button").addEventListener("click", function () {
        registerForm.style.display = "none";
        otpForm.style.display = "block";
    });

    document.getElementById("backToLoginButton").addEventListener("click", function () {
        otpForm.style.display = "none";
        document.getElementById("registerForm").style.display = "block";
    });
});

 

 