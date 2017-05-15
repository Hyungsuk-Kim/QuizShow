package quiz.database;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.helper.QuizException;

public interface QuizDAO {
	/** 사용자를 추가, Using constructor User(nickname, password) */
	public abstract void createUser(User user) throws QuizException;
	
	/** nickname과 password를 제외한 사용자 정보를 수정,
	 * Using constructor User(nickname, password, bestScore, recentAccess)
	 */
	public abstract void changeUserInfo(User user) throws QuizException;
	
	/** 사용자를 제거, Using constructor User(nickname, password) */
	public abstract void removeUser(User user) throws QuizException;
	
	/** 사용자 객체를 얻어옴, Using constructor User(nickname, password) */
	public abstract User getUser(User user) throws QuizException; // 로그인할 때 사용
	
	/** 사용자들의 최고 점수를 기준으로 매개변수로 입력된 값 만큼의 사용자 정보를 가져옴 */
	public abstract User[] getUsersByScore(int limit) throws QuizException;
	
	/** 해당 nickname의 user의 uno를 가져옴 */
	public abstract int nicknameExist(String nickname) throws QuizException;
	
	
	/** 난이도가 같은 퀴즈들을 얻어옴 */
	public abstract Quiz[] getQuizSetAsLevel(int level) throws QuizException;
	
	/** 퀴즈를 얻어옴 */
	public abstract Quiz getQuiz(int qno) throws QuizException;
	
	/** Keyword가 포함된 퀴즈들을 검색 */
	public abstract Quiz[] getQuizSetAsKeyword(String keyword) throws QuizException;
	
	/** 퀴즈 추가, 
	 * Using Quiz(String quiz, String answer, int level) or 
	 * Quiz(String quiz, String answer, int level, String... cases)
	 */
	public abstract void addQuiz(Quiz quiz) throws QuizException;
	
	/** 퀴즈 제거, 
	 * Using Quiz(String quiz, String answer, int level) or 
	 * Quiz(String quiz, String answer, int level, String... cases)
	 */
	public abstract void removeQuiz(Quiz quiz) throws QuizException;
	
}
