package com.lambton.maps_harpreetkaur_c0788553;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class MapsActivity<degrees> extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_CODE = 1;
    private Marker homeMarker;
    private Marker destMarker;

    Polyline line;
    Polygon shape;

    private static final int POLYGON_SIDES = 4;
    List<Marker> markerList = new ArrayList<>();

    // polyLine  line;
    //location manager and location listener

    LocationManager locationManager;
    LocationListener locationListener;

    function degrees = null;

    private GoogleMap map;
    private java.lang.Object Object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

//    var map, markerA, markerB;
//
//    // Callback executed when Google Maps script has been loaded
//    function initMap() {
//        // 1. Initialize map
//        Object zoom;
//        Document document;
//        map = new google.maps.Map(document.getElementById("map"), {
//                zoom: 10,
//
//
//                center:Object lng;
//        {
//        {
//            lat: 6.066385702972249,
//                    lng: -74.07493328924413,
//        }
//    });
//
//        // 2. Put first marker in Bucaramanga
//        Object position;
//        markerA = new google.maps.Marker({
//                position: {
//            lat: 7.099473939079819,
//                    lng: -74.07493328924413,
//        },
//        Object icon;
//        map: map,
//                icon: {
//            url:
//            "http://maps.google.com/mapfiles/kml/pushpin/red-pushpin.png"
//        },
//        title: "Marker A"
//    });
//
//        // 3. Put second marker in Bogota
//        markerB = new google.maps.Marker({
//                position: {
//            lat: 4.710993389138328,
//                    lng: -74.07209873199463
//        },
//        map: map,
//                icon: {
//            url:
//            "http://maps.google.com/mapfiles/kml/pushpin/red-pushpin.png"
//        },
//        title: "Marker B"
//    });
//    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {






            @Override
            public void onLocationChanged(@NonNull Location location) {
                //set home marker
                setHomeMarker(location);

            }
        };

        if (!hasLocationPermission())
            requestLocationPermission();
        else

            startUpdateLocation();
        //apply long press
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //set marker
                setmarker(latLng);
            }

            private void setmarker(LatLng latLng) {
                MarkerOptions options = new MarkerOptions().position(latLng);
                options.title("Marker A");
//                Object lng;

//                            lng:-73.106770,
//
//                },
//                Object title;
//                map:
//                mMap,
//                        title: "Marker A",
//
//                });



                //   mMap.addMarker(new MarkerOptions()).getPosition(new LatLng(latLng)).draggable(true);

            /*    if(destMarker != null)
                    clearMap();
                destMarker = mMap.addMarker(options);

                    drawLine();*/
                //   destMarker.isDraggable() = true;
                //check if there are already same number of markers,so clear the page
                if (markerList.size() == POLYGON_SIDES)
                    clearMap();
                markerList.add(mMap.addMarker(options));
                if (markerList.size() == POLYGON_SIDES)
                    drawShape();

            }


            private void drawShape() {
                PolygonOptions options = new PolygonOptions();
                options.fillColor(0x35000000);
                //options.fillColor(3500000)

                options.strokeColor(Color.RED)
                        .strokeWidth(5);

                for (int i = 0; i < POLYGON_SIDES; i++) {
                    options.add(markerList.get(i).getPosition());
                }
                shape = mMap.addPolygon(options);

            }


            private void drawLine() {
                PolylineOptions options = new PolylineOptions()
                        .color(Color.GREEN)
                        .width(10)
                        .add(homeMarker.getPosition(), destMarker.getPosition());
                line = mMap.addPolyline(options);
            }
        });
    }


    private void clearMap() {
           /*     if(destMarker != null) {
                    destMarker.remove();
                    destMarker = null;
                }
                line.remove();
  */
        for (Marker marker : markerList)
            marker.remove();
        markerList.clear();
        shape.remove();
        shape = null;
    }



    private void startUpdateLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,0, locationListener);
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);


    }


//    private boolean isGrantedPermission() {
//        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }

    private boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void setHomeMarker(Location location) {
        LatLng usrLoc = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions options = new MarkerOptions().position(usrLoc)
                .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .snippet("Your Location");
        homeMarker = mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usrLoc, 15));


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (REQUEST_CODE == requestCode) {
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,0, locationListener);


            }
        }

            }
        }



























































