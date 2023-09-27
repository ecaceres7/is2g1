package py.com.una.progest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTaskRequest {
	@NotNull
	private Long board;
	@NotBlank
	private String title;

	private String description;


	private Date expirationDate;
}
