package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends Activity implements AsyncResponse {

    String usernameStr;
    TextView welcomeMessage;
    String ageStr = null, favPokeStr = null; // get user info...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        welcomeMessage = (TextView) findViewById(R.id.tvWelcome);
        usernameStr = getIntent().getStringExtra("userId");
        welcomeMessage.setText("Welcome " + usernameStr + " !!");
    }

    public void goProfile(View view){
        //for user info
        String type = "getUserInfo";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        backgroundTask.execute(type, usernameStr);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                    Intent intent = new Intent(GameActivity.this, ProfileActivity.class);
                    intent.putExtra("username", usernameStr);
                    intent.putExtra("age", ageStr);
                    intent.putExtra("favPoke", favPokeStr);
                    startActivity(intent);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void goMapPage(View view){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("userId", usernameStr);
        startActivity(intent);
    }

    public void goOnline(View view){

    }

    public void goPokedex(View view){
        Intent intent = new Intent(this, PokedexActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(String processNumber, String jsonString) {
        if(processNumber.equals("getUserInfo")){
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    ageStr = JO.getString("age");
                    favPokeStr = JO.getString("fav_poke");
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
