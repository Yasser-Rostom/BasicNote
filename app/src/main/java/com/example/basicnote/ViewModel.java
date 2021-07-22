package com.example.basicnote;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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


    public void deleteAllWords() {mRepository.deleteAllWords();
    }
    LiveData<List<Word>> getAllWords() { return mAllWords; }



}
