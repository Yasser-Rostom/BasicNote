package com.yasser.memorizewords.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yasser.memorizewords.data.model.Category;
import com.yasser.memorizewords.data.repository.Repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CateViewModel extends AndroidViewModel {

    private Repository repository;
    private final LiveData<List<Category>> allCategories;

    public CateViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new Repository(application);
        allCategories = repository.getAllCategory();
    }

    public  void deleteCategory
            (Category category) {repository.deleteCategory(category);}

    public void insertCat(Category category)
            {
                repository.insertCategory(category);
            }
    public void update(Category category) { repository.updateCategory(category); }
    public void updateWordsByCategory (String old, String newCategory)
    {
        repository.updateByCategory(old,newCategory);
    }
    public void deleteAllCategories()
    {repository.deleteAllCategories();
    }
    public int countCategory(String category)
    {
        int count = repository.countCategory(category);
        return count;
    }
    public LiveData<List<Category>> getAllCategories()
    { return allCategories; }
}
