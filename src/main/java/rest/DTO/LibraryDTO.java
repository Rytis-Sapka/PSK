package rest.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDTO {
    public LibraryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;

    private String name;
}
