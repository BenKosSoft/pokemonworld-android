package edu.sabanciuniv.cs310.pokemonworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements AsyncResponse {

    EditText username, password;
    String username_str, password_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);

    }

    public void onLogin(View view){
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        username_str = username.getText().toString();
        password_str = password.getText().toString();
        String type = "login";
        backgroundTask.execute(type, username_str, password_str);
    }

    public void onRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void processFinish(String processNumber, String output) {
        if(output.contains("not")){
            Toast.makeText(this, "Login Not Success", Toast.LENGTH_SHORT).show();}
        else{
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("userId", username_str);
            startActivity(i);
        }
    }
}
