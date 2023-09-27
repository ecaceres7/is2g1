package py.com.una.progest.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import py.com.una.progest.models.BoardData;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDataRequest {
	private List<BoardData> boardDataReq = new ArrayList<>();

	public void addBoardData(@NotNull List<BoardData> boardDataReq){
		if (this.boardDataReq.isEmpty()) {
			this.boardDataReq = boardDataReq;
		}else {
			boardDataReq.forEach(data -> this.boardDataReq.add(data));
		}
	};

}
