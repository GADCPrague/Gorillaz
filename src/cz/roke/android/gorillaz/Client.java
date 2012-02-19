package cz.roke.android.gorillaz;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import com.google.gson.Gson;



import android.util.Log;

public class Client {

	private SocketChannel channel;
	private StringBuffer buffer;
	
	protected String separator;
	protected ByteBuffer readBuffer, writeBuffer;
	protected CharsetDecoder decoder;
	
	public ClientPacket netSend;
	
	Gson gson = new Gson();

	public Client() {
		this.channel = channel;
		this.buffer = new StringBuffer();
		
		readBuffer = ByteBuffer.allocateDirect(500);
		System.out.println("Capacity of read buffer: " + readBuffer.limit());

		writeBuffer = ByteBuffer.allocateDirect(500);
		System.out.println("Capacity of write buffer: " + writeBuffer.limit());

		decoder = Charset.defaultCharset().newDecoder();
		
		netSend = new ClientPacket();
		separator = "\r\n";
	}

	public SocketChannel getChannel() {
		return channel;
	}

	public StringBuffer getBuffer() {
		return buffer;
	}
	
	public void connect() {
		try {
			channel = SocketChannel.open(new InetSocketAddress("192.168.200.117", 27000));
			channel.configureBlocking(false);

		} catch (IOException e) {
			Log.w("SIT", "Nelze se připojit k serveru " + e.getMessage());
			channel = null;
		}
	}
	
	public boolean isConnect() {
		if (channel == null)
			return false;
		
		return true;
	}
	
	public void prepareWriteBuffer(String message, boolean wrap) {
		writeBuffer.clear();
		message = message + (wrap ? separator : "");

		// Message is longer than write buffer capacity
		if (message.length() > writeBuffer.limit()) {
			Log.w("SIT", "Prilis dlouha zprava");
		} else {
			writeBuffer.put(message.getBytes());
		}
	}
	
	public void send() {
		if (channel != null) {
			
			prepareWriteBuffer(gson.toJson(netSend), true);
		
			writeBuffer.flip();

			try {
				channel.write(writeBuffer);
			}

			catch (IOException ioe) {
				Log.w("SIT", "Nelze odeslat");
			}
			
		}
	}

}
