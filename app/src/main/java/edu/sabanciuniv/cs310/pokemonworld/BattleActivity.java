package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by mbenlioglu on 21/04/2016.
 */
public class BattleActivity extends Activity implements AsyncResponse{

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

    String welcomeMessageStr;
    String townName, userName, enemyName;
    String text;
    TextView welcomeMessage, fight_msg, battle_pname, battle_pHP, battle_plvl,
            battle_e_pname, battle_e_pHP, battle_e_plvl;
    ImageView user_card, enemy_card;
    BackgroundTask backgroundTask;
    Pokemon[] pokemons = new Pokemon[2];
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        townName = getIntent().getStringExtra("townName");
        welcomeMessage = (TextView) findViewById(R.id.tvBattleScreenWelcomeMessage);
        fight_msg = (TextView) findViewById(R.id.fight_msg);
        battle_pname = (TextView) findViewById(R.id.battle_pname);
        battle_pHP = (TextView) findViewById(R.id.battle_pHP);
        battle_plvl = (TextView) findViewById(R.id.battle_plvl);
        battle_e_pname = (TextView) findViewById(R.id.battle_e_pname);
        battle_e_pHP = (TextView) findViewById(R.id.battle_e_pHP);
        battle_e_plvl = (TextView) findViewById(R.id.battle_e_plvl);
        user_card = (ImageView) findViewById(R.id.user_card);
        enemy_card = (ImageView) findViewById(R.id.enemy_card);

        welcomeMessageStr = "Welcome to " + townName + "!";
        welcomeMessage.setText(welcomeMessageStr);

        fight_msg.setText("Press RUN to search pokemon");
        battle_e_plvl.setText("");
        battle_e_pname.setText("");
        battle_e_pHP.setText("");
        enemy_card.setImageResource(R.drawable.card_backface);

        userName = getIntent().getStringExtra("userId");
        backgroundTask = new BackgroundTask(BattleActivity.this);
        backgroundTask.delegate = this;
        backgroundTask.execute("ownPokePocket", userName);
    }

    @Override
    public void processFinish(String processNumber, String output) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(output);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String pid, pXp, pName, exists, move, moveType, hp;
            while (count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                pid = object.getString("pid");
                pXp = object.getString("exp");
                pName = object.getString("pokemonName");
                exists = object.getString("exist");
                move = object.getString("move");
                moveType = object.getString("move_type");
                hp = object.getString("hp");

                pokemons[count] = new Pokemon(Integer.parseInt(pid), pName, Integer.parseInt(pXp), exists, move, moveType);
                pokemons[count].setHp(Integer.parseInt(hp));
                count++;
            }

            battle_pname.setText(pokemons[0].getName());
            battle_plvl.setText(pokemons[0].getLevel().toString());
            battle_pHP.setText(pokemons[0].getHp().toString());

            StringBuilder cardName = new StringBuilder();
            cardName.append("card_").append(battle_pname.getText().toString().toLowerCase().trim());
            user_card.setImageResource(getResources().getIdentifier(cardName.toString(),"drawable", getPackageName()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onRun (View v){

    }

    public void onPokemon (View v){
        switch (current){
            case 0:
                battle_pname.setText(pokemons[1].getName());
                battle_plvl.setText(pokemons[1].getLevel());
                battle_pHP.setText(pokemons[1].getHp());

                StringBuilder cardName = new StringBuilder();
                cardName.append("card_").append(battle_pname.getText().toString().toLowerCase().trim());
                user_card.setImageResource(getResources().getIdentifier(cardName.toString(),"drawable", getPackageName()));
                break;
            case 1:
                battle_pname.setText(pokemons[0].getName());
                battle_plvl.setText(pokemons[0].getLevel());
                battle_pHP.setText(pokemons[0].getHp());

                cardName = new StringBuilder();
                cardName.append("card_").append(battle_pname.getText().toString().toLowerCase().trim());
                user_card.setImageResource(getResources().getIdentifier(cardName.toString(),"drawable", getPackageName()));
                break;
            default:
                break;
        }
    }

    public void onCatch (View v){

    }

    public void onFight (View v){
        //coming soon
    }

    protected int[] randomEnemy (int minLvl, int maxLvl){
        int pokemon, lvl;
        lvl = new Random().nextInt((maxLvl - minLvl) + 1) + minLvl;
        pokemon = new Random().nextInt(150);
        return new int[] {pokemon, lvl};
    }
}
