package com.example.login1703;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapFragment extends Fragment {
    FusedLocationProviderClient client;
    SupportMapFragment supportMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity() ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        MaterialButton applyButton = view.findViewById(R.id.apply_button);
        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        FloatingActionButton floatingButton = view.findViewById(R.id.floating_action_button);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                floatingButton.setEnabled(false);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Task<Location> task = client.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                //MarkerOptions newProblem = new MarkerOptions().position(latLng);//.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon));
                                Marker newProblem = googleMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                //googleMap.addMarker(newProblem);
                                newProblem.setVisible(true);

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        applyButton.setVisibility(View.INVISIBLE);
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        floatingButton.setEnabled(true);
                                        newProblem.remove();
                                    }
                                });

                                applyButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        applyButton.setVisibility(View.INVISIBLE);
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        floatingButton.setEnabled(true);
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });


        return view;

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //MarkerOptions options = new MarkerOptions().position(latLng).title("САЛАМ").draggable(true);//.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            //googleMap.addMarker((options));
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            getCurrentLocation();
        }
    }

    public void createMarker() {

    }
}