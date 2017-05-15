package quiz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.helper.ConvertDateType;
import quiz.common.helper.QuizException;

public class QuizDAOImpl implements QuizDAO{
	String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/MiniProject";
    String user = "root";
    String password = "1234";
	
    public QuizDAOImpl() throws QuizException {
        try {
        	Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new QuizException("QuizDAOImpl.constructor", e);
        }
    }
    
    protected Connection obtainConnection() throws SQLException {
    	return DriverManager.getConnection(url, user, password);
    }
	
	@Override
	public void createUser(User user) throws QuizException {
		String sql = "INSERT INTO user (nickname, password, recent_access, best_score) VALUES (?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int uno = 0;
		uno = this.nicknameExist(user.getNickname()); // 만약 같은 이름의 사람이 있을 경우, 그 사람의 uno
		
		try {
			if (uno != 0) {
				throw new QuizException("This nickname is being used by other user.");
			} else if (uno == 0) {
				con = this.obtainConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, user.getNickname());
				pstmt.setString(2, user.getPassword());
				pstmt.setDate(3, null);
				pstmt.setInt(4, 0);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new QuizException("QuizDAOImpl.createUser()", e);
		} finally {
            try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
	}

	@Override
	public void changeUserInfo(User user) throws QuizException {
		String sql = "UPDATE user SET nickname = ?, password = ?, recent_access = ?, best_score = ? WHERE uno = ?";
		String sql2 = "UPDATE user SET nickname = ?, password = ?, best_score = ? WHERE uno = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int uno = 0;
		try {
			if ((uno = this.nicknameExist(user.getNickname())) != 0) {
				con = this.obtainConnection();
				java.sql.Date lastLogOn = ConvertDateType.ConvertDateUtilToSql(user.getRecentAccess());
				if (lastLogOn == null){
					pstmt = con.prepareStatement(sql2);
					pstmt.setString(1, user.getNickname());
					pstmt.setString(2, user.getPassword());
					pstmt.setInt(3, user.getBestScore());
					pstmt.setInt(4, uno);
				} else if (lastLogOn != null) {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, user.getNickname());
					pstmt.setString(2, user.getPassword());
					pstmt.setDate(3, lastLogOn);
					pstmt.setInt(4, user.getBestScore());
					pstmt.setInt(5, uno);
				}
				pstmt.executeUpdate();
			} else {
				throw new QuizException("Cannot change this user`s data.");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new QuizException("QuizDAOImpl.changeUserInfo()" ,e);
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
	    }
	}

	@Override
	public void removeUser(User user) throws QuizException {
		String sql = "DELETE FROM user WHERE uno = ?";
		Connection con = null;
		PreparedStatement pstmt= null;
		int uno = 0;
		uno = this.nicknameExist(user.getNickname());
		try {
			if (uno != 0) {
				con = this.obtainConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, uno);
				pstmt.executeUpdate();
			} else {
				 throw new QuizException("Cannot delete this user.");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new QuizException("QuizDAOImpl.removeUser()", e);
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
	    }
	}

	@Override
	public User getUser(User user) throws QuizException {
		String sql = "SELECT * FROM user WHERE uno = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User beSearchedUser = null;
		int uno = 0;
		try {
			if ((uno = this.nicknameExist(user.getNickname())) != 0) {
				con = this.obtainConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, uno);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					beSearchedUser = new User(rs.getString("nickname"), rs.getString("password"),
							rs.getInt("best_score"), rs.getDate("recent_access"));
				}
			} else {
				throw new QuizException("There is no user who being matched.");
			}
		} catch (Exception e) {
			throw new QuizException("QuizDAOImpl.getUser()", e);
		} finally {
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		
		return beSearchedUser;
	}

	@Override
	public int nicknameExist(String nickname) throws QuizException {
		int userNumber = 0;
		String sql = "SELECT * FROM user WHERE nickname = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet  rs = null;
		try {
			con = this.obtainConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				userNumber = rs.getInt("uno");
			}else if (userNumber == 0) {
				//throw new QuizException("There is no user who being matched.");
			}
		}catch (SQLException e) {
			throw new QuizException("QuizDAOImpl.nicknameExist()", e);
		} finally {
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		return userNumber;
	}

