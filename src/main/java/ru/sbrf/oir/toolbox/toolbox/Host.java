package ru.sbrf.oir.toolbox.toolbox;

public class Host {
	private String hostname;
	private String port;
	private String role;
	private String logPath;
	
	public String getLogPath() {
		return logPath;
	}
	public Host setLogPath(String logPath) {
		this.logPath = logPath;
		return this;
	}
	public String getHostname() {
		return hostname;
	}
	public Host setHostname(String hostname) {
		this.hostname = hostname;
		return this;
	}
	public String getPort() {
		return port;
	}
	public Host setPort(String port) {
		this.port = port;
		return this;
	}
	public String getRole() {
		return role;
	}
	public Host setRole(String role) {
		this.role = role;
		return this;
	}

}
