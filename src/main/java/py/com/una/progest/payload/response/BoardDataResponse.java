package py.com.una.progest.payload.response;

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
public class BoardDataResponse {
	private List<BoardData> boardDataRes = new ArrayList<>();

	public void addBoardData(@NotNull List<BoardData> boardDataRes){
		if (this.boardDataRes.isEmpty()) {
			this.boardDataRes = boardDataRes;
		}else {
			boardDataRes.forEach(data -> this.boardDataRes.add(data));
		}
	}

}
