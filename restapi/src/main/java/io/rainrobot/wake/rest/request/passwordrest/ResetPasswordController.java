package io.rainrobot.wake.rest.request.passwordrest;

import java.util.Optional;
import java.util.Random;

import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppUser;
import io.rainrobot.wake.rest.configuration.appuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class ResetPasswordController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    // Process form submission from forgotPassword page
    @PostMapping(value = Path.RESETPASSWORD)
    public void sendEmailRequest(@RequestBody String userEmail) {

        Optional<AppUser> optional = userService.findByEmail(userEmail);

        if (!optional.isPresent()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "We didn't find an account for that e-mail address.");
        } else {
            AppUser user = optional.get();

            user.setResetToken(new Random().nextInt(999999));

            userService.save(user);

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("wakeappsup@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");

            passwordResetEmail.setText("your code for resetting the password is:\n"
                    + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);
        }
    }

    @PutMapping(value = Path.RESETPASSWORD + "/{token}")
    public void setNewPassword(@PathVariable int token, @RequestBody String password) {

        Optional<AppUser> user = userService.findByPasswordResetToken(token);

        if (user.isPresent()) {
            AppUser resetUser = user.get();
            resetUser.setPassword(passwordEncoder.encode(password));
            resetUser.setResetToken(null);
            userService.save(resetUser);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Token is invalid");
        }
    }
}