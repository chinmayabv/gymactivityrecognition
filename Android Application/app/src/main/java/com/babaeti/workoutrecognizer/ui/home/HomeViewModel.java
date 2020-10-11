package com.babaeti.workoutrecognizer.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.babaeti.workoutrecognizer.Constants;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(Constants.SERVER_IP);
    }

    public LiveData<String> getText() {
        return mText;
    }
}