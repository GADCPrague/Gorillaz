package cz.posvic.gorillazserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

public class Communication {

	protected String separator;
	protected ByteBuffer readBuffer, writeBuffer;
	protected CharsetDecoder decoder;

	public Communication(String messageEnd, int rbSize, int wbSize) {

		// Default
		this.separator = messageEnd;

		// Minimal size of buffer
		int min = messageEnd.length();
		if (rbSize < min) {
			rbSize = min;
		}

		if (wbSize < min) {
			wbSize = min;
		}

		readBuffer = ByteBuffer.allocateDirect(rbSize);
		System.out.println("Capacity of read buffer: " + readBuffer.limit());

		writeBuffer = ByteBuffer.allocateDirect(wbSize);
		System.out.println("Capacity of write buffer: " + writeBuffer.limit());

		decoder = Charset.defaultCharset().newDecoder();
	}

	public void prepareWriteBuffer(String message) throws TooLongMessageException {
		prepareWriteBuffer(message, true);
	}

	public void prepareWriteBuffer(String message, boolean wrap) throws TooLongMessageException {
		writeBuffer.clear();
		message = message + (wrap ? separator : "");

		// Message is longer than write buffer capacity
		if (message.length() > writeBuffer.limit()) {
			throw new TooLongMessageException();
		} else {
			writeBuffer.put(message.getBytes());
		}
	}

	public void send(SocketChannel channel) {
		writeBuffer.flip();

		try {
			channel.write(writeBuffer);
		}

		catch (IOException ioe) {
			System.err.println("" + ioe);
		}
	}

	public ArrayList<String> read(SocketChannel channel, StringBuffer sb) throws IOException {
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
}
