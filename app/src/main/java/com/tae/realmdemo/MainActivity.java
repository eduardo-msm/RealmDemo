package com.tae.realmdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

import javax.security.auth.login.LoginException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the Realm configuration
        //This should be done in Application class
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
        // Init transaction to add store realm object
        realm.beginTransaction();
        fakeParentGenerator(realm);

        RealmResults<AnswerFlow> answerFlows = realm.allObjects(AnswerFlow.class);
        Log.i(TAG, "onCreate: answerFlows from Realm: " + answerFlows.size());
        int counter = 0;
        int childCounter = 0;
        for (AnswerFlow answerFlow : answerFlows) {
            Log.i(TAG, "onCreate: **********************************Reading answerFlow Parent **********************************");
            Log.i(TAG, "onCreate: **********************************count: "+ counter++ +" **********************************");
            Log.i(TAG, "onCreate: uuid " + answerFlow.getUuid());
            Log.i(TAG, "onCreate: answerId " + answerFlow.getAnswerId());
            if (answerFlow.getChoice() != null) {
                Log.i(TAG, "onCreate: choice id " + answerFlow.getChoice().getId());
                Log.i(TAG, "onCreate: choice value " + answerFlow.getChoice().getValue());
            }
            for (AnswerFlow child : answerFlow.getChildAnswers()) {
                Log.i(TAG, "onCreate: **********************************Reading CHILD answerFlow Parent **********************************");
                Log.i(TAG, "onCreate: **********************************count: "+ childCounter++ +" **********************************");
                Log.i(TAG, "onCreate: child uuid " + child.getUuid());
                Log.i(TAG, "onCreate: child answerId " + child.getAnswerId());
                if (child.getChoice() != null) { // TODO check -> always null
                    Log.i(TAG, "onCreate: child choice id " + child.getChoice().getId());
                    Log.i(TAG, "onCreate: child choice value " + child.getChoice().getValue());
                }
            }

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * Simulation to store AnswerFlow objects with children
     * @param realm
     */
    private void fakeParentGenerator(Realm realm) {
        for (int i = 0; i < 10; i++) {
            AnswerFlow answerFlow = realm.createObject(AnswerFlow.class);
            answerFlow.setUuid(UUID.randomUUID().toString() + " " + i);
            answerFlow.setAnswerId("answer " + i);
            Choice choice = getChoice(realm, i);
            answerFlow.setChoice(choice);
            answerFlow.getChildAnswers().add(fakeChildGenerator(realm));
            RealmList<AnswerFlow> childAnswers = new RealmList<>();
            childAnswers.add(fakeChildGenerator(realm));
            answerFlow.setChildAnswers(childAnswers);
        }
        realm.commitTransaction();
    }

    @NonNull
    private Choice getChoice(Realm realm, int i) {
        Choice choice = realm.createObject(Choice.class);
        choice.setId("choice id: " + i);
        choice.setValue("choice value nro " + i );
        return choice;
    }

    private AnswerFlow fakeChildGenerator(Realm realm) {
        AnswerFlow answerFlow = null;
        for (int i = 0; i < 10; i++) {
            answerFlow = realm.createObject(AnswerFlow.class);
            if (i % 2 == 0) {
                answerFlow.setUuid("Child UUID: " + UUID.randomUUID().toString() + " " + i);
                answerFlow.setAnswerId("child answer " + i);
                answerFlow.setChoice(getChoice(realm, i));
            }
        }
        return answerFlow;
    }

}
