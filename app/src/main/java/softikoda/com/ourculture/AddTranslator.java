package softikoda.com.ourculture;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddTranslator extends AppCompatActivity {
    private PopupWindow pw;
    private boolean expanded; 		//to  store information whether the selected values are displayed completely or in shortened representatn
    public static boolean[] checkSelected;	// store select/unselect information about the values in the list
Button SaveTranslator;
    ArrayList<String> avalableLanguages= new ArrayList<>();
    ArrayList<String> languag = new ArrayList<>();
    String url_languages = "http://wrostdevelopers.com/ourculture/loadLanguages.php";
    String selectedLanguages="";
    String transName,transPhone;
    double transLat,transLong;
    EditText txtTransName,txtTransPhone;
    String translator_name="";
    String translator_phone="";
    String translator_languages="";
    double translator_latitude=0;
    double translator_longitude=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_translator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        avalableLanguages.clear();
        languag.clear();

        txtTransName = (EditText) findViewById(R.id.translator_name) ;
        txtTransPhone = (EditText) findViewById(R.id.translator_phone);
        SaveTranslator = (Button) findViewById(R.id.btnSaveTranslator);
        SaveTranslator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLanguages=",";
                for (int i = 0; i < checkSelected.length; i++) {
                    Log.d("selected : ", ""+checkSelected[i]);
                   if(checkSelected[i]==true){
                       selectedLanguages+=i+1+",";
                   }

                }
                Log.d("selected languages : ",selectedLanguages);
//get translator details
                translator_name = txtTransName.getText().toString().trim();
                translator_phone = txtTransPhone.getText().toString().trim();
                translator_latitude = 2.4353535;
                translator_longitude  = 43.5645123;
                translator_languages=selectedLanguages;
if(!translator_name.equals("")){

    if(!translator_phone.equals("")){

        if(!translator_languages.equals(",")){
            int val=saveTranslator();
            if(val>0){
                Toast.makeText(getApplicationContext(),"Translator added successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddTranslator.this,OurCulture.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Select languages",Toast.LENGTH_SHORT).show();
        }
    }
    else{
        Toast.makeText(getApplicationContext(),"Enter translator phone number",Toast.LENGTH_SHORT).show();
    }

}
                else{
    Toast.makeText(getApplicationContext(),"Enter translator name",Toast.LENGTH_SHORT).show();
                }

            }
        });

        initialize();
    }
    /*
        * Function to set up initial settings: Creating the data source for drop-down list, initialising the checkselected[], set the drop-down list
        * */
    private void initialize(){
//Log.d("array size : ",""+loadLanguages().size());
        //data source for drop-down list
        final ArrayList<String> items = new ArrayList<String>();
//        for(int i=0;i<loadLanguages().size();i++) {
//            items.add(loadLanguages().get(i));
//        }
        items.add("Kikuyu");
        items.add("Luhya");
        items.add("Maasai");
        items.add("Kamba");
        items.add("Luo");
        items.add("Kisii");
        items.add("Swahili");
        items.add("Kalenjin");
        items.add("Spanish");
        items.add("German");
        items.add("Chinese");
        items.add("Arabic");

        checkSelected = new boolean[items.size()];
        //initialize all values of list to 'unselected' initially
        for (int i = 0; i < checkSelected.length; i++) {
            checkSelected[i] = false;
        }

	/*SelectBox is the TextView where the selected values will be displayed in the form of "Item 1 & 'n' more".
    	 * When this selectBox is clicked it will display all the selected values
    	 * and when clicked again it will display in shortened representation as before.
    	 * */
        final TextView tv = (TextView) findViewById(R.id.SelectBox);
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!expanded){
                    //display all selected values
                    String selected = "";
                    int flag = 0;
                    for (int i = 0; i < items.size(); i++) {
                        if (checkSelected[i] == true) {
                            selected += items.get(i);
                            selected += ", ";
                            flag = 1;
                        }
                    }
                    if(flag==1)
                        tv.setText(selected);
                    expanded =true;
                }
                else{
                    //display shortened representation of selected values
                    tv.setText(DropDownListAdapter.getSelected());
                    expanded = false;
                }
            }
        });

        //onClickListener to initiate the dropDown list
        Button createButton = (Button)findViewById(R.id.create);
        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopUp(items,tv);
            }
        });
    }

    /*
     * Function to set up the pop-up window which acts as drop-down list
     * */
    private void initiatePopUp(ArrayList<String> items, TextView tv){
        LayoutInflater inflater = (LayoutInflater)AddTranslator.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get the pop-up window i.e.  drop-down layout
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pop_up_window, (ViewGroup) findViewById(R.id.PopUpView));

        //get the view to which drop-down layout is to be anchored
        RelativeLayout layout1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
        pw = new PopupWindow(layout, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });

        //provide the source layout for drop-down
        pw.setContentView(layout);

        //anchor the drop-down to bottom-left corner of 'layout1'
        pw.showAsDropDown(layout1);

        //populate the drop-down list
        final ListView list = (ListView) layout.findViewById(R.id.dropDownList);
        DropDownListAdapter adapter = new DropDownListAdapter(this, items, tv);
        list.setAdapter(adapter);
    }

    public ArrayList<String> loadLanguages(){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(url_languages, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("response is : ", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Log.d("array data : ",response.length()+" data : "+response.getString(i));
                        languag.add(response.getString(i))  ;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("trial : ", " error " + e);
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.getMessage());
                }
            })

            {

            };
            queue.add(request);
Log.d("new array size : ",""+languag.size());
        return languag;
    }


    public int saveTranslator(){
    String    translator_url="http://wrostdevelopers.com/ourculture/saveTranslator.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,translator_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response is : ", response.toString());

                    if(response.contains("added")){
                 Log.d("add translator : ","added successfully");
                    }
                    else{
                        Log.d("add translator : ","failed to add");
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("translator_name", translator_name);
                params.put("translator_phone", translator_phone);
                params.put("translator_languages",translator_languages);
                params.put("latitude", ""+translator_latitude);
                params.put("longitude", ""+translator_longitude);
                return params;
            }

        };
        queue.add(request);

return 1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    }
