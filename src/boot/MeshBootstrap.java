package boot;

import mesh.xmpp.net.XmppBootstrap;

public class MeshBootstrap {
	
	
	public static void main(String[] args) {
		XmppBootstrap xmppBootstrap = new XmppBootstrap();
		xmppBootstrap.run();
	}
} 
