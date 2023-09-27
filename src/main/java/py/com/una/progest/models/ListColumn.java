package py.com.una.progest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "columns")
public class ListColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public ListColumn(String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }
}
