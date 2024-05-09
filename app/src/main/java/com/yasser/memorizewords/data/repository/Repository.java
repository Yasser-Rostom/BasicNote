package com.yasser.memorizewords.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.yasser.memorizewords.data.model.Word;
import com.yasser.memorizewords.data.dao.WordDAO;
import com.yasser.memorizewords.WordRoomDB;
import com.yasser.memorizewords.data.dao.CategoryDAO;
import com.yasser.memorizewords.data.model.Category;

import java.util.List;

public class Repository {
    private WordDAO mWordDao;
    private LiveData<List<Word>> mAllWords;
    private LiveData<List<Word>> someWords;
    private CategoryDAO mCategoryDao;
    private LiveData<List<Category>> allCategory;
    private LiveData<List<Category>>  categories;


    public Repository(Application application) {
        WordRoomDB db = WordRoomDB.getDatabase(application);

        mWordDao = db.wordDao();
        mAllWords = mWordDao.getOrderedWords();



        mCategoryDao = db.categoryDAO();
        allCategory = mCategoryDao.getCategory();


    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public LiveData<List<Category>> getAllCategory() {return allCategory;}

    public LiveData<List<Word>> getWordsByCategory(String category) {
        someWords = mWordDao.getWords(category);
        return someWords;
    }


    public void update(Word word)
    {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mWordDao.update(word);
        });
    }
    public void updateCategory(Category category)
    {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mCategoryDao.update(category);
        });
    }
    public int countCategory(String category)
    {
        int count;
        count = mCategoryDao.countCategory(category);
        return count;
    }

    public int countWordsByCat(String category)
    {
        int count;
        count = mWordDao.countCategory(category);
        return count;
    }
    public void updateByCategory(String oldCategory, String newCategory)
    {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mWordDao.updateByCategory(oldCategory,newCategory);
        });
    }
    public void deleteWord(Word word)
    {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mWordDao.deleteWord(word);
        });
    }
    public void deleteCategory(Category category)
    {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mCategoryDao.delete(category);
        });
    }
    public void deleteAllWords(String category)
    {
        mWordDao.deleteAll(category);
    }
    public void deleteAllCategories()
    {
        mCategoryDao.deleteAll();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Word word) {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
  /*  void listWords(String word) {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mWordDao.getWords(word);
        });
    }*/
  public void insertCategory(Category category) {
        WordRoomDB.dbWriteExecutor.execute(() -> {
            mCategoryDao.insert(category);
        });
    }
}