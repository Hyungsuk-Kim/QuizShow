package quiz.network.packet;

import quiz.common.domain.User;
import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class DeleteUserPacket extends PacketObject {

	private static final long serialVersionUID = 6515140320592558337L;
	private User user;

	public DeleteUserPacket(User user) {
		this.user = user;
	}
	
	@Override
	public void execute(QuizModel model) {
		try {
			model.deleteUser(user);
			this.result = new SucceedPacket();
		} catch (QuizException e) {
			this.exception = e;
		}
	}

}
