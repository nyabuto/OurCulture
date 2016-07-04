package softikoda.com.ourculture;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geofrey on 5/13/2016.
 */
public class AuthRequest extends JsonArrayRequest{
    public AuthRequest(int method, String url, JSONArray jsonRequest,
                       Response.Listener<JSONArray> listener,
                       Response.ErrorListener errorListener) {
        super(url,listener, errorListener);
//        super(method, url, jsonRequest, listener, errorListener);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return createBasicAuthHeader("hacker233", "k+UN8JzAAS8");
    }

    Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<>();

        String credentials = username + ":" + password;
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + encodedCredentials);

        return headerMap;
    }
}
