package quiz.network.packet;

import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class ChangeUserInfoPacket extends PacketObject {

	private static final long serialVersionUID = -4690357047839619525L;
	private User user;

	public ChangeUserInfoPacket(User user) {
		this.user = user;
	}
	
	@Override
	public void execute(QuizModel model) {
		try {
			model.updateUser(user);
			this.result = new SucceedPacket();
		} catch (QuizException e) {
			this.exception = e;
		}
	}

}
