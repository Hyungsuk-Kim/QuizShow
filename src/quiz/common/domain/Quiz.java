package quiz.common.domain;

import java.io.Serializable;

public class Quiz implements Serializable {
	// Variables
	private static final long serialVersionUID = 435360789792067194L;
	
	private int quizNum;
	private String quiz;
	private String answer;
	private int level;
	private int quizType;
	private String[] cases;
	
	public static final int SUBJECTIVE_QUIZ = 1;
	public static final int OBJECTIVE_QUIZ = 2;
	
	public static final int EXTREME_HARD_LEVEL = 5;
	public static final int HARD_LEVEL = 4;
	public static final int NORMAL_LEVEL = 3;
	public static final int EASY_LEVEL = 2;
	public static final int EXTREME_EASY_LEVEL = 1;
	public static final int[] LEVEL_LIST = {EXTREME_EASY_LEVEL, EASY_LEVEL, NORMAL_LEVEL, HARD_LEVEL, EXTREME_HARD_LEVEL};
	public static final int MAX_COUNT_QUIZ = 10;
	
	// Constructors
	// 객관식 퀴즈 생성
	public Quiz(String quiz, String answer, int level, String... cases) {
		this.quiz = quiz;
		this.answer = answer;
		this.level = level;
		this.quizType = OBJECTIVE_QUIZ;
		this.cases = cases;
	}
	// DAO에서만 사용
	public Quiz(int quizNum, String quiz, String answer, int level, String... cases) {
		this.quizNum = quizNum;
		this.quiz = quiz;
		this.answer = answer;
		this.level = level;
		this.quizType = OBJECTIVE_QUIZ;
		this.cases = cases;
	}
	// 주관식 퀴즈 생성
	public Quiz(String quiz, String answer, int level) {
		this.quiz = quiz;
		this.answer = answer;
		this.level = level;
		this.quizType = SUBJECTIVE_QUIZ;
		this.cases = null;
		//this.cases = new String[0];
	}
	// DAO에서만 사용
	public Quiz(int quizNum, String quiz, String answer, int level) {
		this.quizNum = quizNum;
		this.quiz = quiz;
		this.answer = answer;
		this.level = level;
		this.quizType = SUBJECTIVE_QUIZ;
		this.cases = null;
		//this.cases = new String[0];
	}

	// Methods
	@Override
	public String toString() {
		return "Quiz [quizNum=" + quizNum + ", quiz=" + quiz + ", answer=" + answer + ", level=" + level + ", quizType="
				+ quizType + "]";
	}
	
	// Getters
	public int getQuizNum() {
		return quizNum;
	}
	
	public String getQuiz() {
		return quiz;
	}

	public String getAnswer() {
		return answer;
	}

	public int getLevel() {
		return level;
	}

	public int getQuizType() {
		return quizType;
	}

	public String[] getCases() {
		return cases;
	}
	
	// Setters
	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}
	
	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setQuizType(int quizType) {
		this.quizType = quizType;
	}

	public void setCases(String[] cases) {
		this.cases = cases;
	}
	
}
