package io.rainrobot.wake.app;


public interface IView<T extends AbstractController> {

    void show();
    void showMsg(String string);
    void setController(T controller);
    void startThinkMode();
    void stopThinkMode();
}
