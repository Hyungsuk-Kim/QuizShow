package quiz.network;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import quiz.common.helper.QuizException;

public class QuizClient {
	private String host;
    private int port;
    private Socket skt;
    private InputStream is;
    private ObjectInputStream ois;
    private OutputStream os;
    private ObjectOutputStream oos;
    private int retry; // The number of count for connect to server.
    
    public QuizClient(String host, int port) {
    	System.out.println("QuizClient (24 line)");
    	this.host = host;
    	this.port = port;
    	
    	connect();
    }
	
	public void send(Object obj) throws Exception {
        retry = 10;
        while (retry > 0) {
            try {
            	oos.writeObject(obj);
                retry = 0;
                System.out.println("QuizClient.send: " + obj + "sent");
            } catch (Exception e) {
                System.out.println("QuizClient.send: " + e);
                retry--;
                if (retry == 0) {
                    throw e;
                }
                Thread.sleep(6000);
                connect();
            }
        }
    }
	
	public Object receive() throws Exception {
        Object obj = null;
        try {
            if (ois == null) {
                ois = new ObjectInputStream(is);
            }
            if ((obj = ois.readObject()) == null){
            	System.out.println("null");
            	throw new QuizException("QuizClient.receive()");
            }
        } catch (Exception e) {
            System.out.println("QuizClient.receive: " + e);
            connect();
            throw e;
        }
    	System.out.println("receive");
        return obj;
    }
	
	public void connect(String host, int port) {
    	this.host = host;
        this.port = port;
        connect();
    }

	public void connect() {
		try {
            skt = new Socket(host, port); ////////////////////////////////////////////////
            is = skt.getInputStream();
            ois = null;
            os = skt.getOutputStream();
            oos = new ObjectOutputStream(os);
            System.out.println("QuizClient (81 line)");
        } catch (Exception e) {
            System.out.println("QuizClient.connect: " + e);
        }
	}
	
	public void close() {
		if (skt != null) {
			try {
				skt.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
    }
}
