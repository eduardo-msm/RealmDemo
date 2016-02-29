package com.tae.realmdemo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eduardo on 28/02/2016.
 */
public class AnswerFlow extends RealmObject {


    private String uuid;
    private String answerId;
    private Choice choice;
    private RealmList<AnswerFlow> childAnswers;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public RealmList<AnswerFlow> getChildAnswers() {
        return childAnswers;
    }

    public void setChildAnswers(RealmList<AnswerFlow> childAnswers) {
        this.childAnswers = childAnswers;
    }


}
