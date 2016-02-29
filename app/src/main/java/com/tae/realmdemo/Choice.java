package com.tae.realmdemo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eduardo on 28/02/2016.
 */
public class Choice extends RealmObject {


    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
