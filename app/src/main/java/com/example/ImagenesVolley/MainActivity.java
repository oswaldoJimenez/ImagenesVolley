package com.example.ImagenesVolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ImangenSuper> personajes = new ArrayList<>();
    RecyclerView recyclerView;
    AdaptadorImagenes personajesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargar();

    }



        public void cargar(){
            String urlpersonajes = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";
            StringRequest request = new StringRequest(Request.Method.GET, urlpersonajes,
                    response ->  {
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray array = object.getJSONArray("heroes");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject heroeObj = array.getJSONObject(i);
                                    String objeto = "";
                                    ImangenSuper heroe = new ImangenSuper(heroeObj.getString("name"), heroeObj.getString("imageurl"));
                                    personajes.add(heroe);
                                    objeto =
                                            heroeObj.getString("name");
                                    objeto += "\n" + heroeObj.getString("imageurl");

                                    Toast.makeText(this,"objeto" + objeto,Toast.LENGTH_LONG).show();
                                    Log.d("objeto",objeto);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    }, error -> {
                    //Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this,"NO HAY CONEXIÓN",Toast.LENGTH_LONG).show();
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
            recyclerView = findViewById(R.id.listRecyclerView);
            personajesAdapter = new AdaptadorImagenes(personajes);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(personajesAdapter);
        }
}