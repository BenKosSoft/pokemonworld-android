package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PokemonActivity extends Activity {

    TextView attackTv, defenceTv, hpTv, speedTv, legendaryTv, specialAttackTv, specialDefenseTv,
    type1Tv, type2Tv, titleTv;

    ImageView pokeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        attackTv = (TextView) findViewById(R.id.tv_attack);
        defenceTv = (TextView) findViewById(R.id.tv_defense);
        hpTv = (TextView) findViewById(R.id.tv_hp);
        speedTv = (TextView) findViewById(R.id.tv_speed);
        legendaryTv = (TextView) findViewById(R.id.titleLegendary);
        specialAttackTv = (TextView) findViewById(R.id.tv_s_attack);
        specialDefenseTv = (TextView) findViewById(R.id.tv_s_defense);
        type1Tv = (TextView) findViewById(R.id.tv_type1);
        type2Tv = (TextView) findViewById(R.id.tv_type2);
        titleTv = (TextView) findViewById(R.id.tvPokemonName);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String hp = intent.getStringExtra("hp");
        String defence = intent.getStringExtra("defence");
        String attack = intent.getStringExtra("attack");
        String specialAttack = intent.getStringExtra("special_attack");
        String specialDefense = intent.getStringExtra("special_defense");
        String speed = intent.getStringExtra("speed");
        String legendary = intent.getStringExtra("legendary");
        String type1 = intent.getStringExtra("type1");
        String type2 = intent.getStringExtra("type2");

        StringBuilder uri = new StringBuilder();
        uri.append("@drawable").append(name.toLowerCase());

        int imageResource = getResources().getIdentifier(uri.toString().trim(),null,getPackageName());

        pokeCard = (ImageView) findViewById(R.id.pokeCard);
        Drawable res = getResources().getDrawable(imageResource);
        pokeCard.setImageDrawable(res);

        if(legendary.equals("0")) {
            legendaryTv.setVisibility(TextView.GONE);
            legendaryTv.setVisibility(TextView.INVISIBLE);
        }
        attackTv.setText(attack);
        defenceTv.setText(defence);
        titleTv.setText(name);
        hpTv.setText(hp);
        specialAttackTv.setText(specialAttack);
        specialDefenseTv.setText(specialDefense);
        speedTv.setText(speed);
        type1Tv.setText(type1);
        if(type2.equals("null")) {
            type2Tv.setVisibility(TextView.GONE);
            type2Tv.setVisibility(TextView.INVISIBLE);
        }else {
            type2Tv.setText(type2);
        }
    }
}
