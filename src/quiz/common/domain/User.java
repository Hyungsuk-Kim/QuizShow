package quiz.common.domain;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	
	// Variables
	private static final long serialVersionUID = 6903013318936804235L;
	
	private String nickname;
	private String password;
	private int bestScore; // 최고 점수
	private Date recentAccess; // 최근 접속시간
	private Quiz[] allocatedQuiz = null;
	private String[] solutions = null;
	private int score = 0;
	
	//Constructors
	public User(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
		this.bestScore = 0;
		this.recentAccess = null;
	}
	
	public User(String nickname, String password, int bestScore, Date recentAccess) {
		this.nickname = nickname;
		this.password = password;
		this.bestScore = bestScore;
		this.recentAccess = recentAccess;
	}
	
	// Methods
	
	// Getters
	public String getNickname() {
		return nickname;
	}
	@Override
	public String toString() {
		return "User [nickname=" + nickname + ", password=" + password + ", bestScore=" + bestScore + ", recentAccess="
				+ recentAccess + "]";
	}

	public String getPassword() {
		return password;
	}
	public int getBestScore() {
		return bestScore;
	}
	public int getScore() {
		return score;
	}
	public Date getRecentAccess() {
		return recentAccess;
	}
	public Quiz[] getAllocatedQuiz() {
		return allocatedQuiz;
	}
	public String[] getSolutions() {
		return solutions;
	}
	
	// Setters
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setRecentAccess(Date recentAccess) {
		this.recentAccess = recentAccess;
	}
	public void setAllocatdQuiz(Quiz[] allocatedQuiz) {
		this.allocatedQuiz = allocatedQuiz;
		System.out.println("User(75th line)");
	}
	public void setSolutions(String[] solutions) {
		this.solutions = solutions;
	}
	
}
