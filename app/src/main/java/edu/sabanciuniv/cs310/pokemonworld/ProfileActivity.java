package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends Activity {

    String usernameStr;
    String ageStr;
    String favPokeStr;

    RadioGroup radioGroup;
    TextView username, age, favPokemon;
    ArrayList<Pokemon> ownPokemonList = new ArrayList<Pokemon>();
    ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
    String json_string;
    RadioButton child[] = new RadioButton[3];


    ListView listViewOwnPokemon;
    ListView listViewOwnPokemon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        usernameStr = intent.getStringExtra("username");
        String ageStr = intent.getStringExtra("age");
        String favPokeStr = intent.getStringExtra("favPoke");
        json_string = intent.getStringExtra("jsonPokemon");

        username = (TextView) findViewById(R.id.tvUsernameInProfile);
        age = (TextView) findViewById(R.id.tvAgeInProfile);
        favPokemon = (TextView) findViewById(R.id.tvFavPokemonInProfile);

        username.setText(usernameStr);
        age.setText(ageStr);
        favPokemon.setText(favPokeStr);

        prepareOwnPokemon(1, json_string);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        setRadioGroup();

        listViewOwnPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view.findViewById(R.id.ownPokemonID);
                String TEXT = deleteLastElement(text.getText().toString());
                Pokemon pokemon = returnPokemon(Integer.parseInt(TEXT));
                String move = pokemon.getMove();
                String moveType = pokemon.getMoveType();
                Integer exp = pokemon.getExp();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MOVE = ");
                stringBuilder.append(move).append("\n");
                stringBuilder.append("MOVE TYPE = ");
                stringBuilder.append(moveType).append("\n");
                stringBuilder.append("EXP = ");
                stringBuilder.append(exp.toString());
                Toast.makeText(ProfileActivity.this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void prepareOwnPokemon(int type, String json_string){
        PokemonAdapter pokemonAdapter1 = new PokemonAdapter(this, R.layout.pokemon_listview);
        PokemonAdapter pokemonAdapter2 = new PokemonAdapter(this, R.layout.pokemon_listview);
        if(type == 1){
            listViewOwnPokemon = (ListView) findViewById(R.id.listViewOwnPokemon);
            listViewOwnPokemon.setAdapter(pokemonAdapter1);
        }else if(type == 2){
            listViewOwnPokemon2 = (ListView) findViewById(R.id.listViewOwnPokemon2);
            listViewOwnPokemon2.setAdapter(pokemonAdapter2);
        }

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name, exist, move, moveType;
            Integer exp, pid;
            while (count < jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("pokemonName");
                exp = Integer.parseInt(JO.getString("exp"));
                exist = JO.getString("exist");
                pid = Integer.parseInt(JO.getString("pid"));
                move = JO.getString("move");
                moveType = JO.getString("move_type");
                Pokemon pokemon = new Pokemon(pid, name, exp, exist, move, moveType);
                if(exist.equals("1")) {
                    ownPokemonList.add(pokemon);
                }else{
                    pokemonAdapter2.add(pokemon);
                }
                pokemonAdapter1.add(pokemon);
                pokemonList.add(pokemon);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updatePokemon(View view){

        prepareOwnPokemon(2, json_string);
        String message = "Select pokemon to be changed from your pokemon!";
        Toast.makeText(ProfileActivity.this, message,Toast.LENGTH_SHORT).show();

        listViewOwnPokemon2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view.findViewById(R.id.ownPokemonID);
                String pidStr = deleteLastElement(text.getText().toString());
                Integer pid = null;

                if(child[1].isChecked() || child[2].isChecked()){
                    if(child[1].isChecked()) pid = ownPokemonList.get(0).getPid();
                    else if(child[2].isChecked()) pid = ownPokemonList.get(1).getPid();

                    String type = "changeFirstPokemon";
                    BackgroundTask backgroundTask = new BackgroundTask(ProfileActivity.this);
                    backgroundTask.execute(type, pidStr, pid.toString());

                    finish();
                }else{
                    Toast.makeText(ProfileActivity.this, "Please select one of radio button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updatePokemonType(View view){

    }

    public void setRadioGroup(){
        for (int row = 0; row < 1; row++) {
            RadioGroup radioGroup1 = new RadioGroup(this);

            for (int i = 1; i <= ownPokemonList.size(); i++) {
                child[i] = new RadioButton(this);
                child[i].setId((row * 2) + i);
                String name = ownPokemonList.get(i-1).getName();
                Integer level = ownPokemonList.get(i-1).getLevel();
                Integer pid = ownPokemonList.get(i-1).getPid();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(pid.toString());
                stringBuilder.append(" - ");
                stringBuilder.append(name);
                stringBuilder.append(" - ");
                stringBuilder.append("level ");
                stringBuilder.append(level.toString());
                child[i].setText(stringBuilder.toString().trim());
                radioGroup1.addView(child[i]);
            }

            radioGroup.addView(radioGroup1);
        }
    }

//    @Override
//    public void processFinish(String processNumber, String jsonString) {
//        if(processNumber.equals("getUserInfo")){
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(jsonString);
//                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
//                int count = 0;
//                String ageStr = null, favPokeStr = null;
//                while (count < jsonArray.length()){
//                    JSONObject JO = jsonArray.getJSONObject(count);
//                    ageStr = JO.getString("age");
//                    favPokeStr = JO.getString("fav_poke");
//                    count++;
//                }
//                username.setText(usernameStr);
//                age.setText(ageStr);
//                favPokemon.setText(favPokeStr);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    private String deleteLastElement(String s){
        return s.substring(0, s.length()-1);
    }

    private Pokemon returnPokemon(Integer pid){
        for(Pokemon p : pokemonList){
            if(p.getPid().equals(pid)){
                return p;
            }
        }
        return null;
    }

}
