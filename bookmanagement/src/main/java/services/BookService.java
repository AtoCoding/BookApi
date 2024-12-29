/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dtos.BookDetailsDto;
import dtos.BookSummarizeDto;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repositories.BookRepository;

/**
 *
 * @author Ato
 */
public class BookService {
    private final BookRepository bookRepository;

    public BookService() {
        bookRepository = new BookRepository();
    }
    
    public List<BookSummarizeDto> getBookList() throws SQLException, ClassNotFoundException {
        List<BookSummarizeDto> bookDtoList = bookRepository.getBookList();
        if(bookDtoList == null) bookDtoList = new ArrayList<>();
        return bookDtoList;
    }
    
    public boolean createNewBook(BookDetailsDto bookDetailsDto) throws ClassNotFoundException, SQLException {
        Date formatDate = Date.valueOf(bookDetailsDto.getDateCreated());
        bookDetailsDto.setFormatDateCreated(formatDate);
        return bookRepository.createNewBook(bookDetailsDto);
    }
    
    public BookDetailsDto getBookDetails(int bookId) throws SQLException, ClassNotFoundException {
        return bookRepository.getBookDetails(bookId);
    }
}
