package net.sourceforge.jfox.petstore.dao;

import java.sql.SQLException;
import java.util.List;

import net.sourceforge.jfox.petstore.entity.Category;

public interface CategoryDAO {

    List<Category> getCategoryList() throws SQLException;

    Category getCategory(String categoryId) throws SQLException;

}