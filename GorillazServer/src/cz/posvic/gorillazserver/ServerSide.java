package cz.posvic.gorillazserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

public class ServerSide extends Communication implements Runnable {

	public static void main(String[] args) {
		ServerSide ss = new ServerSide(26000);
		ss.exec();
	}

	public static String TAG = "Server";
	private ServerSocketChannel serverChannel;
	private ArrayList<Client> clients;

	public Gson gson = new Gson();

	public static List<Gorilka> gorilky;
	public static LinkedList<Koule> balls;

	public static Mapa mapa;

	public ServerSide(int port) {
		super("\r\n", 10000, 10000);

		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
			System.out.println("Server is listening on port " + port);

			clients = new ArrayList<Client>();

			gorilky = new ArrayList<Gorilka>();
			mapa = new Mapa();
			balls = new LinkedList<Koule>();

			for (int i = 0; i < 10; i++) {
				balls.add(new Koule((int) (Math.random() * 400 + 40),
						(int) (Math.random() * 250 + 40)));
			}

		}

		catch (IOException ioe) {
			System.err.println("" + ioe);
			System.exit(1);
		}

	}

	
	public void pridejGorilku(Gorilka gorilka) {
		gorilky.add(gorilka);
	}
	
	public void odeberGorilku(Gorilka gorilka) {
		gorilky.remove(gorilka);
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

				update();

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

	private void readMessage()  {
		Iterator<Client> i = clients.iterator();
		while (i.hasNext()) {
			Client user = i.next();
			SocketChannel channel = user.getChannel();
			StringBuffer sb = user.getBuffer();

			try {
			ArrayList<String> messages = read(channel, sb);
			if (messages != null) {
				for (String mm : messages) {
					if (readMessage(user, mm)) {
						odeberGorilku(user.gorilka);
						userQuit(user);
						channel.close();
						i.remove();
					}
				}
			} else

			{
				odeberGorilku(user.gorilka);
				channel.close();
				i.remove();
			}
			
			} catch (Exception e) {
				odeberGorilku(user.gorilka);
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
		Client client = new Client(channel);
		clients.add(client);
		pridejGorilku(client.gorilka);
	}

	public boolean readMessage(Client client, String message) {

		ClientPacket packet = gson.fromJson(message, ClientPacket.class);

		if (packet != null) {
			Gorilka gorilka = client.gorilka;
			gorilka.up = packet.up;
			gorilka.down = packet.down;
			gorilka.left = packet.left;
			gorilka.right = packet.right;
			gorilka.fire = packet.fire;
		}

		return false;
	}

	public void userQuit(Client client) {

	}

	public void update() {

		
	//	System.out.println("gorilky: " + gorilky.size());
	//	System.out.println("koule: " + balls.size());
		
		for (Koule ball : balls) {
			ball.update();
		}
		
		for (Gorilka gorilka : gorilky) {
			gorilka.update();
		}
		
		// generovani packetu

		ServerPacket packet = new ServerPacket();
		packet.gorilky = gorilky;
		packet.koule = balls;

		String packetText = gson.toJson(packet);
		
		System.out.println("" + packetText);
		
		try {
			prepareWriteBuffer(packetText);
		} catch (TooLongMessageException e) {
			System.err.println("Dlouha zprava");
		}
		
		sendBroadcast();

	}

}
