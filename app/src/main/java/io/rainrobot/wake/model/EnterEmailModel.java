package io.rainrobot.wake.model;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.app.UserInputExeption;
import io.rainrobot.wake.client.ResetPasswordClient;

public class EnterEmailModel implements IModel {
    private ResetPasswordClient client;

    public EnterEmailModel(ResetPasswordClient client) {
        this.client = client;
    }

    public void sendEmail(String email) {
        if (email == "") throw new UserInputExeption("Please enter an email");
        client.sendEmailRequest(email);
    }

}
