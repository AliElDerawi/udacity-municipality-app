package it.communikein.municipalia.data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class Poi extends News {

    private static final String ARG_LOCATION_LAT = "location_lat";
    private static final String ARG_LOCATION_LNG = "location_lng";

    private double locationLat;
    private double locationLng;

    public Poi(String id, String title, String description, String image, long timestamp,
               double locationLat, double locationLng) {
        super(id, title, description, image, timestamp);

        setLocationLat(locationLat);
        setLocationLng(locationLng);
    }

    public Poi(String id, String title, String description, long timestamp,
               double locationLat, double locationLng) {
        this(id, title, description, null, timestamp, locationLat, locationLng);
    }

    public Poi(String id, Map<String, Object> map) {
        super(id, map);

        if (map.containsKey(ARG_LOCATION_LAT))
            setLocationLat((double)map.get(ARG_LOCATION_LAT));
        if (map.containsKey(ARG_LOCATION_LNG))
            setLocationLng((double)map.get(ARG_LOCATION_LNG));
    }


    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public LatLng getCoords() {
        return new LatLng(getLocationLat(), getLocationLng());
    }

    public void setCoords(LatLng coords) {
        setLocationLat(coords.latitude);
        setLocationLng(coords.longitude);
    }


    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Event)) return false;

        Event event = (Event) obj;
        return event.getId().equals(this.getId());
    }

    public boolean displayEquals(Object obj) {
        if (! super.displayEquals(obj)) return false;

        Event event = (Event) obj;
        return event.getLocationLat() == this.getLocationLat() &&
                event.getLocationLng() == this.getLocationLng();
    }

}
