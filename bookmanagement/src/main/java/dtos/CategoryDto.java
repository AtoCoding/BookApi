/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Ato
 */
public class CategoryDto {

    private int bcId;
    private int categoryId;
    private String categoryName;

    public CategoryDto() {
    }

    public CategoryDto(int bcId, int categoryId, String categoryName) {
        this.bcId = bcId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getBcId() {
        return bcId;
    }

    public void setBcId(int bcId) {
        this.bcId = bcId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryDto{" + "bcId=" + bcId + ", categoryId=" + categoryId + ", categoryName=" + categoryName + '}';
    }

}
