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
public class CustomListAdapter extends ArrayAdapter<String> {
    private String[] name;
    private String[] description;
    private String[] imageurl;
    private Activity context;


    public CustomListAdapter(Activity context, String[] name, String[] description, String[] imageurl) {
        super(context, R.layout.fragmentlist, name);
        this.context = context;
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.custom_list, null, true);
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView myImage = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewId.setText(name[position]);
        textViewName.setText(description[position]);

        Picasso.with(context)
                .load(imageurl[position])
                        //.placeholder(R.drawable.ic_placeholder)   // optional
                       // .error(R.drawable.defaults)      // optional
                .resize(250, 200)                        // optional
                        //.rotate(0)                             // optional
                        // .fit()
                .into(myImage);



        return listViewItem;
    }



}