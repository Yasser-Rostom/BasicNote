package com.yasser.memorizewords.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yasser.memorizewords.data.model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Delete
    void delete(Category category);
    @Query("DELETE FROM category_Table")
    void deleteAll();

    @Update
    void update(Category category);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("Select * from category_Table ORDER BY id ASC")
    LiveData<List<Category>> getCategory();

    @Query("SELECT COUNT(*) FROM category_Table WHERE category = :category;")
    int countCategory(String category);
}
