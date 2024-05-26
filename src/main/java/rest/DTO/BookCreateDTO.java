package rest.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BookCreateDTO {
    public String name;

    public long library;

    public int pages;

    public Date checkOutDate;

    public Date estimatedReturnDate;

    public List<Long> authors;

    public Integer version;
}
