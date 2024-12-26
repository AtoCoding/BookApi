/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import dtos.CategoryDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Ato
 */
public class CategoryRepository {
    public List<CategoryDto> getCategoryList() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if(con != null) {
                String query = "select * from Category";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                while(rs.next()) {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setCategoryId(rs.getInt("CategoryId"));
                    categoryDto.setCategoryName(rs.getNString("CategoryName"));
                    categoryDtoList.add(categoryDto);
                }
                return categoryDtoList;
            }
        } finally {
            if(rs != null) rs.close();
            if(stm != null) stm.close();
            if(con != null) con.close();
        }
        return null;
    }
}
