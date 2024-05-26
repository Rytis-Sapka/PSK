package entities;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "select b from Book as b"),
})
@Table(name = "Book", schema = "public")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1)
    @Basic(optional = false)
    private String name;

    @ManyToOne(optional = false)
    private Library library;

    @Basic(optional = false)
    private int pages;

    @Basic(optional = true)
    private Date checkOutDate;

    @Basic(optional = true)
    private Date estimatedReturnDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Author> authors = new ArrayList<>();

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;
}
