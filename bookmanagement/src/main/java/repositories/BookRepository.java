/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import dtos.BookDetailsDto;
import dtos.BookSummarizeDto;
import dtos.CategoryDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Ato
 */
public class BookRepository {

    public List<BookSummarizeDto> getBookList() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<BookSummarizeDto> bookDtoList = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String query = "select * from Book";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                while (rs.next()) {
                    BookSummarizeDto bookDto = new BookSummarizeDto();
                    bookDto.setBookId(rs.getInt("BookId"));
                    bookDto.setBookName(rs.getNString("BookName"));
                    bookDto.setAuthor(rs.getNString("Author"));
                    bookDtoList.add(bookDto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return bookDtoList;
    }

    public BookDetailsDto getBookDetails(int bookId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        BookDetailsDto bookDetailsDto = new BookDetailsDto();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String query = "select Book.BookId, BookName, Author, DateCreated, Quantity, Category.CategoryId, CategoryName, BCID "
                        + "from Book "
                        + "left join BookCategory on Book.BookId = BookCategory.BookId "
                        + "left join Category on BookCategory.CategoryId = Category.CategoryId "
                        + "where Book.BookId = ?";
                stm = con.prepareStatement(query);
                stm.setInt(1, bookId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    bookDetailsDto.setBookId(rs.getInt("BookId"));
                    bookDetailsDto.setBookName(rs.getNString("BookName"));
                    bookDetailsDto.setAuthor(rs.getNString("Author"));
                    bookDetailsDto.setFormatDateCreated(rs.getDate("DateCreated"));
                    bookDetailsDto.setQuantity(rs.getInt("Quantity"));
                    categoryDtoList.add(new CategoryDto(
                            rs.getInt("BCID"),
                            rs.getInt("CategoryId"),
                            rs.getNString("CategoryName")));
                    bookDetailsDto.setCategoryList(categoryDtoList);
                }
                return bookDetailsDto;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
    public List<Integer> getBcIdListByBookId(int bookId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Integer> bcIdList = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String query = "select * from BookCategory where BookId = ?";
                stm = con.prepareStatement(query);
                stm.setInt(1, bookId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    bcIdList.add(rs.getInt("BCID"));
                }
                return bcIdList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean createNewBook(BookDetailsDto bookDetailsDto) throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                con.setAutoCommit(false);
                int bookId = createBook(con, bookDetailsDto);
                if (bookId > 0) {
                    boolean isBookCategoryCreated = createBookCategory(con, bookId, bookDetailsDto);
                    if (isBookCategoryCreated) {
                        con.commit();
                        return true;
                    }
                }
                con.rollback();
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    private int createBook(Connection con, BookDetailsDto bookDetailsDto) throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String query = "insert into Book(BookName, Author, DateCreated, Quantity, Username) values (?, ?, ?, ?, ?)";
            stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setNString(1, bookDetailsDto.getBookName());
            stm.setNString(2, bookDetailsDto.getAuthor());
            stm.setDate(3, bookDetailsDto.getFormatDateCreated());
            stm.setInt(4, bookDetailsDto.getQuantity());
            stm.setString(5, "admin");
            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return -1;
    }

    private boolean createBookCategory(Connection con, int bookId, BookDetailsDto bookDetailsDto) throws SQLException {
        PreparedStatement stm = null;
        try {
            String query = "insert into BookCategory(BookId, CategoryId) values (?, ?)";
            stm = con.prepareStatement(query);
            for (int i = 0; i < bookDetailsDto.getCategoryList().size(); i++) {
                stm.setInt(1, bookId);
                stm.setInt(2, bookDetailsDto.getCategoryList().get(i).getCategoryId());
                stm.addBatch();
            }
            int[] results = stm.executeBatch();
            for (int result : results) {
                if (result == Statement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
    
    public boolean updateBookByBookId(BookDetailsDto bookDetailsDto, List<Integer> bcIdList, String action) throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                con.setAutoCommit(false);
                boolean isBookUpdated = updateBook(con, bookDetailsDto);
                boolean isBookCategoryModify = true;
                if (action.equals("override")) {
                    isBookCategoryModify = updateBookCategory(con, bookDetailsDto, bcIdList);
                } else if (action.equals("add")) {
                    isBookCategoryModify = createBookCategory(con, bookDetailsDto.getBookId(), bookDetailsDto);
                } else if (action.equals("delete&add")) {
                    boolean isBookCategoryDeleted = deleteBookCategory(con, bookDetailsDto);
                    if(isBookCategoryDeleted) {
                        isBookCategoryModify = createBookCategory(con, bookDetailsDto.getBookId(), bookDetailsDto);
                    } else {
                        isBookCategoryModify = false;
                    }
                }
                
                if(isBookUpdated && isBookCategoryModify) {
                    con.commit();
                    return true;
                }
                con.rollback();
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    private boolean deleteBookCategory(Connection con, BookDetailsDto bookDetailsDto) throws SQLException {
        PreparedStatement stm = null;
        try {
            String query = "delete from BookCategory where BookId = ?";
            stm = con.prepareStatement(query);
            stm.setInt(1, bookDetailsDto.getBookId());
            int affectedRows = stm.executeUpdate();
            if(affectedRows > 0) return true;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return false;
    }

    private boolean updateBook(Connection con, BookDetailsDto bookDetailsDto) throws SQLException {
        PreparedStatement stm = null;
        try {
            String query = "update Book set BookName = ?, Author = ?, DateCreated = ?, Quantity = ?, Username = ? where BookId = ?";
            stm = con.prepareStatement(query);
            stm.setNString(1, bookDetailsDto.getBookName());
            stm.setNString(2, bookDetailsDto.getAuthor());
            stm.setDate(3, bookDetailsDto.getFormatDateCreated());
            stm.setInt(4, bookDetailsDto.getQuantity());
            stm.setString(5, "admin");
            stm.setInt(6, bookDetailsDto.getBookId());
            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return false;
    }

    private boolean updateBookCategory(Connection con, BookDetailsDto bookDetailsDto, List<Integer> bcIdList) throws SQLException {
        PreparedStatement stm = null;
        try {
            String query = "update BookCategory set CategoryId = ? where BCID = ?";
            stm = con.prepareStatement(query);
            for (int i = 0; i < bookDetailsDto.getCategoryList().size(); i++) {
                stm.setInt(1, bookDetailsDto.getCategoryList().get(i).getCategoryId());
                stm.setInt(2, bcIdList.get(i));
                stm.addBatch();
            }
            int[] results = stm.executeBatch();
            for (int result : results) {
                if (result == Statement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}
