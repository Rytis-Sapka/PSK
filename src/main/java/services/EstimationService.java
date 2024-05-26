package services;

import entities.Book;

import java.util.Date;

public interface EstimationService {
    Date estimateReturnDate(Book book);
}
