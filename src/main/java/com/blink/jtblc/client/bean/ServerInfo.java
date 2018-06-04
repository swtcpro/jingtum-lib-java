package com.blink.jtblc.client.bean;

/**
 * 服务器信息
 */
public class ServerInfo {
    private String version;


    private String ledgers;


    private String node;


    private String state;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLedgers() {
        return ledgers;
    }

    public void setLedgers(String ledgers) {
        this.ledgers = ledgers;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
