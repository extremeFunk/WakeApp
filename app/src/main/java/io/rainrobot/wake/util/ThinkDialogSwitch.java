package io.rainrobot.wake.util;

public class ThinkDialogSwitch {
    private static final int thinkModeMinimum = 1;
    private int thinkMode = thinkModeMinimum - 2;

    public boolean isShow() {
        //the below diagram represent a multiple request logic
        //-1 0 <- show-A 1 <- show-B  close-A -> 0 close-B -> -1 0 <- show-C
        thinkMode++;
        if(thinkMode < thinkModeMinimum) {
            return true;
        } else return false;
    }

    public boolean isClose() {
        if(thinkMode < thinkModeMinimum) {
            thinkMode--;
            return true;
        } else {
            thinkMode--;
            return false;
        }
    }
}
