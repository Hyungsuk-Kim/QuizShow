package quiz.network.packet;

import quiz.common.helper.QuizException;
import quiz.mvc.interfaces.QuizModel;

public class LoadingRankBoardPacket extends PacketObject {

	private static final long serialVersionUID = 6492116742638980086L;
	int limit;

	public LoadingRankBoardPacket(int limit) {
		this.limit = limit;
	}
	
	@Override
	public void execute(QuizModel model) {
		try {
			result = model.loadRankBoard(limit);
		} catch (QuizException e) {
			this.exception = e;
		}
	}
	
}
