package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends Activity {

    String username_str, townName_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        username_str = getIntent().getStringExtra("userId");
    }

    public void startBattle(View view){
        townName_str = (String) view.getTag();

        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("userId", username_str);
        intent.putExtra("townName", townName_str);
        startActivity(intent);
    }
}
