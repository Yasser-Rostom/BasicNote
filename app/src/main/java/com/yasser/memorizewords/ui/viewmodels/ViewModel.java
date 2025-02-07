package com.yasser.memorizewords.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yasser.memorizewords.data.model.Word;
import com.yasser.memorizewords.data.repository.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository mRepository;

 private final LiveData<List<Word>> mAllWords;
    public ViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
       mAllWords = mRepository.getAllWords();

    }

    public  void deleteWord (Word word) {mRepository.deleteWord(word);}


    public void deleteAllWords(String category) {mRepository.deleteAllWords(category);
    }
   LiveData<List<Word>> getAllWords() { return mAllWords; }

    public LiveData<List<Word>> getSpecificWords(String category) {
       final LiveData<List<Word>> someWords = mRepository.getWordsByCategory(category);
        return someWords;

    }
    public int countWordByCat(String category)
    {
        int count = mRepository.countWordsByCat(category);
        return count;
    }




}
