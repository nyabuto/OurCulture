package softikoda.com.ourculture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OurCulture extends AppCompatActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
//    String district_url = "https://distribution-xml.booking.com/json/bookings.getDistrictHotels?city_ids=-2140479";
    String district_url = "https://distribution-xml.booking.com/json/bookings.getDistrictHotels?countrycodes=ke";
    String hotels_url = "https://distribution-xml.booking.com/json/bookings.getHotels?hotel_ids=";
    JSONArray allDistricts = null;
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    int radius;
    ProgressDialog progressDialog;
    private boolean isGPSEnabled;
LatLng lastHotel;
    String hotelName="none";
    String hotel_id,hotel_lat,hotel_long,hotel_district;
    final Context context = this;
    Button btnSave,btnSaveCulture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_culture);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CreateMap();
    }

    public void CreateMap() {
        mMap.clear();
        progressDialog = new ProgressDialog(OurCulture.this);
        progressDialog.setMessage("Loading Hotels ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        radius = 1000000;


        RequestQueue queue = Volley.newRequestQueue(this);
        AuthRequest request = new AuthRequest(Request.Method.GET, district_url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                mMarkersHashMap = new HashMap<Marker, MyMarker>();
                mMyMarkersArray.clear();

                try {
Log.d("no of hotels : ",""+response.length());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jObj = response.getJSONObject(i);

                        final String district_id = jObj.getString("district_id");
                        final String hotel_id = jObj.getString("hotel_id");
//Log.d("hotel id : ",hotel_id);
//    get hotel_descriptions
                        String full_hotel_url=hotels_url+hotel_id;
                        Log.d("hotel_url ",full_hotel_url);



                        RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
                        AuthRequest request2 =  new AuthRequest(Request.Method.GET,full_hotel_url, null, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response2) {
                                Log.d("response 2 is :?>>>>>> ", response2.toString());
                                try {

                                    for (int i = 0; i < response2.length(); i++) {
                                        JSONObject jObj = response2.getJSONObject(i);
                                        String city = jObj.getString("city");
                                        String hotel_name=jObj.getString("name");
                                        String address = jObj.getString("address");
                                        String review_score = jObj.getString("review_score");
                                        JSONObject latlongObj = jObj.getJSONObject("location");

                                        double hotel_latitude = latlongObj.getDouble("latitude");
                                        double hotel_longitude = latlongObj.getDouble("longitude");
Log.d("district id : ",district_id);
                                        mMyMarkersArray.add(new MyMarker(district_id, city, hotel_id, address, review_score, hotel_latitude, hotel_longitude,hotel_name));

//    get hotel_descriptions
                                        lastHotel = new LatLng(hotel_latitude,hotel_longitude);
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastHotel));
                                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));


                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                    Log.d("trial : ", " error " + e1);
                                }

                                plotMarkers(mMyMarkersArray);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error2) {
                                Log.e("Error", error2.getMessage());
                            }
                        })

                        {

                        };
                        queue2.add(request2);
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
        progressDialog.dismiss();
    }

    private void plotMarkers(ArrayList<MyMarker> markers) {
        Log.d("end of loading codes : ", "plotted");
        if (markers.size() > 0) {
            for (final MyMarker myMarker : markers) {

                Log.d("well : ", "looped");
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getMlatitude(), myMarker.getMlongitudes()));
                try {
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                } catch (Exception e) {
                    Log.d("error on click : ", e.getMessage());
                }
                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
//                      PASS PARAMETERS TO THE NEXT WINDOW

                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.action_layout);
                        dialog.setTitle("Choose Action");
                        dialog.setCancelable(true);


                        btnSave = (Button) dialog.findViewById(R.id.btnSave);
                        btnSaveCulture = (Button) dialog.findViewById(R.id.btnBookCulture);

                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                        btnSaveCulture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();

                                Intent intent = new Intent(OurCulture.this,MainActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putString("hotel_name",hotelName);
                                bundle.putString("hotel_id",hotel_id);
                                bundle.putString("hotel_lat",hotel_lat);
                                bundle.putString("hotel_long",hotel_long);
                                bundle.putString("hotel_district",hotel_district);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                    }
                });
            }
            progressDialog.dismiss();
        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        public MarkerInfoWindowAdapter() {

        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.map_info, null);

            final MyMarker myMarker = mMarkersHashMap.get(marker);

            TextView map_hotelName = (TextView) v.findViewById(R.id.hotel_name);
            TextView map_address = (TextView) v.findViewById(R.id.address);
            TextView map_reviews = (TextView) v.findViewById(R.id.reviews);
            TextView map_city = (TextView) v.findViewById(R.id.city);
            Button map_book = (Button) v.findViewById(R.id.bookButton);

            try {

                map_hotelName.setText("" + myMarker.getmHotel_name()+" Hotel");
                map_address.setText("Address : " + myMarker.getmAddress() + "");
                map_reviews.setText("Rating : " + myMarker.getmReview());
                map_city.setText(""+myMarker.getmCity() + "");

hotelName=myMarker.getmHotel_name();
hotel_id=myMarker.getmHotel_id();
hotel_lat=""+myMarker.getMlatitude();
hotel_long=""+myMarker.getMlongitudes();
hotel_district=myMarker.getmDistrict_id();

            } catch (Exception e) {
                Log.d("error on click 2 : ", e.getMessage());
            }

            return v;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_addTranslator) {
            Intent intentTrans = new Intent(OurCulture.this,AddTranslator.class);
            startActivity(intentTrans);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadDialog(){


    }
}


