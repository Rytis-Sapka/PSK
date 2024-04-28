package entities;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@Table(name = "Author", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
@NamedQueries({
        @NamedQuery(name = "Author.findAll", query = "select a from Author as a"),
})
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1)
    @Basic(optional = false)
    private String name;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
}
