package mesh.xmpp.net;

public class XmppClientInformation {
	private String jid = "";
	
	private String username = "";
	private String password = "";
	
	private String domain = "";
	private String resource = "";
	
	private String mechanism = null ; 
	
	private boolean isConnect = false ;
	
	private boolean isLogin = false ; 
	
	private boolean isBind = false ; 
	
	private boolean isSession = false ; 
	
	private boolean isAbailable = false ; 
	
	private String xml = "";

	public XmppClientInformation() {}

	public void setStreamUrl(String url) {
		if ( url != null && !url.isEmpty() && url.split("@").length == 2 ) {
			String split[] = url.split("@");
			
			if ( split[0] != null && !split[0].isEmpty() &&
					split[1] != null && !split[1].isEmpty() ) {
				this.username = split[0];
				
				if ( split[1].indexOf("/") != -1 ) {
					this.domain = split[1].substring(0,split[1].indexOf("/")); 
					this.resource = split[1].substring(split[1].indexOf("/") + 1,split[1].length());
				}else {
					this.domain = split[1] ; 
					this.resource = "";
				}

			}
		}
	}

	public String getMechanism() {
		return mechanism;
	}


	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}


	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public boolean isBind() {
		return isBind;
	}

	public void setBind(boolean isBind) {
		this.isBind = isBind;
	}

	public boolean isSession() {
		return isSession;
	}

	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public boolean isAbailable() {
		return isAbailable;
	}

	public void setAbailable(boolean isAbailable) {
		this.isAbailable = isAbailable;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	};
	
	
}
