package services;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Model
@SessionScoped
public class BookLoader implements Serializable {
    @Getter
    @Setter
    private Map<Long, CompletableFuture> libraryBooks = new HashMap<>();

    public void loadLibraryBooks(Long libraryId)
    {
        libraryBooks.put(libraryId, CompletableFuture.runAsync(() -> loadBooks()));
    }

    public void removeLibrary(Long libraryId)
    {
        libraryBooks.remove(libraryId);
    }

    private void loadBooks(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }
}
