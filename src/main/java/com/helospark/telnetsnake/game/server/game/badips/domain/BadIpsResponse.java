package com.helospark.telnetsnake.game.server.game.badips.domain;

public class BadIpsResponse {
    private String err;
    private String suc;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    @Override
    public String toString() {
        return "BadIpsResponse [err=" + err + ", suc=" + suc + "]";
    }

}
