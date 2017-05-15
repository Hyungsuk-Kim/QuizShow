package quiz.common.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.*;

import quiz.common.domain.Quiz;
import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class QuizGUI implements ActionListener {
	private QuizModel model;
	private Quiz[] quizs = null;
	private int quizIndex = 0;
	private int quizSolutionsCount = 0;
	private boolean solutionsSend[] = new boolean[10];

	Image imgMark = Toolkit.getDefaultToolkit().getImage("image/mark.gif");
	ImageIcon imgBackBoard = new ImageIcon("image/blackboard.jpg");
	ImageIcon imgBack_before = new ImageIcon("image/back_before.gif");
	ImageIcon imgBack_after = new ImageIcon("image/back_after.gif");
	ImageIcon imgNext_before = new ImageIcon("image/next_before.gif");
	ImageIcon imgNext_after = new ImageIcon("image/next_after.gif");
	ImageIcon imgCheck_after = new ImageIcon("image/check_after.gif");
	ImageIcon imgCheck_before = new ImageIcon("image/check_before.gif");
	ImageIcon imgSave_after = new ImageIcon("image/save_after.gif");
	ImageIcon imgSave_before = new ImageIcon("image/save_before.gif");
	ImageIcon imgExit_after = new ImageIcon("image/exit_after.gif");
	ImageIcon imgExit_before = new ImageIcon("image/exit_before.gif");
	ImageIcon imgQuiz_after = new ImageIcon("image/quiz_after.gif");
	ImageIcon imgQuiz_before = new ImageIcon("image/quiz_before.gif");
	ImageIcon imgRang_after = new ImageIcon("image/rang_after.gif");
	ImageIcon imgRang_before = new ImageIcon("image/rang_before.gif");

	JFrame mainFrame = new JFrame("Quiz Show");

	JDialog conDialog, rankDialog, signDialog;

	JPanel pnlMain = new JPanel();
	JPanel pnlCenter = new JPanel();
	JPanel pnlCenterQuizExample = new JPanel() {
		public void paintComponent(Graphics g) {
			Dimension d = getSize();
			g.drawImage(imgBackBoard.getImage(), 0, 0, d.width, d.height, null);
			setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
			super.paintComponent(g);
		}
	};
	JPanel pnlCenterButton = new JPanel();
	JPanel pnlEast = new JPanel();
	JPanel pnlSouth = new JPanel();
	JPanel pnlWestSpace = new JPanel();

	JLabel lblQuiz = new JLabel("<html><font size='7'>Wellcome to Quiz Show !!!</font></html>");
	JLabel lblExample = new JLabel("");
	JLabel lblTimer = new JLabel("");

	JButton btnBeforeQuiz = new JButton(imgBack_before);
	JButton btnNextQuiz = new JButton(imgNext_before);
	JButton btnNewQuiz = new JButton(imgQuiz_before);
	JButton btnRankTable = new JButton(imgRang_before);
	JButton btnExit = new JButton(imgExit_before);
	JButton btnSaveSolutions = new JButton(imgSave_before);
	JButton btnSendSolutions = new JButton(imgCheck_before);

	JTextField txtInputSolutions = new JTextField(30);

	// conDialog
	JPanel userInfoPan, lblPan, tfPan, btnPan;
	JLabel lblTitle, lblNickname, lblPassword;
	JTextField tfNickname, tfPassword;
	JButton btnSignIn, btnCancel, btnNewJoin;

	// rankDialog
	JPanel rankMainPan, rankPan, rankButtonPan;
	JLabel[] lblRank = new JLabel[10];
	JButton btnOK;

	// Singuplog
	JPanel signUpMainPan, signUpTextPan, signUpButtonPan;
	JLabel lblNickNameInput, lblPasswordInput, lblPasswordReInput;
	JTextField txtNickNameInput;
	JPasswordField pwfPasswordInput, pwfPasswordReInput;
	JButton btnSubmit, btnSignCancel;

	public QuizGUI(QuizModel model) {
		this.model = model;
		buildDisplay();
	}

	/** 프레임 빌더 */
	private void buildDisplay() {

		// 레이아웃 설정
		mainFrame.setLayout(new BorderLayout());
		pnlMain.setLayout(new BorderLayout(20, 20));
		pnlCenter.setLayout(new BorderLayout());
		pnlCenterQuizExample.setLayout(new GridLayout(0, 1, 10, 10));
		pnlCenterButton.setLayout(new FlowLayout());
		pnlEast.setLayout(new GridLayout(0, 1));
		pnlSouth.setLayout(new FlowLayout());

		// 패널에 컴포넌트 배치
		pnlCenterQuizExample.add(lblQuiz);
		pnlCenterQuizExample.add(lblExample);
		pnlCenterButton.add(btnBeforeQuiz);
		pnlCenterButton.add(btnNextQuiz);
		pnlEast.add(lblTimer);
		pnlEast.add(btnNewQuiz);
		pnlEast.add(btnRankTable);
		pnlEast.add(btnExit);
		pnlSouth.add(txtInputSolutions);
		pnlSouth.add(btnSaveSolutions);
		pnlSouth.add(btnSendSolutions);

		// 패널 배치
		mainFrame.add(pnlMain, BorderLayout.CENTER);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		pnlCenter.add(pnlCenterQuizExample, BorderLayout.CENTER);
		pnlCenter.add(pnlCenterButton, BorderLayout.SOUTH);

		// 컴포넌트 설정
		btnBeforeQuiz.setPressedIcon(imgBack_after);
		btnBeforeQuiz.setBorder(null);
		btnNextQuiz.setPressedIcon(imgNext_after);
		btnNextQuiz.setBorder(null);
		btnNewQuiz.setPressedIcon(imgQuiz_after);
		btnNewQuiz.setBorder(null);
		btnNewQuiz.setContentAreaFilled(false);
		btnExit.setPressedIcon(imgExit_after);
		btnExit.setBorder(null);
		btnExit.setContentAreaFilled(false);
		btnRankTable.setPressedIcon(imgRang_after);
		btnRankTable.setBorder(null);
		btnRankTable.setContentAreaFilled(false);
		btnSaveSolutions.setPressedIcon(imgSave_after);
		btnSaveSolutions.setBorder(null);
		btnSaveSolutions.setContentAreaFilled(false);
		btnSendSolutions.setPressedIcon(imgCheck_after);
		btnSendSolutions.setBorder(null);
		btnSendSolutions.setContentAreaFilled(false);
		btnSendSolutions.setEnabled(false);
		lblQuiz.setForeground(Color.WHITE);
		lblExample.setForeground(Color.WHITE);

		// 보더 설정
		pnlMain.setBorder(new EmptyBorder(10, 10, 10, 15));
		pnlCenterQuizExample.setBorder(new EmptyBorder(5, 20, 5, 20));

		// 메인 프레임 세팅
		mainFrame.setSize(820, 520);
		mainFrame.setVisible(true);
		mainFrame.setIconImage(imgMark);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 리스너 추가
		btnNewQuiz.addActionListener(this);
		btnRankTable.addActionListener(this);
		btnBeforeQuiz.addActionListener(this);
		btnNextQuiz.addActionListener(this);
		btnExit.addActionListener(this);
		btnSaveSolutions.addActionListener(this);
		btnSendSolutions.addActionListener(this);

		launchDialog();

	}

	/** 로그인 다이얼로그 창 */
	public void launchDialog() {
		conDialog = new JDialog(mainFrame, "로그인", false);

		userInfoPan = new JPanel(new GridLayout(0, 2));
		lblTitle = new JLabel("Sign-in", JLabel.CENTER);
		lblTitle.setOpaque(true);
		lblTitle.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 32));
		lblTitle.setBackground(Color.orange);
		lblNickname = new JLabel("Nickname", JLabel.CENTER);
		lblNickname.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 18));
		lblPassword = new JLabel("Password", JLabel.CENTER);
		lblPassword.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 18));
		tfNickname = new JTextField(20);
		tfPassword = new JPasswordField(20);
		btnSignIn = new JButton("Log-in");
		btnCancel = new JButton("Cancel");
		btnNewJoin = new JButton("Sign-up");

		lblPan = new JPanel(new GridLayout(2, 0));
		lblPan.add(lblNickname);
		lblPan.add(lblPassword);

		tfPan = new JPanel(new GridLayout(2, 0));
		tfPan.add(tfNickname);
		tfPan.add(tfPassword);

		btnPan = new JPanel();
		btnPan.add(btnSignIn);
		btnPan.add(btnCancel);
		btnPan.add(btnNewJoin);

		userInfoPan.add(lblPan);
		userInfoPan.add(tfPan);

		conDialog.add(lblTitle, BorderLayout.NORTH);
		conDialog.add(userInfoPan, BorderLayout.CENTER);
		conDialog.add(btnPan, BorderLayout.SOUTH);
		conDialog.add(new JPanel(), BorderLayout.WEST);
		conDialog.add(new JPanel(), BorderLayout.EAST);

		conDialog.setSize(350, 180);

		conDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
			}
		});

		btnSignIn.addActionListener(this);
		btnCancel.addActionListener(this);
		btnNewJoin.addActionListener(this);

		conDialog.setLocationRelativeTo(mainFrame);

		conDialog.pack();
		conDialog.setVisible(true);
		tfNickname.requestFocus();
	}

	/** 랭킹 다이얼로그 창 */
	private void launchRankDialog(User[] userRanking) {

		// User[] users = userRanking;
		User[] users = null;

		rankDialog = new JDialog(mainFrame, "순 위 표", true);
		rankMainPan = new JPanel(new BorderLayout());
		rankPan = new JPanel(new GridLayout(10, 0));
		// for(int index = 0; index < 10; index++){
		// lblRank[index] = new JLabel((index+1) + "위. " +
		// users[index].getNickname() + "\t" + users[index].getScore() + "점");
		// rankPan.add(lblRank[index]);
		// }
		for (int index = 0; index < 10; index++) {
			lblRank[index] = new JLabel((index + 1) + "위. " + "홍길동" + 0 + "점");
			rankPan.add(lblRank[index]);
		}
		rankButtonPan = new JPanel(new FlowLayout());
		btnOK = new JButton("확인");
		rankButtonPan.add(btnOK);
		rankMainPan.add(rankPan, BorderLayout.CENTER);
		rankMainPan.add(rankButtonPan, BorderLayout.SOUTH);

		rankDialog.add(rankMainPan, BorderLayout.CENTER);

		rankPan.setBorder(new EmptyBorder(5, 20, 5, 20));

		rankDialog.setSize(350, 500);

		rankDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				rankDialog.setVisible(false);
			}
		});

		btnOK.addActionListener(this);

		rankDialog.setLocationRelativeTo(mainFrame);

		rankDialog.setVisible(true);
	}

	/** 회원가입 다이얼로그 창 */
	private void launchSignUpDialog() {

		signDialog = new JDialog(mainFrame, "회원가입", true);
		signUpMainPan = new JPanel(new BorderLayout());
		signUpTextPan = new JPanel(new GridLayout(0, 2));
		signUpButtonPan = new JPanel(new FlowLayout());

		btnSubmit = new JButton("회원가입");
		btnSignCancel = new JButton("취소");

		lblNickNameInput = new JLabel("닉네임 (ID) : ");
		lblPasswordInput = new JLabel("비밀번호 입력 : ");
		lblPasswordReInput = new JLabel("비밀번호 확인 : ");

		txtNickNameInput = new JTextField(20);
		pwfPasswordInput = new JPasswordField(20);
		pwfPasswordReInput = new JPasswordField(20);

		signUpTextPan.add(lblNickNameInput);
		signUpTextPan.add(txtNickNameInput);
		signUpTextPan.add(lblPasswordInput);
		signUpTextPan.add(pwfPasswordInput);
		signUpTextPan.add(lblPasswordReInput);
		signUpTextPan.add(pwfPasswordReInput);

		signUpButtonPan.add(btnSubmit);
		signUpButtonPan.add(btnSignCancel);

		signUpMainPan.add(signUpTextPan, BorderLayout.CENTER);
		signUpMainPan.add(signUpButtonPan, BorderLayout.SOUTH);

		signDialog.add(signUpMainPan);

		signUpMainPan.setBorder(new EmptyBorder(5, 20, 5, 20));

		signDialog.setSize(350, 200);

		signDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				signDialog.setVisible(false);
			}
		});

		btnSubmit.addActionListener(this);
		btnSignCancel.addActionListener(this);
		txtNickNameInput.addActionListener(this);
		pwfPasswordInput.addActionListener(this);
		pwfPasswordReInput.addActionListener(this);

		signDialog.setLocationRelativeTo(mainFrame);

		signDialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == btnNewQuiz) {

			try {

				viewQuiz(model.loadQuizSet());

			} catch (QuizException e1) {
				e1.printStackTrace();
			}

		} else if (source == btnBeforeQuiz) {

			beforeQuiz();

		} else if (source == btnNextQuiz) {

			nextQuiz();

		} else if (source == btnRankTable) {

			try {
				User[] userRanking = model.loadRankBoard(10);
				launchRankDialog(userRanking);
			} catch (QuizException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (source == btnOK) {
			rankDialog.setVisible(false);
		} else if (source == btnSaveSolutions) {

			try {

				if (!solutionsSend[quizIndex]) {
					quizSolutionsCount++;
					solutionsSend[quizIndex] = true;
					if (quizSolutionsCount == 10) {
						btnSendSolutions.setEnabled(true);
						btnSaveSolutions.setEnabled(false);
					}
					model.saveAnswer(txtInputSolutions.getText(), quizIndex);
				} else {
					JOptionPane.showMessageDialog(null, "이미 답을 입력하셨습니다");
				}
			} catch (QuizException e1) {
				e1.printStackTrace();
			}

		} else if (source == btnSendSolutions) {

			try {

				quizSolutionsCount = 0;
				btnSendSolutions.setEnabled(false);
				btnSaveSolutions.setEnabled(true);
				int score = model.checkCorrectAnswer();
				for (int index = 0; index < solutionsSend.length; index++) {
					solutionsSend[index] = false;
				}
				JOptionPane.showMessageDialog(null, score + "점 입니다.");

			} catch (QuizException e1) {
				e1.printStackTrace();
			}

		} else if (source == btnExit) {

			mainFrame.dispose();

		} else if (source == btnSignIn) {

			String nickname = tfNickname.getText().trim();
			String password = tfPassword.getText().trim();
			if (!(nickname.equals("") || password.equals(""))) {
				try {
					model.signIn(new User(nickname, password));
					conDialog.setVisible(false);
				} catch (QuizException e1) {
					e1.printStackTrace();
				}
			} else if (nickname.equals("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요");
			} else if (password.equals("")) {
				JOptionPane.showMessageDialog(null, "패스워드를 입력하세요");
			}

		} else if (source == btnCancel) {
			mainFrame.dispose();
		} else if (source == btnNewJoin) {

			launchSignUpDialog();

		} else if (source == btnSubmit) {
			if (!txtNickNameInput.getText().equals("")) {
				if (!pwfPasswordInput.getText().equals("")) {
					if (!pwfPasswordReInput.getText().equals("")) {
						if (pwfPasswordInput.getText().equals(pwfPasswordReInput.getText())) {
							JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
							try {
								model.addUser(new User(txtNickNameInput.getText(),
										pwfPasswordInput.getText().toString()));
							} catch (QuizException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "패스워드가 일치하지 않습니다.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "패스워드를 확인하시기 바랍니다.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "패스워드를 입력하시기 바랍니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "닉네임을 입력하시기 바랍니다.");
			}
		} else if (source == btnSignCancel) {
			signDialog.setVisible(false);
		}

	}

	/** 다음 퀴즈 버튼 */
	private void nextQuiz() {

		if (quizIndex >= 9) {
			JOptionPane.showMessageDialog(null, "마지막 문제입니다");
		} else {
			quizIndex++;
			String text = quizs[quizIndex].getQuiz();
			System.out.println(text);
			String[] textString;
			StringBuilder text2 = new StringBuilder("<html>");
			if (text.length() > 60) {
				textString = new String[text.length() / 60 + 1];
				for (int index = 0; index < textString.length; index++) {
					// textString[index] = text.substring(10, 11*index);
					if (60 + index * 60 + 1 < text.length()) {
						text2.append(text.substring(0 + index * 60 + 1, 60 + index * 60 + 1) + "<br>");
					} else {
						text2.append(text.substring(index * 60 + 1, text.length() - 1));
					}
				}
				text2.append("</html>");
				System.out.println(text2);
				System.out.println(text2.toString());
				lblQuiz.setText(text2.toString());
			} else {
				lblQuiz.setText(text);
			}
			if (quizs[quizIndex].getQuizType() == Quiz.OBJECTIVE_QUIZ) {
				String[] cases = quizs[quizIndex].getCases();
				StringBuilder caseBuilder = new StringBuilder("<html>");
				for (int index = 0; index < cases.length; index++) {
					if (!(index == cases.length - 1)) {
						caseBuilder.append(cases[index] + "<br>");
					} else {
						caseBuilder.append(cases[index]);
					}
				}
				caseBuilder.append("</html>");
				lblExample.setText(caseBuilder.toString());
			}

		}

	}

	/** 이전 퀴즈 버튼 */
	private void beforeQuiz() {

		if (quizIndex <= 0) {
			JOptionPane.showMessageDialog(null, "첫 번째 문제입니다");
		} else {
			quizIndex--;
			String text = quizs[quizIndex].getQuiz();
			System.out.println(text);
			String[] textString;
			StringBuilder text2 = new StringBuilder("<html>");
			if (text.length() > 60) {
				textString = new String[text.length() / 60 + 1];
				for (int index = 0; index < textString.length; index++) {
					// textString[index] = text.substring(10, 11*index);
					if (60 + index * 60 + 1 < text.length()) {
						text2.append(text.substring(0 + index * 60 + 1, 60 + index * 60 + 1) + "<br>");
					} else {
						text2.append(text.substring(index * 60 + 1, text.length() - 1));
					}
				}
				text2.append("</html>");
				System.out.println(text2);
				System.out.println(text2.toString());
				lblQuiz.setText(text2.toString());
			} else {
				lblQuiz.setText(text);
			}
			if (quizs[quizIndex].getQuizType() == Quiz.OBJECTIVE_QUIZ) {
				String[] cases = quizs[quizIndex].getCases();
				StringBuilder caseBuilder = new StringBuilder("<html>");
				for (int index = 0; index < cases.length; index++) {
					if (!(index == cases.length - 1)) {
						caseBuilder.append(cases[index] + "<br>");
					} else {
						caseBuilder.append(cases[index]);
					}
				}
				caseBuilder.append("</html>");
				lblExample.setText(caseBuilder.toString());
			}

		}

	}

	/** 퀴즈 가져오기 버튼 */
	private void viewQuiz(Quiz[] quizs) {
		this.quizs = quizs;
		String quizText = quizs[0].getQuiz();
		String[] textString;
		StringBuilder textBuilder = new StringBuilder("<html>");
		if (quizText.length() > 60) {
			textString = new String[quizText.length() / 60 + 1];
			for (int index = 0; index < textString.length; index++) {
				// textString[index] = text.substring(10, 11*index);
				if (60 + index * 60 + 1 < quizText.length()) {
					textBuilder.append(quizText.substring(0 + index * 60 + 1, 60 + index * 60 + 1) + "<br>");
				} else {
					textBuilder.append(quizText.substring(index * 60 + 1, quizText.length() - 1));
				}
			}
			textBuilder.append("</html>");
			lblQuiz.setText(textBuilder.toString());
		} else {
			lblQuiz.setText(quizText);
		}
		if (quizs[0].getQuizType() == Quiz.OBJECTIVE_QUIZ) {
			String[] cases = quizs[0].getCases();
			StringBuilder caseBuilder = new StringBuilder("<html>");
			for (int index = 0; index < cases.length; index++) {
				if (!(index == cases.length - 1)) {
					caseBuilder.append(cases[index] + "<br>");
				} else {
					caseBuilder.append(cases[index]);
				}
			}
			caseBuilder.append("</html>");
			lblExample.setText(caseBuilder.toString());
		}

	}

	public static void main(String[] args) {
		QuizGUI gui = new QuizGUI(null);
	}

}
