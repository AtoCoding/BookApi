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
        if (bookDtoList == null) {
            bookDtoList = new ArrayList<>();
        }
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

    public boolean updateBookByBookId(BookDetailsDto bookDetailsDto) throws ClassNotFoundException, SQLException {
        Date formatDate = Date.valueOf(bookDetailsDto.getDateCreated());
        bookDetailsDto.setFormatDateCreated(formatDate);
        List<Integer> bcIdList = getBcIdListByBookId(bookDetailsDto.getBookId());
        String action = "none";
        if (bcIdList == null) {
            return false;
        } else {
            /*
            Số lượng db list bằng với client list
            ---> kiểm tra bcid ---> bcid khác nhau ---> ghi đè <---> ko làm gì
            */
            
            /*
            Số lượng db list nhỏ hơn client list
            ---> xóa trong db ---> thêm mới 
            */
            
            /*
            Số lượng db list lớn hơn client list
            ---> xóa trong db ---> thêm mới 
            */
            if (bcIdList.isEmpty()) {
                action = "add";
            } else if (bcIdList.size() == bookDetailsDto.getCategoryList().size()) {
                for(int i = 0; i < bookDetailsDto.getCategoryList().size(); i++) {
                    if(!bcIdList.contains(bookDetailsDto.getCategoryList().get(i).getBcId())) {
                        action = "override";
                        break;
                    }
                }
            } else if (bcIdList.size() != bookDetailsDto.getCategoryList().size()) {
                action = "delete&add";
            }
            return bookRepository.updateBookByBookId(bookDetailsDto, bcIdList, action);
        }
    }
    
    private List<Integer> getBcIdListByBookId(int bookId) throws SQLException, ClassNotFoundException {
        return bookRepository.getBcIdListByBookId(bookId);
    }
}
