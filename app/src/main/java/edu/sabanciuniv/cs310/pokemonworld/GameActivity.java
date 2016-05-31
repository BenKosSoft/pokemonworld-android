package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends Activity implements AsyncResponse {

    String usernameStr;
    TextView welcomeMessage;
    String ageStr = null, favPokeStr = null; // get user info...
    String jsonStringPokemon; // get own pokemon info...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        welcomeMessage = (TextView) findViewById(R.id.tvWelcome);
        usernameStr = getIntent().getStringExtra("userId");
        String welcomeMessageStr = new StringBuilder().append("Welcome ").append(usernameStr).append(" !" ).toString().trim();
        welcomeMessage.setText(welcomeMessageStr);
    }

    public void goProfile(View view){

        //for pokemoninfo and userinfo
        String type2 ="ownPokemon";
        BackgroundTask backgroundTask1 = new BackgroundTask(this);
        backgroundTask1.delegate = this;
        backgroundTask1.execute(type2, usernameStr);

    }

    public void goMapPage(View view){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("userId", usernameStr);
        startActivity(intent);
    }

    public void goOnline(View view){
        Toast.makeText(this, "COMING SOON!", Toast.LENGTH_SHORT).show();
    }

    public void goPokedex(View view){
        Intent intent = new Intent(this, PokedexActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(String processNumber, String jsonString) {
        jsonStringPokemon = jsonString;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;

            JSONObject JO = jsonArray.getJSONObject(count);
            ageStr = JO.getString("age");
            favPokeStr = JO.getString("fav_poke");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(GameActivity.this, ProfileActivity.class);
        intent.putExtra("username", usernameStr);
        intent.putExtra("age", ageStr);
        intent.putExtra("favPoke", favPokeStr);
        intent.putExtra("jsonPokemon", jsonStringPokemon);
        startActivity(intent);
    }

}
