package services;

import entities.Author;
import persistence.AuthorDAO;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Decorator
@Dependent
public abstract class AuthorDecorator implements IAuthorCreation {
    @Inject
    @Delegate
    private IAuthorCreation authorDAO;

    @Override
    @Transactional
    public void save(Author author) {
        author.setName(capitalizeName(author.getName()));
        authorDAO.save(author);
    }

    private String capitalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        String[] words = name.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
                capitalized.append(" ");
            }
        }

        return capitalized.toString().trim();
    }
}
