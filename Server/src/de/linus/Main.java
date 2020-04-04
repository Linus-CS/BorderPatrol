package de.linus;

import java.net.UnknownHostException;

import de.linus.mexicanBorder.MBHandler;
import de.linus.server.Server;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException {
		new Server(new MBHandler());
	}
}
