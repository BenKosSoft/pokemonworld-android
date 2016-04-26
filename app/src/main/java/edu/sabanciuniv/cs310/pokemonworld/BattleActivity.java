package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by mbenlioglu on 21/04/2016.
 */
public class BattleActivity extends Activity {

    String welcomeMessageStr;
    String townName;
    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        townName = getIntent().getStringExtra("townName");
        welcomeMessage = (TextView) findViewById(R.id.tvBattleScreenWelcomeMessage);
        welcomeMessageStr = "Welcome to " + townName + "!";
        welcomeMessage.setText(welcomeMessageStr);


    }
}
