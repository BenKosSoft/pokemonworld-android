package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity implements AsyncResponse {

    String usernameStr;

    RadioGroup radioGroup;
    TextView username, age, favPokemon;
    ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();

    ListView listViewOwnPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        usernameStr = intent.getStringExtra("username");
        String ageStr = intent.getStringExtra("age");
        String favPokeStr = intent.getStringExtra("favPoke");
        String json_string = intent.getStringExtra("jsonPokemon");

        username = (TextView) findViewById(R.id.tvUsernameInProfile);
        age = (TextView) findViewById(R.id.tvAgeInProfile);
        favPokemon = (TextView) findViewById(R.id.tvFavPokemonInProfile);

        username.setText(usernameStr);
        age.setText(ageStr);
        favPokemon.setText(favPokeStr);

        prepareOwnPokemon(json_string);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        setRadioGroup();

    }

    private void prepareOwnPokemon(String json_string){
        PokemonAdapter pokemonAdapter = new PokemonAdapter(this, R.layout.pokemon_listview);
        listViewOwnPokemon = (ListView) findViewById(R.id.listViewOwnPokemon);
        listViewOwnPokemon.setAdapter(pokemonAdapter);

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name;
            Integer exp;
            String exist;
            while (count < jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("pokemonName");
                exp = Integer.parseInt(JO.getString("exp"));
                exist = JO.getString("exist");
                Pokemon pokemon = new Pokemon(name, exp, exist);
                if(exist.equals("1")) {
                    pokemonList.add(pokemon);
                }
                pokemonAdapter.add(pokemon);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updatePokemon(View view){

    }

    public void updatePokemonType(View view){

    }

    public void setRadioGroup(){
        for (int row = 0; row < 1; row++) {
            RadioGroup radioGroup1 = new RadioGroup(this);

            for (int i = 1; i <= pokemonList.size(); i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                String name = pokemonList.get(i-1).getName();
                Integer level = pokemonList.get(i-1).getLevel();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(name);
                stringBuilder.append(" - ");
                stringBuilder.append(level.toString());
                rdbtn.setText(stringBuilder.toString().trim());
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
