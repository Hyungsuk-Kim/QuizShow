package quiz.network;

import quiz.common.gui.QuizGUI;
import quiz.mvc.interfaces.*;

public class StartClient {
	public static void main(String[] args) {
		int port = 6789;
		String host = "localhost";
		//String host = "220.67.115.225";
		try {
            if (args.length > 0) {
                host = args[0];
            }
            QuizClient client = new QuizClient(host, port);
            QuizModel model = new QuizModelNwImpl(client);
            QuizGUI gui = new QuizGUI(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
