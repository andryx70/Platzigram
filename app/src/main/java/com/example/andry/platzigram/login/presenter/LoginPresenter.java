package com.example.andry.platzigram.login.presenter;

public interface LoginPresenter {
    void signIn(String username, String password); //Interactor
    void loginSuccess();
    void loginError(String error);
}