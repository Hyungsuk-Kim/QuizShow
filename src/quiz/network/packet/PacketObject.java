package quiz.network.packet;

import java.io.Serializable;

import quiz.mvc.interfaces.QuizModel;

public abstract class PacketObject implements Serializable{
	
	private static final long serialVersionUID = 7413463516051102249L;
	
	protected Object result;
	protected Exception exception = null;
	/*protected int command; // 위의 public static final int의 값 사용
	
	public static final int REQ_REGISTER_USER = 1001; // 사용자 등록
	public static final int REQ_UPDATE_USER = 1002; // 사용자 정보 수정
	public static final int REQ_DELETE_USER = 1003; // 사용자 삭제
	public static final int REQ_SIGN_IN = 1101; // 로그인
	public static final int REQ_SIGN_OUT = 1102; // 로그아웃
	public static final int REQ_SEND_MESSAGE = 1201; // 메시지 전송
	public static final int REQ_LOAD_QUIZ = 1301; // 퀴즈 요청
	
	public static final int ERR_ALREADY_EXIST_USER = 2001; // 이미 존재하는 사용자일 경우(회원가입) 
	public static final int ERR_VERIFICATION_PASSWORD = 2002; // 회원가입 시 password와 password 확인 결과가 다른 경우
	public static final int ERR_INVALID_USER = 2003; // 사용자 변경, 삭제 시 잘못된 사용자일 경우
	public static final int ERR_INVALID_PASSWORD = 2103; // 잘못된 password
	public static final int ERR_QUIZ_SERVER_BLOCKED = 2301; // DB or Server 접속이 안될 경우
	
	public static final int SUCCEED_REGISTER_USER = 3001;
	public static final int FAILURE_REGISTER_USER = 3011;
	public static final int SUCCEED_UPDATE_USER = 3002;
	public static final int FAILURE_UPDATE_USER = 3012;
	public static final int SUCCEED_DELETE_USER = 3003;
	public static final int FAILURE_DELETE_USER = 3013;
	public static final int SUCCEED_SIGN_IN = 3101;
	public static final int FAILURE_SIGN_IN = 3111;
	public static final int SUCCEED_SIGN_OUT = 3102;
	public static final int FAILURE_SIGN_OUT = 3112;
	public static final int SUCCEED_SEND_MESSAGE = 3201;
	public static final int FAILURE_SEND_MESSAGE = 3211;
	public static final int SUCCEED_LOAD_QUIZ = 3301;
	public static final int FAILURE_LOAD_QUIZ = 3311;*/
	
	// Methods
	/** Using Server`s model implementation,
	 * this method called when receive this packet object from ObjectInputStream connected to Client side.
	 */
	public abstract void execute(QuizModel model);
	
	/** Using Client`s model implementation, 
	 * this method called when receive this packet object from ObjectInputStream connected to Server side.
	 */
	public Object getResult() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        }
        return this.result;
    }
}
