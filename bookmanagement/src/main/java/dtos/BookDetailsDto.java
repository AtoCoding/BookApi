/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ato
 */
public class BookDetailsDto extends BookSummarizeDto {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate dateCreated;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date formatDateCreated;
    private int quantity;
    List<CategoryDto> categoryList;

    
    public BookDetailsDto() {
    }

    public BookDetailsDto(LocalDate dateCreated, Date formatDateCreated, int quantity, List<CategoryDto> categoryList) {
        this.dateCreated = dateCreated;
        this.formatDateCreated = formatDateCreated;
        this.quantity = quantity;
        this.categoryList = categoryList;
    }

    public BookDetailsDto(LocalDate dateCreated, Date formatDateCreated, int quantity, List<CategoryDto> categoryList, int bookId, String bookName, String author) {
        super(bookId, bookName, author);
        this.dateCreated = dateCreated;
        this.formatDateCreated = formatDateCreated;
        this.quantity = quantity;
        this.categoryList = categoryList;
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getFormatDateCreated() {
        return formatDateCreated;
    }

    public void setFormatDateCreated(Date formatDateCreated) {
        this.formatDateCreated = formatDateCreated;
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
        return "BookDetailsDto{" + "bookId=" + super.getBookId() + ", bookName=" + super.getBookName() + ", author=" + super.getAuthor() + ", dateCreated=" + dateCreated + ", formatDateCreated=" + formatDateCreated + ", quantity=" + quantity + ", categoryList=" + categoryList + '}';
    }
  
}
