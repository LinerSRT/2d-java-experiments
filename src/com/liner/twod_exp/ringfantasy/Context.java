package com.liner.twod_exp.ringfantasy;

public interface Context {
    Player getPlayer();
    Map getMap();
    Camera getCamera();
    Hud getHud();
    Event getEvent();
    Message getMessage();
    void setMessage(Message message);
    Question getQuestion();
    void setQuestion(Question question);
}
