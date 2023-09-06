package com.example.travelandtourismguide;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.travelandtourismguide.Maps.FetchURL;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Garbage {
//    private static final String TAG = MapsActivity.class.getSimpleName();
//    private GoogleMap map;
//    private CameraPosition cameraPosition;
//
//    // The entry point to the Places API.
//    private PlacesClient placesClient;
//
//    // The entry point to the Fused Location Provider.
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//    // A default location (Sydney, Australia) and default zoom to use when location permission is
//    // not granted.
//    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
//    private static final int DEFAULT_ZOOM = 15;
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    private boolean locationPermissionGranted;
//
//    // The geographical location where the device is currently located. That is, the last-known
//    // location retrieved by the Fused Location Provider.
//    private Location lastKnownLocation;
//
//    // Keys for storing activity state.
//    private static final String KEY_CAMERA_POSITION = "camera_position";
//    private static final String KEY_LOCATION = "location";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Retrieve location and camera position from saved instance state.
//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }
//
//        // Retrieve the content view that renders the map.
//        setContentView(R.layout.activity_maps);
//
//        // Construct a PlacesClient
//        Places.initialize(getApplicationContext(), getString(R.string.api_key));
//        placesClient = Places.createClient(this);
//
//        // Construct a FusedLocationProviderClient.
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // Build the map.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    /**
//     * Saves the state of the map when the activity is paused.
//     */
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        if (map != null) {
//            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
//            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
//        }
//        super.onSaveInstanceState(outState);
//    }
//
//    /**
//     * Manipulates the map when it's available.
//     * This callback is triggered when the map is ready to be used.
//     */
//    @Override
//    public void onMapReady(GoogleMap map) {
//        this.map = map;
//
//        // Use a custom info window adapter to handle multiple lines of text in the
//        // info window contents.
//        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
//                        (FrameLayout) findViewById(R.id.map), false);
//
//                TextView title = infoWindow.findViewById(R.id.title);
//                title.setText(marker.getTitle());
//
//                TextView snippet = infoWindow.findViewById(R.id.snippet);
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });
//
//        // Prompt the user for permission.
//        getLocationPermission();
//
//        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
//
//        // Get the current location of the device and set the position of the map.
//        getDeviceLocation();
//    }
//
//    /**
//     * Gets the current location of the device, and positions the map's camera.
//     */
//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (locationPermissionGranted) {
//                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(lastKnownLocation.getLatitude(),
//                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            map.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                            map.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Prompts the user for permission to use the device location.
//     */
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    /**
//     * Handles the result of the request for location permissions.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        locationPermissionGranted = false;
//        if (requestCode
//                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//        updateLocationUI();
//    }
//
//
//    /**
//     * Updates the map's UI settings based on whether the user has granted location permission.
//     */
//    private void updateLocationUI() {
//        if (map == null) {
//            return;
//        }
//        try {
//            if (locationPermissionGranted) {
//                map.setMyLocationEnabled(true);
//                map.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//                map.setMyLocationEnabled(false);
//                map.getUiSettings().setMyLocationButtonEnabled(false);
//                lastKnownLocation = null;
//                getLocationPermission();
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//Correct Code



















//Code to get Coordinates of current Position
//public class MapsActivity extends AppCompatActivity {
//    private static final int REQUEST_LOCATION = 1;
//    Button btnGetLocation;
//    TextView showLocation;
//    LocationManager locationManager;
//    String latitude, longitude;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        ActivityCompat.requestPermissions( this,
//                new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
////        showLocation = findViewById(R.id.showLocation);
////        btnGetLocation = findViewById(R.id.btnGetLocation);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//        } else {
//            getLocation();
//        }
//    }
//
//
//    private void OnGPS() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(longi);
//                Toast.makeText(this, "Latitude = "+latitude+" \nLongitude = "+longitude, Toast.LENGTH_LONG).show();
////                showLocation.setText("Your Location: " + " " + "Latitude: " + latitude + " " + "Longitude: " + longitude);
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//}







//Program for getting current location and Given Location
//protected void onCreate(@Nullable Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_maps);
//    getDirection = findViewById(R.id.btnGetDirection);
//
//    GetCurrentPosition();
//    getDirection.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            new FetchURL(MapsActivity.this).execute(getUrl(current.getPosition(), Tou_place.getPosition(), "driving"), "driving");
//        }
//    });
//    //27.658143,85.3199503
//    //27.667491,85.3208583
//
//    Log.d("9999999", "onCreate: "+lat +"\n "+longi);
//
//    current = new MarkerOptions().position(new LatLng(lat,longi)).title("Current Location");
//    Tou_place = new MarkerOptions().position(new LatLng(27.1751, 78.0421)).title("Location 2");
//    SupportMapFragment mapFragment = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
//    mapFragment.getMapAsync(this);
//}
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        Log.d("mylog", "Added Markers");
//        mMap.addMarker(current);
//        mMap.addMarker(Tou_place);
//
//    }
//
//    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Mode
//        String mode = "mode=" + directionMode;
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + mode;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.api_key);
//        return url;
//    }
//
//    @Override
//    public void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
//        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
//    }
//
//
//
//    public void GetCurrentPosition(){
//
//        ActivityCompat.requestPermissions( this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//        } else {
//            getLocation();
//        }
//    }
//
//    private void OnGPS() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                lat = locationGPS.getLatitude();
//                longi = locationGPS.getLongitude();
//
//                Toast.makeText(this, "Latitude = "+lat+" \nLongitude = "+longi, Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
