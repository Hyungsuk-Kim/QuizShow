package quiz.network;

import java.util.ArrayList;
import java.util.List;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.gui.QuizGUI;
import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;
import quiz.network.packet.*;

public class QuizModelNwImpl implements QuizModel{
	private List<QuizGUI> changeModelListeners = new ArrayList<QuizGUI>();
	private User currentUser = null;
	private QuizClient client = null;
	
	public QuizModelNwImpl(QuizClient client) {
		this.client = client;
	}
	
	/*private void fireModelChangeEvent(Object display) {
        for (QuizGUI gui : changeModelListeners) {
            try {
                gui.handleModelChange(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } */

	@Override
	public void addChangeModelListener(QuizGUI gui) throws QuizException {
		this.changeModelListeners.add(gui);
	}

	@Override
	public void addUser(User user) throws QuizException {
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new RegiserUserPacket(user);
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof SucceedPacket)) {
				throw new QuizException("QuizModelNwImpl.addUser()");
			}
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteUser(User user) throws QuizException {
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new DeleteUserPacket(user);
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof SucceedPacket)) {
				throw new QuizException("QuizModelNwImpl.deleteUser()");
			}
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}		
	}

	@Override
	public void updateUser(User user) throws QuizException {
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new ChangeUserInfoPacket(user);
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof SucceedPacket)) {
				throw new QuizException("QuizModelNwImpl.deleteUser()");
			}
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}			
	}

	@Override
	public User signIn(User user) throws QuizException {
		User confirmedUser = null;
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new SignInPacket(user);
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof User)) {
				throw new QuizException("QuizModelNwImpl.signIn()");
			}
			confirmedUser = (User) result;
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}
		this.currentUser = confirmedUser;
		return confirmedUser;
	}

	@Override
	public void signOut(User user) throws QuizException {
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new SignOutPacket(user);
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof SucceedPacket)) {
				throw new QuizException("QuizModelNwImpl.signOut()");
			}
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}	
	}

	@Override
	public Quiz[] loadQuizSet() throws QuizException {
		Quiz[] quizSet = null;
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new LoadingQuizSetPacket();
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof Quiz[])) {
				throw new QuizException("QuizModelNwImpl.loadQuizSet()");
			}
			quizSet = (Quiz[]) result;
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}
		this.currentUser.setAllocatdQuiz(quizSet);
		return quizSet;
	}

	/*@Override
	public void allocateQuizToUser(User user) throws QuizException {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public User[] loadRankBoard(int limit) throws QuizException {
		User[] rankers = null;
		Object result = null;
		PacketObject packet = null;
		try {
			packet = new LoadingRankBoardPacket(limit);
			this.client.send(packet);
			packet = (PacketObject) client.receive();
			result = packet.getResult();
			if (!(result instanceof User[])) {
				throw new QuizException("QuizModelNwImpl.loadRankBoard()");
			}
			rankers = (User[]) result;
		}catch (Exception e) {
			throw new QuizException(e.getMessage(), e);
		}	
		return rankers;
	}

	@Override
	public int checkCorrectAnswer() throws QuizException {
		String[] solutions = currentUser.getSolutions();
		Quiz[] quizzes = currentUser.getAllocatedQuiz();
		int score = 0;
		for (int i = 0; i < quizzes.length; i++) {
			if ((quizzes[i].getAnswer()).equals(solutions[i])) {
				score += (quizzes[i].getLevel() * 100);
			}
		}
		if (score > currentUser.getBestScore()) {
			currentUser.setBestScore(score);
			try {
				this.updateUser(currentUser);
			} catch (Exception e) {
				throw new QuizException("QuizModelNwImpl.checkCorrectAnswer()", e);
			}
		}
		return score;
	}

	@Override
	public void saveAnswer(String solution, int questionNum) throws QuizException {
		String[] solutions = currentUser.getSolutions();
		solutions[questionNum] = solution;
		currentUser.setSolutions(solutions);
	}
	
}
