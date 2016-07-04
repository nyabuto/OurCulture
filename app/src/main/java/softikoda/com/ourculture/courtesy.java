package softikoda.com.ourculture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


/**
 * A simple {@link Fragment} subclass.
 */
public class courtesy extends Fragment {
    public static final String JSON_URL = "http://www.wrostdevelopers.com/ourculture/loadAvailableCultures.php?district_id=11653&culture_id=1";
    ListView listView;

    public courtesy() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlist, container, false);

        listView  = (ListView) view.findViewById(R.id.listView);


        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showJSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(courtesy.this.getActivity(), error.getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                }) ;

        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);

        // Inflate the layout for this fragment
        return view;
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomListAdapter cl = new CustomListAdapter(this.getActivity(), ParseJSON.name,ParseJSON.description,ParseJSON.imageurl);
        listView.setAdapter(cl);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(view.getContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

}

