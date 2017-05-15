package quiz.network.packet;


import quiz.common.domain.User;
import quiz.mvc.interfaces.QuizModel;

public class RegiserUserPacket extends PacketObject {
	
	private static final long serialVersionUID = -6653784639841272096L;
	private User user;

	public RegiserUserPacket(User user) {
		this.user = user;
	}
	
	@Override
	public void execute(QuizModel model) {
		try {
			model.addUser(user);
			this.result = new SucceedPacket();
		} catch (Exception e) {
			this.exception = e;
		}
	}

}
