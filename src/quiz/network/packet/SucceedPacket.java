package quiz.network.packet;

import quiz.mvc.interfaces.QuizModel;

public class SucceedPacket extends PacketObject {

	private static final long serialVersionUID = -8104653063900753352L;
	
	public SucceedPacket() {
		this.exception = null;
		this.result = this;
	}
	
	@Override
	public void execute(QuizModel model) {
		// TODO Auto-generated method stub
		
	}

}
