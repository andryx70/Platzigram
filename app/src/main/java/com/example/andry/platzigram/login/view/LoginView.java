package com.example.andry.platzigram.login.view;

import android.view.View;

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();
    void loginError(String error);
    void goCreateAccount(View view);
    void goHome();
    void goPlatigramWeb(View view);
}
