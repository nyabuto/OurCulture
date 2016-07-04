package softikoda.com.ourculture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TranslatorList extends AppCompatActivity {

    ListView listView;
    String visitorlanguage,translatorlanguage,longitude,latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//                Intent intent= new Intent();
//        Bundle my_bundle_received=intent.getExtras();
//        latitude = my_bundle_received.get("latitude").toString();
//        longitude = my_bundle_received.get("longitude").toString();
//        visitorlanguage = my_bundle_received.get("visitorlanguage").toString();
//        translatorlanguage = my_bundle_received.get("translatorlanguage").toString();

        latitude="1.246674";
        longitude="2.3465765";
        visitorlanguage = "1";
        translatorlanguage ="3";

        String JSON_URL = "http://www.wrostdevelopers.com/ourculture/loadAvailableTranslators.php?fromLanguage=" + visitorlanguage +
                "&toLanguage=" + translatorlanguage + "&hotel_latitude=" + latitude+"&hotel_longitude="+ longitude;
        Log.d("json url--", JSON_URL);
        listView  = (ListView) findViewById(R.id.listView);


        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("translator response -->", response);
                        showJSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TranslatorList.this, error.getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                }) ;

        RequestQueue requestQueue = Volley.newRequestQueue(TranslatorList.this);
        requestQueue.add(stringRequest);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showJSON(String json){
        TranslatorParser pj = new TranslatorParser(json);
        pj.TranslatorParser();
        TranslatorListAdapter cl = new TranslatorListAdapter(TranslatorList.this, TranslatorParser.translator_name,TranslatorParser.phone,TranslatorParser.distance);
        listView.setAdapter(cl);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}
