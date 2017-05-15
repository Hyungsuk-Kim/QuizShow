package quiz.network.packet;

import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class SignInPacket extends PacketObject {

	private static final long serialVersionUID = 8467727162685146663L;
	private User user;

	public SignInPacket(User user) {
		this.user = user;
	}
	
	@Override
	public void execute(QuizModel model) {
		try {
			result = model.signIn(user);
		} catch (QuizException e) {
			this.exception = e;
		}
	}

}
