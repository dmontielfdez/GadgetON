import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * -- Promotion server
 * 
 * This server accept two types of connections, admin and client.
 * If the connection is the type of admin, the socket reads the stream and sends to all clients
 * and if the connection is the type of client, save his socket in a list
 * @author dmontielfdez
 *
 */
public class Server {

	static Socket admin;
	static ArrayList<Socket> listClients;
	public static void main(String[] args) {
		
		ServerSocket server = null;
		try {
			listClients = new ArrayList<Socket>();

			server = new ServerSocket(2222);
			System.out.println("Conectado");

			while(true){
				Socket s = server.accept();
				DataInputStream input = new DataInputStream(s.getInputStream());
				String type = input.readUTF();

				if (type.substring(0, 5).equals("admin")) {
					String mensaje = type.substring(6, type.length());
					enviarMensaje(mensaje);
					admin = s;
					System.out.println("Admin conectado");
				} else{
					listClients.add(s);
					System.out.println("Cliente conectado");
				}

			}
		} catch (SocketException e) {
			System.out.println("Cliente desconectado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void enviarMensaje(String mensaje){
		for (Socket s : listClients) {
			try {
				DataOutputStream output = new DataOutputStream(s.getOutputStream());
				output.writeUTF(mensaje);
			} catch (SocketException e) {
				System.out.println("Cliente desconectado");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
