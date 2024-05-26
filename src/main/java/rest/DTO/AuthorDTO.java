package rest.DTO;

import entities.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuthorDTO {
    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;

    private String name;
}
