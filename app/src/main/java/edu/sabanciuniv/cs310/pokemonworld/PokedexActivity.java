package edu.sabanciuniv.cs310.pokemonworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokedexActivity extends AppCompatActivity implements AsyncResponse {

    ListView listView;
    private String [] pokedex = {"Bulbasaur","Ivysaur","Venusaur","Charmander","Charmeleon","Charizard","Squirtle",
            "Wartortle","Blastoise","Caterpie","Metapod","Butterfree","Weedle","Kakuna","Beedrill","Pidgey",
            "Pidgeotto","Pidgeot","Rattata","Raticate","Spearow","Fearow","Ekans","Arbok","Pikachu","Raichu",
            "Sandslash","Nidoran_m","Nidorina","Nidoqueen","Nidoran_f","Nidorino","Nidoking","Clefairy","Clefable",
            "Vulpix","Ninetales","Jigglypuff","Wigglytuff","Zubat","Golbat","Oddish","Gloom","Vileplume",
            "Paras","Parasect","Venonat","Venomoth","Diglett","Dugtrio","Meowth","Persian","Psyduck","Golduck",
            "Mankey","Primeape","Growlithe","Arcanine","Poliwag","Poliwhirl","Poliwrath","Abra","Kadabra",
            "Alakazam","Machop","Machoke","Machamp","Bellsprout","Weepinbell","Victreebel","Tentacool","Tentacruel",
            "Geodude","Graveler","Golem","Ponyta","Rapidash","Slowpoke","Slowbro","Magnemite","Magneton","Farfetchd",
            "Doduo","Dodrio","Seel","Dewgong","Grimer","Muk","Shellder","Cloyster","Gastly","Haunter","Gengar","Onix",
            "Drowzee","Hypno","Krabby","Kingler","Voltorb","Electrode","Exeggcute","Exeggutor","Cubone","Marowak","Hitmonlee",
            "Hitmonchan","Lickitung","Koffing","Weezing","Rhyhorn","Rhydon","Chansey","Tangela","Kangaskhan","Horsea","Seadra",
            "Goldeen","Seaking","Staryu","Starmie","Mr_Mime","Scyther","Jynx","Electabuzz","Magmar","Pinsir","Tauros","Magikarp",
            "Gyarados","Lapras","Ditto","Eevee","Vaporeon","Jolteon","Flareon","Porygon","Omanyte","Omastar","Kabuto","Kabutops",
            "Aerodactyl","Snorlax","Articuno","Zapdos","Moltres","Dratini","Dragonair","Dragonite","Mewtwo","Mew"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        listView = (ListView) findViewById(R.id.pokedexList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pokedex);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pokemonName = (String) listView.getItemAtPosition(position);
                String type = "pokedex_json";

                BackgroundTask backgroundTask = new BackgroundTask(PokedexActivity.this);
                backgroundTask.delegate = (AsyncResponse) PokedexActivity.this;
                backgroundTask.execute(type, pokemonName);
            }
        });
    }

    @Override
    public void processFinish(String processNumber, String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name = null, hp = null, attack = null, defence = null,
                    special_attack = null,
                    special_defense = null, speed = null, legendary = null, type1 = null, type2 = null;
            while (count < jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                hp = JO.getString("hp");
                attack = JO.getString("attack");
                defence = JO.getString("defence");
                special_attack = JO.getString("special_attack");
                special_defense = JO.getString("special_defense");
                speed = JO.getString("speed");
                legendary = JO.getString("legendary");
                type1 = JO.getString("type1");
                type2 = JO.getString("type2");
                count++;
            }
            Intent intent = new Intent(this, PokemonActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("hp", hp);
            intent.putExtra("defence", defence);
            intent.putExtra("attack", attack);
            intent.putExtra("special_attack", special_attack);
            intent.putExtra("special_defense", special_defense);
            intent.putExtra("speed", speed);
            intent.putExtra("legendary", legendary);
            intent.putExtra("type1", type1);
            if(type2 != null){
                intent.putExtra("type2", type2);
            }
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
