package com.blakebr0.cucumber.network.message;

import java.util.function.IntSupplier;

public abstract class LoginMessage<T extends Message<T>> extends Message<T> implements IntSupplier {
    private int loginIndex;

    @Override
    public int getAsInt() {
        return this.loginIndex;
    }

    public int getLoginIndex() {
        return this.loginIndex;
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }
}
