package softikoda.com.ourculture;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class translator extends Fragment {

    Spinner mylanguage,translatorlanguage;
    Button btnLanguage;
    String latitude,longitude;
    String visitorlanguage="";
    String tranlanguage= "";

    public translator() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translator, container, false);

//        Intent intent= new Intent();
//        Bundle my_bundle_received=intent.getExtras();
//        latitude = my_bundle_received.get("latitude").toString();
//        longitude = my_bundle_received.get("longitude").toString();

         mylanguage = (Spinner)view.findViewById(R.id.spinner);
        translatorlanguage = (Spinner)view.findViewById(R.id.spinner1);
//        visitorlanguage = mylanguage.getSelectedItem().toString();
//        tranlanguage=translatorlanguage.getSelectedItem().toString();
        btnLanguage = (Button) view.findViewById(R.id.button);

    btnLanguage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         //   Toast.makeText(getActivity(),mylanguage.getSelectedItem() + " and " + translatorlanguage.getSelectedItem(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(),TranslatorList.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("visitorlanguage", visitorlanguage);
            intent.putExtra("tranlanguage", tranlanguage);
            startActivity(intent);


        }
    });

        // Inflate the layout for this fragment
        return view;
    }


}
