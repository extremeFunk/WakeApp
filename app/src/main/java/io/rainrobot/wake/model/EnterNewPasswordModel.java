package io.rainrobot.wake.model;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.client.ResetPasswordClient;

public class EnterNewPasswordModel implements IModel {

    private ResetPasswordClient client;

    public EnterNewPasswordModel(ResetPasswordClient client) {
        this.client = client;
    }

    public void sendNewPasswordAndToken (String token, String password) {
        client.resetPassword(token, password);
    }
}
