/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Ato
 */
public class BookDetailsDto extends BookSummarizeDto {
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreated;
    private int quantity;
    List<CategoryDto> categoryList;

    
    public BookDetailsDto() {
    }

    public BookDetailsDto(Date dateCreated, int quantity, List<CategoryDto> categoryList) {
        this.dateCreated = dateCreated;
        this.quantity = quantity;
        this.categoryList = categoryList;
    }

    public BookDetailsDto(Date dateCreated, int quantity, List<CategoryDto> categoryList, int bookId, String bookName, String author) {
        super(bookId, bookName, author);
        this.dateCreated = dateCreated;
        this.quantity = quantity;
        this.categoryList = categoryList;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<CategoryDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDto> categoryList) {
        this.categoryList = categoryList;
    }
    
    @Override
    public String toString() {
        return "BookDetailsDto{" + "bookId=" + super.getBookId() + ", bookName=" + super.getBookName() + ", author=" + super.getAuthor() + ", dateCreated=" + dateCreated + ", quantity=" + quantity + ", categoryList=" + categoryList + '}';
    }

}
