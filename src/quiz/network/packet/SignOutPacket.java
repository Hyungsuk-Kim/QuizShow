package quiz.network.packet;

import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class SignOutPacket extends PacketObject {

	private static final long serialVersionUID = 8209556641760527089L;
	private User user;

	public SignOutPacket(User user) {
		this.user = user;
	}
	
	@Override
	public void execute(QuizModel model) {
		try {
			model.signOut(user);
			this.result = new SucceedPacket();
		} catch (QuizException e) {
			this.exception = e;
		}
	}
	
}
