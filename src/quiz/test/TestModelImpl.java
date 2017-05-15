package quiz.test;

import java.util.Date;

import javax.security.auth.callback.ConfirmationCallback;

import org.junit.Before;
import org.junit.Test;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.database.QuizDAO;
import quiz.database.QuizDAOImpl;
import quiz.mvc.implementation.QuizModelImpl;
import quiz.mvc.interfaces.QuizModel;

public class TestModelImpl {
	QuizModel model = null;
	QuizDAO dao = null;
	
	@Before
	public void before() throws QuizException {
		this.dao = new QuizDAOImpl();
		this.model = new QuizModelImpl(dao);
	}
	
	/*@Test
	public void TestLoadQuizSet() throws QuizException {
		Quiz[] quizzes = null;
		try {
			quizzes = model.loadQuizSet();
		} catch (QuizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Quiz quiz : quizzes) {
			System.out.print(quiz.getQuiz());
			System.out.println("Level : " + quiz.getLevel());
		}
	}*/
	
	@Test
	public void testAddUser() throws QuizException {
		User confirmedUser;
		User user = new User("admin", "1234", 10000, new Date());
		model.signOut(user);
		//System.out.println(confirmedUser);
		//model.updateUser(user);
		//model.addUser(user);
		//model.deleteUser(user);
	}
	
	@Test
	public void testLoadRankBoard() throws QuizException {
		int limit = 10000;
		User[] ranker = model.loadRankBoard(limit);
		for (User user : ranker) {
			System.out.println(user);
		}
	}
}
