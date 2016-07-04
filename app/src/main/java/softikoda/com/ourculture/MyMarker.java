package softikoda.com.ourculture;

/**
 * Created by Geofrey on 2/15/2016.
 */
public class MyMarker {

    double Mlatitude;
    double Mlongitudes;
    String mDistrict_id;
    String mCity;
    String mHotel_id;
    String mAddress;
    String mReview;
    String mHotel_name;


    public MyMarker(String district_id,String city, String hotel_id, String address,String review_score,double latitude,double longitudes,String hotel_name)
    {
        this.Mlatitude=latitude;
        this.Mlongitudes=longitudes;
        this.mDistrict_id=district_id;
        this.mCity = city;
        this.mHotel_id = hotel_id;
        this.mAddress = address;
        this.mReview = review_score;
        this.mHotel_name=hotel_name;
    }

    public double getMlatitude() {
        return Mlatitude;
    }

    public void setMlatitude(double mlatitude) {
        Mlatitude = mlatitude;
    }

    public double getMlongitudes() {
        return Mlongitudes;
    }

    public void setMlongitudes(double mlongitudes) {
        Mlongitudes = mlongitudes;
    }

    public String getmDistrict_id() {
        return mDistrict_id;
    }

    public void setmDistrict_id(String mDistrict_id) {
        this.mDistrict_id = mDistrict_id;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmHotel_id() {
        return mHotel_id;
    }

    public void setmHotel_id(String mHotel_id) {
        this.mHotel_id = mHotel_id;
    }

    public String getmHotel_name() {
        return mHotel_name;
    }

    public void setmHotel_name(String mHotel_name) {
        this.mHotel_name = mHotel_name;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmReview() {
        return mReview;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }

}
