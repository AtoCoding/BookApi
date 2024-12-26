/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dtos.CategoryDto;
import java.sql.SQLException;
import java.util.List;
import repositories.CategoryRepository;

/**
 *
 * @author Ato
 */
public class CategoryService {
    private final CategoryRepository categoryRepository;
    
    public CategoryService() {
        categoryRepository = new CategoryRepository();
    }
    
    public List<CategoryDto> getCategoryList() throws ClassNotFoundException, SQLException {
        return categoryRepository.getCategoryList();
    }
}
