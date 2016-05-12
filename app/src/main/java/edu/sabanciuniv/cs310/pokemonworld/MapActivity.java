package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends Activity implements AsyncResponse {

    String username_str, townName_str;
    String levelfirst, levelsecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        username_str = getIntent().getStringExtra("userId");
    }

    public void startBattle(View view){
        townName_str = (String) view.getTag();

        String type = "findRangeRegion";

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        backgroundTask.execute(type, townName_str);



    }

    @Override
    public void processFinish(String processNumber, String output) {
        int index = output.indexOf("-");
        levelfirst = output.substring(0, index);
        levelsecond = output.substring(index+1);

        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("userId", username_str);
        intent.putExtra("townName", townName_str);
        intent.putExtra("levelFirst", levelfirst);
        intent.putExtra("levelSecond", levelsecond);
        startActivity(intent);
    }
}
