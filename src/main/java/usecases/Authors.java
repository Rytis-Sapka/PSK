package usecases;

import mybatis.model.Author;
import lombok.Getter;
import lombok.Setter;
import mybatis.dao.AuthorMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
public class Authors implements Serializable {
    @Inject
    private AuthorMapper authorMapper;

    @Getter
    @Setter
    private Author authorToCreate = new Author();

    @Getter
    private List<Author>   allAuthors;

    @PostConstruct
    public void init() {
        loadAllAuthors();
    }

    @Transactional
    public void createAuthor() {
        this.authorMapper.insert(authorToCreate);
    }

    private void loadAllAuthors() {
        this.allAuthors = authorMapper.selectAll();
    }
}