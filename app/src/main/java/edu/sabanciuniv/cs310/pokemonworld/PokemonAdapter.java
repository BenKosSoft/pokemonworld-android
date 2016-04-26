package edu.sabanciuniv.cs310.pokemonworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mert on 26.04.2016.
 */
public class PokemonAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public PokemonAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Pokemon pokemon) {
        list.add(pokemon);
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        PokemonHolder pokemonHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.pokemon_listview,parent,false);
            pokemonHolder = new PokemonHolder();
            pokemonHolder.tx_name = (TextView) row.findViewById(R.id.ownPokemonNameTextView);
            pokemonHolder.tx_level = (TextView) row.findViewById(R.id.ownPokemonLevelTextView);
            row.setTag(pokemonHolder);
        }else{
            pokemonHolder = (PokemonHolder)row.getTag();
        }

        Pokemon pokemon = (Pokemon)this.getItem(position);
        pokemonHolder.tx_name.setText(pokemon.getName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lvl-");
        stringBuilder.append(pokemon.getLevel().toString());
        pokemonHolder.tx_level.setText(stringBuilder.toString().trim());
        return row;
    }

    static class PokemonHolder{
        TextView tx_name, tx_level;
    }

}