	@Override
	public Quiz[] getQuizSetAsLevel(int level) throws QuizException {
		Quiz[] quizSet = null;
		String sql = "SELECT * FROM quiz WHERE level = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Savepoint sp = null;
		List<Quiz> qList = new Vector<Quiz>();
		try {
			con = this.obtainConnection();
			con.setAutoCommit(false);
			sp = con.setSavepoint();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, level);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int quizNum = rs.getInt("qno");
				String quiz = rs.getString("question");
				String answer = rs.getString("answer");
				int quizType = rs.getInt("quiz_type");
				if (quizType == Quiz.OBJECTIVE_QUIZ) {
					String sql2 = "SELECT * FROM objective_quiz WHERE qno = ?";
					pstmt2 = con.prepareStatement(sql2);
					pstmt2.setInt(1, quizNum);
					rs2 = pstmt2.executeQuery();
					String[] cases = new String[4];
					while (rs2.next()) {
						cases[0] = rs2.getString("first_case");
						cases[1] = rs2.getString("second_case");
						cases[2] = rs2.getString("third_case");
						cases[3] = rs2.getString("fourth_case");
					}
					qList.add(new Quiz(quizNum, quiz, answer, level, cases));
				} else if (quizType == Quiz.SUBJECTIVE_QUIZ) {
					qList.add(new Quiz(quizNum, quiz, answer, level));
				}
			}
			quizSet = qList.toArray(new Quiz[0]);
			con.setAutoCommit(true);
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback(sp);
					con.setAutoCommit(true);
				} catch(SQLException se) {
					throw new QuizException("QuizDAOImpl.getQuizSetAsLevel()", se);
				}  
			}
			throw new QuizException("QuizDAOImpl.getQuizSetAsLevel()", e);
		} finally {
			try { if (rs2 != null) rs2.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt2 != null) pstmt2.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		return quizSet;
	}

	@Override
	public Quiz getQuiz(int qno) throws QuizException {
		String sql = "SELECT * FROM quiz WHERE qno = ?";
		Quiz quiz = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Savepoint sp = null;
		try {
			con = this.obtainConnection();
			con.setAutoCommit(false);
			sp = con.setSavepoint();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("quiz_type") == Quiz.SUBJECTIVE_QUIZ) {
					quiz = new Quiz(qno, rs.getString("question"), rs.getString("answer"), rs.getInt("level"));
				} else if (rs.getInt("quiz_type") == Quiz.OBJECTIVE_QUIZ) {
					String sql2 = "SELECT * FROM objective_quiz WHERE qno = ?";
					pstmt2 = con.prepareStatement(sql2);
					pstmt2.setInt(1, qno);
					String[] cases = new String[4];
					rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
						cases[0] = rs2.getString("first_case");
						cases[1] = rs2.getString("second_case");
						cases[2] = rs2.getString("third_case");
						cases[3] = rs2.getString("fourth_case");
						quiz = new Quiz(qno, rs.getString("question"), rs.getString("answer"), rs.getInt("level"), cases);
					}
				}
			}
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();///////////////
			if(con != null) {
				try {
					con.rollback(sp);
					con.setAutoCommit(true);
				} catch(SQLException se) {
					throw new QuizException("QuizDAOImpl.getQuiz()", se);
				}  
			}
			throw new QuizException("QuizDAOImpl.getQuiz()", e);
		} finally {
			try { if (rs2 != null) rs2.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt2 != null) pstmt2.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		return quiz;
	}
	
	@Override
	public void addQuiz(Quiz quiz) throws QuizException {
		String sql = "INSERT INTO quiz (question, answer, quiz_type, level) VALUES (?, ?, ?, ?)";
		String sql2 = "INSERT INTO objective_quiz VALUES (?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		Savepoint sp = null;
		try {
			con = this.obtainConnection();
			con.setAutoCommit(false);
			sp = con.setSavepoint();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, quiz.getQuiz());
			pstmt.setString(2, quiz.getAnswer());
			pstmt.setInt(3, quiz.getQuizType());
			pstmt.setInt(4, quiz.getLevel());
			pstmt.executeUpdate();
			if (quiz.getQuizType() == Quiz.OBJECTIVE_QUIZ) {
				pstmt.close();
				String[] cases = quiz.getCases();
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, getQNo());
				for (int i = 0; i < cases.length; i++) {
					pstmt.setString((i+2), cases[i]);
				}
				pstmt.executeUpdate();
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback(sp);
					con.setAutoCommit(true);
				} catch(SQLException se) {
					throw new QuizException("QuizDAOImpl.addQuiz()", se);
				}  
			}
			throw new QuizException("QuizDAOImpl.addQuiz()", e);
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
	}
	
	protected int getQNo() throws QuizException{
		int qno = 0;
		String sql = "SELECT MAX(qno) FROM quiz";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.obtainConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				qno = rs.getInt(1);
			} else if (qno == 0) {
				throw new QuizException("Quiz pool is empty.");
			}
		} catch (SQLException e) {
			throw new QuizException("QuizDAOImpl.getQNo()", e);
		} finally {
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		return qno;
	}
	
	@Override
	public void removeQuiz(Quiz quiz) throws QuizException {
		int qno = 0;
		Quiz[] quizSet = this.getQuizSetAsKeyword(quiz.getQuiz());
		for (Quiz quizTemp : quizSet) {
			if (quizTemp.getQuiz().equals(quiz.getQuiz())) {
				if (quizTemp.getAnswer().equals(quiz.getAnswer())) {
					if ((quizTemp.getLevel()) == (quiz.getLevel())) {
						qno = quizTemp.getQuizNum();
					}
				}
			}
		}
		String sql = "DELETE FROM quiz WHERE qno = ?";
		String sql2 = "DELETE FROM objective_quiz WHERE qno = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		Savepoint sp = null;
		if (qno == 0) {
			throw new QuizException("There is no quiz which being matched.");
		} else if (qno != 0) {
			try {
				con = this.obtainConnection();
				con.setAutoCommit(false);
				sp = con.setSavepoint();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, qno);
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, qno);
				pstmt.executeUpdate();
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				if(con != null) {
					try {
						con.rollback(sp);
						con.setAutoCommit(true);
					} catch(SQLException se) {
						throw new QuizException("QuizDAOImpl.removeQuiz()", se);
					}  
				}
				throw new QuizException("QuizDAOImpl.removeQuiz()", e);
			} finally {
				try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
				try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
			}
		}
	}

	@Override
	public Quiz[] getQuizSetAsKeyword(String keyword) throws QuizException {
		Quiz[] quizSet = null;
		String sql = "SELECT * FROM quiz WHERE question LIKE ? ESCAPE '@'";
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Savepoint sp = null;
		List<Quiz> qList = new Vector<Quiz>();
		
		String wildcardKeyword1 = keyword.replace("%", "@%");
		String wildcardKeyword2 = wildcardKeyword1.replace("_", "@_");
		String wildcardKeyword3 = wildcardKeyword2.replace("@", "@@");
		String newKeyword= wildcardKeyword3.replace(' ', '%');
		try {
			con = this.obtainConnection();
			con.setAutoCommit(false);
			sp = con.setSavepoint();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ("%" + newKeyword + "%"));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int quizNum = rs.getInt("qno");
				String quiz = rs.getString("question");
				String answer = rs.getString("answer");
				int quizType = rs.getInt("quiz_type");
				int level = rs.getInt("level");
				if (quizType == Quiz.SUBJECTIVE_QUIZ) {
					String sql2 = "SELECT * FROM objective_quiz WHERE qno = ?";
					pstmt2 = con.prepareStatement(sql2);
					pstmt2.setInt(1, quizNum);
					rs2 = pstmt2.executeQuery();
					String[] cases = new String[4];
					while (rs2.next()) {
						cases[0] = rs2.getString("first_case");
						cases[1] = rs2.getString("second_case");
						cases[2] = rs2.getString("third_case");
						cases[3] = rs2.getString("fourth_case");
					}
					qList.add(new Quiz(quizNum, quiz, answer, level, cases));
				} else if (quizType == Quiz.OBJECTIVE_QUIZ) {
					qList.add(new Quiz(quizNum, quiz, answer, level));
				}
			}
			quizSet = qList.toArray(new Quiz[0]);
			con.setAutoCommit(true);
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback(sp);
					con.setAutoCommit(true);
				} catch(SQLException se) {
					throw new QuizException("QuizDAOImpl.getQuizSetAsLevel()", se);
				}  
			}
			throw new QuizException("QuizDAOImpl.getQuizSetAsLevel()", e);
		} finally {
			try { if (rs2 != null) rs2.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt2 != null) pstmt2.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		return quizSet;
	}

	@Override
	public User[] getUsersByScore(int limit) throws QuizException {
		String sql = "SELECT * FROM user ORDER BY best_score DESC LIMIT ?";
		List<User> uList = new ArrayList<User>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.obtainConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, limit);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String nickname = rs.getString("nickname");
				String password = rs.getString("password");
				Date recentAccess = ConvertDateType.ConvertDateSqlToUtil(rs.getDate("recent_access"));
				int bestScore =  rs.getInt("best_score");
				uList.add(new User(nickname, password, bestScore, recentAccess));
			}
		} catch (SQLException e) {
			throw new QuizException("QuizDAOImpl.getUsersByScore()", e);
		} finally {
			try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
			try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
		}
		
		return uList.toArray(new User[0]);
	}
	
}
