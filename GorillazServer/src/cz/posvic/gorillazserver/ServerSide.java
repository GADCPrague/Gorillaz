package cz.posvic.gorillazserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

public class ServerSide extends Communication implements Runnable {

	public static void main(String[] args) {
		ServerSide ss = new ServerSide(27000);
		ss.exec();
	}

	public static String TAG = "Server";
	private ServerSocketChannel serverChannel;
	private ArrayList<Client> clients;
	
	Gson gson = new Gson();

	public ServerSide(int port) {
		super("\r\n", 512, 512);

		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
			System.out.println("Server is listening on port " + port);

			clients = new ArrayList<Client>();
		}

		catch (IOException ioe) {
			System.err.println("" + ioe);
		}

	}

	public void exec() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		// TODO
		while (true) {
			try {
				acceptNewClient();
				readMessage();
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
				}
			}

			catch (IOException ioe) {
				System.err.println("" + ioe);
			}
		}

	}

	private void acceptNewClient() throws IOException {
		SocketChannel clientChannel = serverChannel.accept();
		if (clientChannel == null) {
			return;
		}

		clientChannel.configureBlocking(false);
		acceptUser(clientChannel);
	}

	private void readMessage() throws IOException {
		Iterator<Client> i = clients.iterator();
		while (i.hasNext()) {
			Client user = i.next();
			SocketChannel channel = user.getChannel();
			StringBuffer sb = user.getBuffer();

			ArrayList<String> messages = read(channel, sb);
			if (messages != null) {
				for (String mm : messages) {
					if (readMessage(user, mm)) {
						userQuit(user);
						channel.close();
						i.remove();
					}
				}
			} else

			{
				channel.close();
				i.remove();
			}
		}
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public void sendBroadcast() {
		for (Client user : clients) {
			send(user.getChannel());
		}
	}

	public void sendBroadcast(Client except) {
		for (Client user : clients) {
			SocketChannel channel = user.getChannel();
			if (!channel.equals(except.getChannel())) {
				send(user.getChannel());
			}
		}
	}

	// -------------------------------------------------------------------------

	public void acceptUser(SocketChannel channel) {
		System.out.println("pridavam klienta");
		clients.add(new Client(channel));
	}

	public boolean readMessage(Client client, String message) {
			
		ClientPacket packet = gson.fromJson(message, ClientPacket.class);

		return false;
	}

	public void userQuit(Client client) {

	}

}
