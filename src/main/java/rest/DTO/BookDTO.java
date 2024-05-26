package rest.DTO;

import entities.Author;
import entities.Library;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BookDTO {
    public Long id;

    public String name;

    public LibraryDTO library;

    public int pages;

    public Date checkOutDate;

    public Date estimatedReturnDate;

    public List<AuthorDTO> authors = new ArrayList<>();

    public Integer version;
}
