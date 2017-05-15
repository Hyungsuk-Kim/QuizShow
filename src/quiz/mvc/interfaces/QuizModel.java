package quiz.mvc.interfaces;

import quiz.common.domain.*;
import quiz.common.gui.QuizGUI;
import quiz.common.helper.QuizException;

public interface QuizModel {
	
	/** view에게 model의 변경사항을 알려주기 위해 view를 등록 (view가 사용) */
	public abstract void addChangeModelListener(QuizGUI gui) throws QuizException;
	/** 입력된 사용자 추가 */
	public abstract void addUser(User user) throws QuizException;
	/** 입력된 사용자 제거 */
	public abstract void deleteUser(User user) throws QuizException;
	/** 입력된 사용자 정보 수정 */
	public abstract void updateUser(User user) throws QuizException;
	/** 입력된 사용자 계정으로 로그인 */
	public abstract User signIn(User user) throws QuizException;
	/** 입력된 사용자 계정으로 로그아웃 */
	public abstract void signOut(User user) throws QuizException;
	/** 퀴즈 집합을 로딩 */
	public abstract Quiz[] loadQuizSet() throws QuizException;
	///** 로딩된 퀴즈를 사용자에게 할당 - */
	//public abstract void allocateQuizToUser(User user) throws QuizException;
	/** 사용자가 입력한 퀴즈의 답이 정답인지 검사 */
	public abstract int checkCorrectAnswer() throws QuizException;
	/** Rank board loading */
	public abstract User[] loadRankBoard(int limit) throws QuizException; 
	/** model의 user의 답안 저장 */
	public abstract void saveAnswer(String solution, int questionNum) throws QuizException;
	
}
