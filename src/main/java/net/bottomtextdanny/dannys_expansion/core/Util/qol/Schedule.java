package net.bottomtextdanny.dannys_expansion.core.Util.qol;

public class Schedule {
    private int timer;

    public void tryDown() {
        if (this.timer > 0) this.timer--;
    }

    public void setSleep(int i) {
        this.timer = i;
    }

    public void sleepForNow() {
        this.timer = 1;
    }

    public void wake() {
        this.timer = 0;
    }

    public boolean isWoke() {
        return this.timer == 0;
    }
}
