package softikoda.com.ourculture;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnolwamba on 3/1/2016.
 */
public class TranslatorListAdapter extends ArrayAdapter<String> {
    private String[] phone;
    private String[] distance;
    private String[] name;
    private Activity context;


    public TranslatorListAdapter(Activity context, String[] name, String[] phone, String[] distance) {
        super(context, R.layout.fragmentlist, name);
        this.context = context;
        this.name = name;
        this.phone = phone;
        this.distance = distance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.custom_translator_list, null, true);
        TextView textViewName= (TextView) listViewItem.findViewById(R.id.textView2);
        //TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.textView3);
        TextView textViewDistance = (TextView) listViewItem.findViewById(R.id.textView5);

        textViewName.setText(name[position]);
        //textViewPhone.setText(phone[position]);

        Double mydistance = Double.parseDouble(distance[position]);
        Integer dista = mydistance.intValue();
        textViewDistance.setText(""+dista+" KMs away.");

        return listViewItem;
    }



}