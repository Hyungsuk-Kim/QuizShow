package quiz.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import quiz.database.QuizDAO;
import quiz.database.QuizDAOImpl;
import quiz.mvc.implementation.QuizModelImpl;
import quiz.mvc.interfaces.QuizModel;
import quiz.network.packet.PacketObject;

public class QuizServer {
	private int port;
	private ServerSocket serverSocket;
	private QuizModel quizModel;
	
	public QuizServer(int port, QuizModel quizModel) {
		this.port = port;
		this.quizModel = quizModel;
		try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("QuizServer created ServerSocket on port " + port);
            this.acceptConnection();
            
        } catch (Exception e) {
            System.out.println("QuizServer constructor: " + e);
        }
	}
	
	private void acceptConnection() {
		Socket skt;
        System.out.println("Server.acceptConnection called");
        while (true) {
            try {
            	while (true) {
            		skt = serverSocket.accept();
            		new ThreadAllocatedPerClient(this, skt).start();
            	}
            } catch (Exception e) {
                System.out.println("Server.acceptConnection: " + e);
            }
        }
	}
	
	class ThreadAllocatedPerClient extends Thread {
		
		private QuizServer server;
    	private Socket socket;
    	private ObjectInputStream ois;
    	private ObjectOutputStream oos;
    	
		public ThreadAllocatedPerClient(QuizServer quizServer, Socket skt) {
			this.server = quizServer;
    		this.socket = skt;
		}

		@Override
    	public void run() {
    		
    		PacketObject packet = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                	packet = (PacketObject) ois.readObject();
                	System.out.println("QuizServer.service " + packet);
                    
                    packet.execute(quizModel);
                    System.out.println("QuizServer (72 line)");
                    
                    oos.writeObject(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            	try {
	            	if (ois != null) {
	            		ois.close();
	            	}
	            	if (oos != null) {
	            		oos.close();
	            	}
	            	if (socket != null) {
	            		socket.close();
	            	}
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }
    	}
	}
	
	public static void main(String[] args) {
		int port = 6789;
		try {
			QuizDAO dao = new QuizDAOImpl();
			QuizModel model = new QuizModelImpl(dao);
			QuizServer server = new QuizServer(port, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
