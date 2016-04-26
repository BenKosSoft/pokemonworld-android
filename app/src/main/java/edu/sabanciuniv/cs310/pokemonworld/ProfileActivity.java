package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends Activity implements AsyncResponse {

    String usernameStr;

    RadioGroup radioGroup;
    TextView username, age, favPokemon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        usernameStr = intent.getStringExtra("username");
        String ageStr = intent.getStringExtra("age");
        String favPokeStr = intent.getStringExtra("favPoke");

        username = (TextView) findViewById(R.id.tvUsernameInProfile);
        age = (TextView) findViewById(R.id.tvAgeInProfile);
        favPokemon = (TextView) findViewById(R.id.tvFavPokemonInProfile);

        username.setText(usernameStr);
        age.setText(ageStr);
        favPokemon.setText(favPokeStr);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        setRadioGroup(3);

    }

    public void updatePokemon(View view){

    }

    public void updatePokemonType(View view){

    }

    public void setRadioGroup(int number){
        for (int row = 0; row < 1; row++) {
            RadioGroup radioGroup1 = new RadioGroup(this);

            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("Radio " + rdbtn.getId());
                radioGroup1.addView(rdbtn);
            }

            radioGroup.addView(radioGroup1);
        }
    }

    @Override
    public void processFinish(String processNumber, String jsonString) {
        if(processNumber.equals("getUserInfo")){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String ageStr = null, favPokeStr = null;
                while (count < jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    ageStr = JO.getString("age");
                    favPokeStr = JO.getString("fav_poke");
                    count++;
                }
                username.setText(usernameStr);
                age.setText(ageStr);
                favPokemon.setText(favPokeStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
