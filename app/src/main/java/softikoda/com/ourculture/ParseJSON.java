package softikoda.com.ourculture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnolwamba on 2/22/2016.
 */
public class ParseJSON {


    public static String[] imageurl;
    public static String[] description;
    public static String[] name;


    public static final String JSON_ARRAY = "all_culturedetails";
    public static final String KEY_IMAGEURL= "culturedetails_url";
    public static final String KEY_DESCRIPTION= "culturedetails_description";
    public static final String KEY_NAME = "culturedetails_name";

    private JSONArray items = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            items = jsonObject.getJSONArray(JSON_ARRAY);

            imageurl = new String[items.length()];
            description = new String[items.length()];
            name = new String[items.length()];

            for(int i=0;i<items.length();i++){
                JSONObject jo = items.getJSONObject(i);
                imageurl[i] = jo.getString(KEY_IMAGEURL);
                description[i] = jo.getString(KEY_DESCRIPTION);
                name[i] = jo.getString(KEY_NAME);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
