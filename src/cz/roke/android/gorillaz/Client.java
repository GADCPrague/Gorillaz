package cz.roke.android.gorillaz;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

import android.util.Log;

import com.google.gson.Gson;

public class Client {

	private SocketChannel channel;
	private StringBuffer buffer;

	protected String separator;
	protected ByteBuffer readBuffer, writeBuffer;
	protected CharsetDecoder decoder;

	public ClientPacket netSend;

	Gson gson = new Gson();

	public Client() {
		this.channel = null;
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
		if (channel == null) {
			try {
				channel = SocketChannel.open(new InetSocketAddress("192.168.200.68", 26000));
				channel.configureBlocking(false);

			} catch (IOException e) {
				Log.w("SIT", "Nelze se připojit k serveru " + e.getMessage());
				channel = null;
			}
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

	public void update() throws IOException {
		if (channel != null) {

			StringBuffer sb = buffer;

			ArrayList<String> messages = read(channel, sb);
			if (messages != null) {
				for (String mm : messages) {
					if (readMessage(mm)) {
						channel.close();
						channel = null;
					}
				}
			} else

			{
				channel.close();
				channel = null;
			}

		}
	}

	public ArrayList<String> read(SocketChannel channel, StringBuffer sb)
			throws IOException {
		ArrayList<String> ret = new ArrayList<String>();

		readBuffer.clear();
		int length = channel.read(readBuffer);

		// Message
		if (length > 0) {
			readBuffer.flip();
			sb.append(decoder.decode(readBuffer).toString());

			int index;
			while ((index = sb.indexOf(separator)) != -1) {
				String message = sb.substring(0, index);
				sb = sb.delete(0, index + separator.length());
				ret.add(message);
			}
		} else

		// Client is disconnected
		if (length == -1) {
			channel.close();
			ret = null;
		}

		return ret;
	}

	public boolean readMessage(String message) {

		//Log.i("SIT", message);
		ServerPacket packet = gson.fromJson(message, ServerPacket.class);

		//Log.i("SIT", "" + packet.gorilky.size());
		GameView.gorilkaArray = packet.gorilky;
		GameView.balls = packet.koule;

		return false;
	}

}
