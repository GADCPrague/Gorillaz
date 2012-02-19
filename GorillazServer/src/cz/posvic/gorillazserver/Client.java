package cz.posvic.gorillazserver;

import java.nio.channels.SocketChannel;

import javax.imageio.ImageIO;

public class Client {

	private SocketChannel channel;
	private StringBuffer buffer;

	public Gorilka gorilka;
	
	public Client(SocketChannel channel) {
		gorilka = new Gorilka(100, 100);
		
		this.channel = channel;
		this.buffer = new StringBuffer();
	}

	public SocketChannel getChannel() {
		return channel;
	}

	public StringBuffer getBuffer() {
		return buffer;
		

	}

}
