package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

    EditText username, password, age, favPoke;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        age = (EditText) findViewById(R.id.register_age);
        favPoke = (EditText) findViewById(R.id.register_favPoke);
    }

    public void doneRegister(View view){
        String username_str, password_str, age_str, favPoke_str;

        username_str = username.getText().toString();
        password_str = password.getText().toString();
        age_str = age.getText().toString();
        favPoke_str = favPoke.getText().toString();

        String type = "register";

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(type, username_str, password_str, age_str, favPoke_str);
        finish();
    }
}
