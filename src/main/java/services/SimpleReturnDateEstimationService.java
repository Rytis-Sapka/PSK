package services;

import entities.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Calendar;
import java.util.Date;

@ApplicationScoped
@Alternative
public class SimpleReturnDateEstimationService implements EstimationService {
    public Date estimateReturnDate(Book book) {
        if (book.getCheckOutDate() == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(book.getCheckOutDate());
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        return calendar.getTime();
    }
}
