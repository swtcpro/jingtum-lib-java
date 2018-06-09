package com.blink.jtblc.client.bean;

/**
 * 服务器信息
 */
public class ServerInfo {
    private String buildVersion;//服务器部署项目版本


    private String completeLedgers;//账本区间


    private String pubkeyNode;//节点公钥


    private String serverState;//服务器状态

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getCompleteLedgers() {
        return completeLedgers;
    }

    public void setCompleteLedgers(String completeLedgers) {
        this.completeLedgers = completeLedgers;
    }

    public String getPubkeyNode() {
        return pubkeyNode;
    }

    public void setPubkeyNode(String pubkeyNode) {
        this.pubkeyNode = pubkeyNode;
    }

    public String getServerState() {
        return serverState;
    }

    public void setServerState(String serverState) {
        this.serverState = serverState;
    }
}
