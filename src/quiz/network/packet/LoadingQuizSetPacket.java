package quiz.network.packet;

import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class LoadingQuizSetPacket extends PacketObject {

	private static final long serialVersionUID = -1643096689588472667L;

	@Override
	public void execute(QuizModel model) {
		try {
			result = model.loadQuizSet();
		} catch (QuizException e) {
			this.exception = e;
		}
	}

}
