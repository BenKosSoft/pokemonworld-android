package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    EditText username, password, age, favPoke;
    Button btn_register;

    Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        age = (EditText) findViewById(R.id.register_age);
        favPoke = (EditText) findViewById(R.id.register_favPoke);

        spinner1 = (Spinner) findViewById(R.id.spinnerFirstPokemon);
        spinner2 = (Spinner) findViewById(R.id.spinnerSecondPokemon);

    }

    public void doneRegister(View view){
        String username_str, password_str, age_str, favPoke_str;
        String pokemonFirst = spinner1.getSelectedItem().toString();
        String pokemonSecond = spinner2.getSelectedItem().toString();

        Integer idFirst = returnId(pokemonFirst);
        Integer idSecond = returnId(pokemonSecond);

        if(idFirst == -1 || idSecond == -1){
            Toast.makeText(this, "ERROR!!", Toast.LENGTH_LONG).show();
        }else{

            username_str = username.getText().toString();
            password_str = password.getText().toString();
            age_str = age.getText().toString();
            favPoke_str = favPoke.getText().toString();

            if(username_str.equals("") || password_str.equals("") || age_str.equals("") || favPoke_str.equals("")){

                new AlertDialog.Builder(this)
                        .setTitle("You cannot register with missing information!")
                        .setMessage("Please fill all information...")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }else{

                String type = "register";

                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(type, username_str, password_str, age_str, favPoke_str, pokemonFirst, pokemonSecond);
                finish();
            }



        }
    }

    private Integer returnId(String pokemonName){

        if(pokemonName.equals("Charmander")){
            return 4;
        }else if(pokemonName.equals("Squirtle")){
            return 7;
        }else if(pokemonName.equals("Bulbasaur")){
            return 1;
        }else if(pokemonName.equals("Pikachu")){
            return 25;
        }else if(pokemonName.equals("Pidgey")){
            return 16;
        }else if(pokemonName.equals("Caterpie")){
            return 10;
        }else{
            return -1;
        }
    }
}
