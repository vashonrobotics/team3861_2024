package org.firstinspires.ftc.teamcode.core;

public class Button {
    private boolean state;
    private boolean changed;

    public void update(boolean state) {
        if(state == this.state) {
             changed = false;
             return;
        }
        changed = true;
        this.state = state;
    }

    public boolean pressed() {
        return state && changed;
    }

    public boolean released() {
        return !state && changed;
    }

    public boolean getState() {
        return state;
    }
}
