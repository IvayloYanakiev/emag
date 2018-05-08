package com.emag.dao;

import com.emag.config.ConstantsSQL;
import com.emag.exception.CategoryException;
import com.emag.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, Category> getAllCategories() throws CategoryException {
        String getAllCategories = ConstantsSQL.GET_ALL_CATEGORIES;
        Map<Long, Category> categories = null;
        try {
            categories = jdbcTemplate.query(getAllCategories, new ResultSetExtractor<Map<Long, Category>>() {

                @Override
                public Map<Long, Category> extractData(ResultSet rs) throws SQLException {
                    Map<Long, Category> myCategories = new HashMap<Long, Category>();

                    while (rs.next()) {
                        try {
                            Long mainID = rs.getLong("id");
                            String mainName = rs.getString("main_name");
                            Long middleID = rs.getLong("middle_id");
                            String middleName = rs.getString("middle_name");

                            if (!myCategories.containsKey(mainID)) {
                                Category mainCategory = new Category(mainID, mainName);
                                myCategories.put(mainID, mainCategory);
                            }

                            myCategories.get(mainID).addCategory(new Category(middleID, middleName));
                        } catch (CategoryException e) {
                            throw new SQLException(e);
                        }
                    }
                    return myCategories;
                }
            });
        } catch (Exception e) {
            throw new CategoryException(e.getMessage(), e);
        }
        return categories;
    }
}
