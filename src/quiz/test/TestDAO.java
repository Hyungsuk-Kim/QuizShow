package quiz.test;


import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.database.QuizDAO;
import quiz.database.QuizDAOImpl;

public class TestDAO {
	QuizDAO dao = null;
	User[] users = null;
	
	@Before
	public void before(){
		try {
			this.dao = new QuizDAOImpl();
		} catch (QuizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DAO Created");
		
		int arraySize = 7;
		User user = null;
		
		users = new User[arraySize];
		for (int i = 0; i < arraySize; i++) {
			user = new User("user" + i, "1234");
			users[i] = user;
		}
	}
	
	/*@Test
	public void getQuizSetAsLevel() throws QuizException {
		Quiz[] quizzes = dao.getQuizSetAsLevel(2);
		for (Quiz quiz : quizzes) {
			System.out.println(quiz.getLevel() + " - " + quiz.getQuiz() + " - " + quiz.getAnswer());
		}
	}*/
	
	/*@Test
	public void testCreateUser() throws QuizException {
		
		for (User user : users) {
			try {
				dao.createUser(user);
			} catch (QuizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	/*@Test
	public void testGetUser() throws QuizException {
		User searchedUser = null;
		for (User user : users) {
			searchedUser = dao.getUser(user);
			System.out.println(searchedUser);
		}
		//dao.getUser(new User("X-man", "6789"));
	}*/
	
	/*@Test
	public void testChangeUserInfo() throws QuizException {
		User changedUser1 = new User("user3", "0987", 1200, new Date());
		User changedUser3 = new User("user1", "0987", 2100, new Date());
		User changedUser4 = new User("user2", "0987", 1700, new Date());
		User changedUser5 = new User("user4", "0987", 2100, new Date());
		User changedUser6 = new User("user6", "0987", 3000, new Date());
		User changedUser2 = new User("user5", "0987", 1000, new Date());
		
		dao.changeUserInfo(changedUser1);
		dao.changeUserInfo(changedUser2);
		dao.changeUserInfo(changedUser3);
		dao.changeUserInfo(changedUser4);
		dao.changeUserInfo(changedUser5);
		dao.changeUserInfo(changedUser6);
		
		for (User user : users) {
			System.out.println(dao.getUser(user));
		}
		
	}*/
	
	/*@Test
	public void testDeleteUser() throws QuizException {
		for (User user : users) {
			dao.removeUser(user);
		}
		for (User user : users) {
			try {
				System.out.println(dao.getUser(user));
			} catch (QuizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	/*@Test
	public void testNicknameExist() throws QuizException {
		for (User user : users) {
			System.out.println(dao.nicknameExist(user.getNickname()));
		}
	}*/
	
	/*@Test
	public void testGetQuiz() throws QuizException {
		int[] qnoSet = {1,34, 52, 23, 11, 200};
		Quiz quiz = null;
		
		for (int qno : qnoSet) {
			quiz = dao.getQuiz(qno);
			System.out.println(quiz);
		}
	}*/
	
	@Test
	public void testGetQuizSetAsKeyword() throws QuizException {
		String[] keywords = {"만화"};
		Quiz[] quizzes = null;
		for (String keyword : keywords) {
			quizzes = dao.getQuizSetAsKeyword(keyword);
			for (Quiz quiz : quizzes) {
				System.out.println(quiz);
			}
		}
	}
	
	/*@Test
	public void getUserByScore() throws QuizException {
		User[] ranker = null;
		ranker = dao.getUsersByScore(100);
		for (User user : ranker) {
			System.out.println(user);
		}
	}*/
	
}
