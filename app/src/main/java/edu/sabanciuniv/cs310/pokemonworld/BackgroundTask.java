package edu.sabanciuniv.cs310.pokemonworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Mert on 14.04.2016.
 */
public class BackgroundTask extends AsyncTask<String, Void, String> {

    Context context;
    public AsyncResponse delegate = null;
    String type_main = "";
    ProgressDialog progressDialog;


    BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Gathering Information...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        type_main = type;
        if (type.equals("login")) {
            return login(params);
        } else if (type.equals("register")) {
            return register(params);
        } else if (type.equals("pokedex_json")) {
            return getJsonString(params);
        } else if (type.equals("getUserInfo")) {
            return getUserInfo(params);
        } else if (type.equals("ownPokemon")) {
            return ownPokemon(params);
        } else if(type.equals("changeFirstPokemon")){
            return changeFirstPokemon(params);
        } else if(type.equals("changePokemonType")) {
            return changePokemonType(params);
        } else if(type.equals("findRangeRegion")) {
            return findRangeRegion(params);
        } else if(type.equals("ownPokePocket")) {
            return ownPokePocket(params);
        } else if(type.equals("catchPokemon")){
            return catchPokemon(params);
        } else {
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (type_main.equals("login")) {
            delegate.processFinish("login", result);
        } else if (type_main.equals("register")) {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        } else if (type_main.equals("pokedex_json")) {
            delegate.processFinish("pokedex_json", result);
        } else if (type_main.equals("getUserInfo")) {
            delegate.processFinish("getUserInfo", result);
        } else if(type_main.equals("ownPokemon")){
            delegate.processFinish("ownPokemon", result);
        } else if(type_main.equals("changeFirstPokemon")) {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        } else if(type_main.equals("changePokemonType")){
            delegate.processFinish("changePokemonType",result);
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        } else if(type_main.equals("findRangeRegion")){
            delegate.processFinish("findRangeRegion", result);
        } else if(type_main.equals("ownPokePocket")){
            delegate.processFinish("ownPokePocket", result);
        } else if(type_main.equals("catchPokemon")){
            delegate.processFinish("catchPokemon", result);
        }
        progressDialog.dismiss();
    }

    private String login(String... params) {
        String login_url = "http://mertkoo.com/PokemonWorld/login.php";
        try {
            String username = params[1];
            String password = params[2];
            URL url = new URL(login_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String register(String... params) {
        String register_url = "http://mertkoo.com/PokemonWorld/register.php";
        try {
            String username = params[1];
            String password = params[2];
            String age = params[3];
            String favPoke = params[4];
            String pokemonFirst = params[5];
            String pokemonSecond = params[6];

            URL url = new URL(register_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                    URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&" +
                    URLEncoder.encode("fav_poke", "UTF-8") + "=" + URLEncoder.encode(favPoke, "UTF-8") + "&" +
                    URLEncoder.encode("pokemonFirst", "UTF-8") + "=" + URLEncoder.encode(pokemonFirst, "UTF-8") + "&" +
                    URLEncoder.encode("pokemonSecond", "UTF-8") + "=" + URLEncoder.encode(pokemonSecond, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJsonString(String... params) {
        String json_url = "http://mertkoo.com/PokemonWorld/json_pokemon_data.php";
        try {
            String pokemon_name = params[1];
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("pokemon_name", "UTF-8") + "=" + URLEncoder.encode(pokemon_name, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String JSON_STRING;
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "/n");
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getUserInfo(String... params) {
        String json_url = "http://mertkoo.com/PokemonWorld/json_get_user_info.php";
        try {
            String username = params[1];
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String JSON_STRING;
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "/n");
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String ownPokemon(String... params) {

        String json_url = "http://mertkoo.com/PokemonWorld/json_own_pokemon.php";

        try {
            String username = params[1];
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String JSON_STRING;
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "/n");
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String changeFirstPokemon(String ... params) {

        String created = params[1];
        String deleted = params[2];

        String json_url = "http://mertkoo.com/PokemonWorld/change_pokemon.php";

        try {
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("created", "UTF-8") + "=" + URLEncoder.encode(created, "UTF-8") + "&" +
                    URLEncoder.encode("deleted", "UTF-8") + "=" + URLEncoder.encode(deleted, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String changePokemonType(String ... params){
        String pokemonId = params[1];
        String pokemonType = params[2];
        String pokemonTypeAtt = params[3];

        String json_url = "http://mertkoo.com/PokemonWorld/change_pokemon_type.php";

        try {
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("pokemonId", "UTF-8") + "=" + URLEncoder.encode(pokemonId, "UTF-8") + "&" +
                    URLEncoder.encode("pokemonType", "UTF-8") + "=" + URLEncoder.encode(pokemonType, "UTF-8") + "&" +
                    URLEncoder.encode("pokemonTypeAtt", "UTF-8") + "=" + URLEncoder.encode(pokemonTypeAtt, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    private String findRangeRegion(String[] params) {

        String regionName = params[1];
        String json_url = "http://mertkoo.com/PokemonWorld/region.php";

        try {
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("regionName", "UTF-8") + "=" + URLEncoder.encode(regionName, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }


    private String ownPokePocket(String[] params) {

        String json_url = "http://mertkoo.com/PokemonWorld/ownPokePocket.php";

        try {
            String username = params[1];
            URL url = new URL(json_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String JSON_STRING;
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "/n");
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    private String catchPokemon(String[] params) {

        String url2 = "http://mertkoo.com/PokemonWorld/catchpokemon.php";
        try {
            String username = params[1];
            String pokemon = params[2];
            String exp = params[3];

            URL url = new URL(url2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                    URLEncoder.encode("pokemon", "UTF-8") + "=" + URLEncoder.encode(pokemon, "UTF-8") + "&" +
                    URLEncoder.encode("exp", "UTF-8") + "=" + URLEncoder.encode(exp, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
