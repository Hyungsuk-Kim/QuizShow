package quiz.common.helper;

public class QuizException extends Exception{

	private static final long serialVersionUID = 8134315679182176740L;
	
	public QuizException() {
		super();
	}
	public QuizException(String message) {
		super(message);
	}
	public QuizException(Throwable t) {
		super(t);
	}
	public QuizException(String message, Throwable t) {
		super(message, t);
	}

}
