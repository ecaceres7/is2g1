package py.com.una.progest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardData {
    private Long id;
    private String title;
    private String description;
    private Long column;
}
