package quiz.mvc.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.gui.QuizGUI;
import quiz.common.helper.QuizException;
import quiz.database.QuizDAO;
import quiz.mvc.interfaces.QuizModel;

public class QuizModelImpl implements QuizModel{
	// Variables
	private QuizDAO dao;
	private List<QuizGUI> changeModelListeners = new ArrayList<QuizGUI>();
	
	// Constructor
	public QuizModelImpl(QuizDAO dao) {
		this.dao = dao;
	}
	
	// Methods
	protected int randomIntInRange(int minimun, int maximun) {
		return (int) (Math.random() * (maximun - minimun + 1)) + minimun;
	}
	
	@Override
	public void addChangeModelListener(QuizGUI gui) throws QuizException {
		changeModelListeners.add(gui);
	}

	@Override
	public void addUser(User user) throws QuizException {
		try{
		dao.createUser(user);
		}catch(QuizException e){
			throw new QuizException("QuizModelImpl.addUser()", e);
		}
	}

	@Override
	public void deleteUser(User user) throws QuizException {
		try{
			dao.removeUser(user);
		}catch(QuizException e){
			throw new QuizException("QuizModelImpl.deleteUser()",e);
		}
	}

	@Override
	public void updateUser(User user) throws QuizException {
		try{
			dao.changeUserInfo(user);
		}catch(QuizException e){
			throw new QuizException("QuizModelImpl.updateUser()",e);
		}		
	}

	@Override
	public User signIn(User user) throws QuizException {
		User userConfirm = null; // 임시 저장될 user 변수
		User confirmedUser = null; // 리턴 될 user 변수
		try {
			userConfirm = this.dao.getUser(user);
			if ((userConfirm.getPassword()).equals(user.getPassword())) {
				userConfirm.setRecentAccess(new Date());
				this.dao.changeUserInfo(userConfirm);
				confirmedUser = userConfirm;
			} else {
				throw new QuizException("You have wrong password.");
			}
		} catch (QuizException e) {
			throw new QuizException("QuizModelImpl.signIn()", e);
		}
		return confirmedUser;
	}

	@Override
	public void signOut(User user) throws QuizException {
		try {
			this.dao.changeUserInfo(user);
		} catch (QuizException e) {
			throw new QuizException("QuizModelImpl.signOut()", e);
		}
	}
	
	@Override
	public Quiz[] loadQuizSet() throws QuizException {
		List<Quiz> qList = new ArrayList<Quiz>();
		ArrayList<Quiz> tempList = new ArrayList<Quiz>();
		try {
			Quiz[] tempQuizSetAsLevel = null;
			int maxCountQuizPerLevel = (Quiz.MAX_COUNT_QUIZ) / (Quiz.LEVEL_LIST.length);
			
			for (int level : Quiz.LEVEL_LIST) {
				tempQuizSetAsLevel = this.dao.getQuizSetAsLevel(level);
				for (int i = 0; i < tempQuizSetAsLevel.length; i++) {
					tempList.add(tempQuizSetAsLevel[i]);
				}
				Collections.shuffle(tempList);
				for (int i = 0; i < maxCountQuizPerLevel; i++) {
					qList.add(tempList.get(i));
				}
				
				/*for (Quiz quiz : tempQuizSetAsLevel) {
					System.out.println(quiz.getQuiz() + " - " + quiz.getAnswer());
				}*/
				/*int count = 0;
				while (count < maxCountQuizPerLevel) {
					int curIndex = this.randomIntInRange(0, tempQuizSetAsLevel.length);
					int prevIndex = 0;
					if(curIndex == prevIndex) {
						//curIndex = this.randomIntInRange(0, tempQuizSetAsLevel.length);
					} else if (curIndex != prevIndex){
						qList.add(tempQuizSetAsLevel[curIndex]);
						prevIndex = curIndex;
						count++;
					}
				}*/
				/*for (int i = 0; i < maxCountQuizPerLevel; i++) {
					int curIndex = this.randomIntInRange(0, tempQuizSetAsLevel.length);
					int prevIndex = 0;
					if(curIndex == prevIndex) {
						curIndex = this.randomIntInRange(0, tempQuizSetAsLevel.length);
					} else if (curIndex != prevIndex){
						qList.add(tempQuizSetAsLevel[curIndex]);
						prevIndex = curIndex;
					}
				}*/
			}
		} catch (QuizException e) {
			throw new QuizException("QuizModelImpl.loadQuizSet()", e);
		}
		return qList.toArray(new Quiz[0]);
	}

	/*@Override
	public void allocateQuizToUser(User user) throws QuizException {
				
	}*/

	@Override
	public int checkCorrectAnswer() throws QuizException {
		// not used.
		return 0;
	}
	
	@Override
	public User[] loadRankBoard(int limit) throws QuizException {
		try {
			return this.dao.getUsersByScore(limit);
		} catch (QuizException e) {
			throw new QuizException("QuizModelImpl.loadQuizSet()", e);
		}
	}
	/** model의 user의 답안 저장 */
	@Override
	public void saveAnswer(String solutions, int answeNum) throws QuizException {
		// not used.
	}
}
