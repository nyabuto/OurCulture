package softikoda.com.ourculture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnolwamba on 2/22/2016.
 */
public class TranslatorParser
{
    public static String[] translator_name;
    public static String[] phone;
    public static String[] distance;

    public static final String JSON_ARRAY = "all_translators";
    public static final String KEY_TRANSLATORNAME = "translator_name";
    public static final String KEY_TRANSLATORPHONE = "translator_phone";
    public static final String KEY_TRANSLATORDISTANCE = "translator_distance";

    private JSONArray items = null;

    private String json;

    public TranslatorParser(String json){
        this.json = json;
    }

    protected void TranslatorParser(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            items = jsonObject.getJSONArray(JSON_ARRAY);

            translator_name = new String[items.length()];
            phone = new String[items.length()];
            distance = new String[items.length()];

            for(int i=0;i<items.length();i++){
                JSONObject jo = items.getJSONObject(i);
                translator_name[i] = jo.getString(KEY_TRANSLATORNAME);
                phone[i] = jo.getString(KEY_TRANSLATORPHONE);
                distance[i] = jo.getString(KEY_TRANSLATORDISTANCE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
