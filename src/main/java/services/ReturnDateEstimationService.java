package services;

import entities.Book;

import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;
import java.util.Date;

@ApplicationScoped
public class ReturnDateEstimationService {
    private static final int PAGES_PER_DAY = 25;

    public Date estimateReturnDate(Book book) {
        if (book.getCheckOutDate() == null)
            return null;

        int daysToRead = (int) Math.ceil((double) book.getPages() / PAGES_PER_DAY);
        Date returnDate = addDaysToDate(book.getCheckOutDate(), daysToRead);

        Date currentDate = new Date();
        if (currentDate.before(returnDate))
            return returnDate;

        return addDaysToDate(currentDate, 1);
    }

    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}
