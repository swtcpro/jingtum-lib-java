package com.blink.jtblc.client.bean;

/**
 * 服务器信息
 */
public class JTServerInfo {
    private String buildVersion;
    private String completeLedgers;
    private String pubkeyNode;
    private String serverState;

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
