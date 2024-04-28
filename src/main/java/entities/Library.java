package entities;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@NamedQueries({
        @NamedQuery(name = "Library.findAll", query = "select l from Library as l"),
        @NamedQuery(name = "Library.findByName", query = "select l from Library as l where l.name like :name")
})
@Table(name = "Library", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
},
indexes = {
        @Index(name = "player_name_index", columnList = "name")
})
public class Library implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1)
    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Library(){}
}