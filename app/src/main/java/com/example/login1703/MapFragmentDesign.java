package com.example.login1703;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.example.login1703.Models.FirebaseMarkerDetails;
import com.example.login1703.Models.Markers;
import com.example.login1703.Models.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapFragmentDesign extends Fragment {

    private static final String TAG = "EmailPassword";

    FusedLocationProviderClient client;
    SupportMapFragment supportMapFragment;

    private DatabaseReference mDataBase;

    Marker currentMarker;

    boolean selectButton = false;

    String type = "";

    private FirebaseAuth mAuth;

    private ArrayList<String> adminArrayList = new ArrayList<>();

    MaterialButton nextButton;
    MaterialButton cancelButton;
    FloatingActionButton floatingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adminArrayList.add("sergey.khmizyuk@gmail.com");
        adminArrayList.add("super.shomka@mail.ru");

        mDataBase = FirebaseDatabase.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mAuth = FirebaseAuth.getInstance();

        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        mDataBase.child("markers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("GetFrom", "ok");
                for (DataSnapshot child: snapshot.getChildren()) {
                    Markers marker = child.getValue(Markers.class);

                    String getSnippet = marker.getSnippet();
                    String[] splitArray = getSnippet.split("\n");
                    //for (String s: splitArray)
                        //Log.i("pppppp", "splitArrayFor: "+s);

                    LatLng latLng = new LatLng(marker.getLatitude(), marker.getLongitude());
                    client = LocationServices.getFusedLocationProviderClient(getContext());
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Task<Location> task = client.getLastLocation();
                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.i("GetFrom", "ok");
                            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    Log.i("GetFrom", "ok");

                                    Marker newProblem = googleMap.addMarker(new MarkerOptions().position(latLng).snippet(marker.getSnippet()));

                                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {
                                            //marker.showInfoWindow();
                                            //marker.hideInfoWindow();

                                            FirebaseUser user = mAuth.getCurrentUser();

                                            boolean isAdmin = false;
                                            for (String adminEmail : adminArrayList)
                                                if (user.getEmail().equals(adminEmail)) {
                                                    isAdmin = true;
                                                    break;
                                                }
                                            if (isAdmin && marker.getAlpha()==1.0f)
                                                showMarkerInfoWindowAdmin(marker);
                                            else if (!isAdmin && marker.getAlpha()==1.0f)
                                                showMarkerInfoWindowUser(marker);

                                            return false;
                                        }
                                    });

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.i("GetFrom", e.toString());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("GetFrom", error.toString());
            }
        });

        /*mDataBase.child("markers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //if (dataSnapshot==null) return;
                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {

                    mDataBase.child("markers").child(postSnapShot.getKey()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Markers marker = dataSnapshot.getValue(Markers.class);
                            LatLng latLng = new LatLng(marker.getLatitude(), marker.getLongitude());
                            client = LocationServices.getFusedLocationProviderClient(getContext());
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
                                            Marker newProblem = googleMap.addMarker(new MarkerOptions().position(latLng).title(marker.getId()).snippet(marker.getSnippet()));
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.i()
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("GetFromDatabase", databaseError.toString());
            }
        });*/

        nextButton = view.findViewById(R.id.apply_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        floatingButton = view.findViewById(R.id.floating_action_button);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextButton.setVisibility(View.VISIBLE);
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
                                double currentLatitude = location.getLatitude();
                                double currentLongitude = location.getLongitude();
                                //MarkerOptions newProblem = new MarkerOptions().position(latLng);//.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon));
                                Marker newProblem = googleMap.addMarker(new MarkerOptions().position(latLng).alpha(0.5f));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                //googleMap.addMarker(newProblem);

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        nextButton.setVisibility(View.INVISIBLE);
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        floatingButton.setEnabled(true);
                                        newProblem.remove();
                                    }
                                });

                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        nextButton.setVisibility(View.INVISIBLE);
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        floatingButton.setVisibility(View.INVISIBLE);
                                        newProblem.setDraggable(false);

                                        //показ всплывающего окна
                                        showAddProblemWindow(newProblem, currentLatitude, currentLongitude);
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


    private void showMarkerInfoWindowUser(Marker marker) {
        AlertDialog.Builder window = new AlertDialog.Builder(getContext());
        window.setTitle("Информация о метке");

        String getSnippet = marker.getSnippet();
        String[] splitArray = getSnippet.split("\n");
        for (String s: splitArray)
            Log.i("pppppp", "splitArrayFor: "+s);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View showMarkerInfoWindowUser = inflater.inflate(R.layout.show_marker_info_window_user, null);
        window.setView(showMarkerInfoWindowUser);

        MaterialTextView markerInfo = showMarkerInfoWindowUser.findViewById(R.id.markerInfo);
        markerInfo.setText(splitArray[1]);

        window.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        window.show();
    }

    private void showMarkerInfoWindowAdmin(Marker marker) {
        AlertDialog.Builder window = new AlertDialog.Builder(getContext());
        window.setTitle("Информация о метке");

        String getSnippet = marker.getSnippet();
        String[] splitArray = getSnippet.split("\n");
        for (String s: splitArray)
            Log.i("pppppp", "splitArrayFor: "+s);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View showMarkerInfoWindowAdmin = inflater.inflate(R.layout.show_marker_info_window_admin, null);
        window.setView(showMarkerInfoWindowAdmin);

        CheckBox checkBox1 = showMarkerInfoWindowAdmin.findViewById(R.id.checkBox1);
        CheckBox checkBox2 = showMarkerInfoWindowAdmin.findViewById(R.id.checkBox2);

        MaterialButton deleteMarker = showMarkerInfoWindowAdmin.findViewById(R.id.deleteMarker);
        deleteMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked() && checkBox2.isChecked()) {
                    marker.remove();
                    //TODO delete

                    /*FirebaseDatabase.getInstance().getReference().orderByChild("id").equalTo(marker.getTitle()).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                                        child.getRef().removeValue();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                                }
                            });*/

                    String getSnippet = marker.getSnippet();
                    String[] splitArray = getSnippet.split("\n");

                    mDataBase.child("markers").child(splitArray[0]).removeValue();
                    ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);
                }
            }
        });

        MaterialTextView markerInfo = showMarkerInfoWindowAdmin.findViewById(R.id.markerInfo);
        markerInfo.setText(splitArray[1]);

        MaterialTextView emailInfo = showMarkerInfoWindowAdmin.findViewById(R.id.emailInfo);
        String info = "Автор: "+splitArray[2];
        emailInfo.setText(info);

        window.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        window.show();
    }

    private void showAddProblemWindow(Marker marker, double latitude, double longitude) {

        AlertDialog.Builder window = new AlertDialog.Builder(getContext());
        window.setTitle("Новая метка");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View addProblemWindow = inflater.inflate(R.layout.add_problem_window, null);
        window.setView(addProblemWindow);

        TextInputEditText message = addProblemWindow.findViewById(R.id.message);

        Button trafficLight = addProblemWindow.findViewById(R.id.traffic_light);
        trafficLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton = true;
                type = "trafficLight";
                //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
        });

        Button bin = addProblemWindow.findViewById(R.id.bin);
        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton = true;
                type = "bin";
                //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }
        });

        Button another = addProblemWindow.findViewById(R.id.another);
        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton = true;
                type = "another";
                //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            }
        });

        window.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                floatingButton.setVisibility(View.VISIBLE);
                floatingButton.setEnabled(true);
                marker.remove();
                dialog.dismiss();
            }
        });

        window.setPositiveButton("Добавить метку", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ((TextUtils.isEmpty(message.getText().toString())) || (!selectButton)) {
                    //TODO обработка ошибки пустых полей
                    floatingButton.setVisibility(View.VISIBLE);
                    floatingButton.setEnabled(true);
                    marker.remove();
                    dialog.dismiss();
                } else {
                    nextButton.setVisibility(View.INVISIBLE);
                    cancelButton.setVisibility(View.INVISIBLE);
                    floatingButton.setVisibility(View.VISIBLE);
                    floatingButton.setEnabled(true);

                    String inputMessage = message.getText().toString();

                    marker.setAlpha(1.0f);

                    /*String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZqwertyuiopasdfghjklzxcvbnm1234567890";
                    int length = 10;
                    Random random = new Random();
                    StringBuilder id = new StringBuilder(length);
                    for (int n=0; n<4; n++) {
                        for (int i = 0; i < length; i++)
                            id.append(characters.charAt(random.nextInt(characters.length())));
                        id.append("-");
                    }
                    id.delete(id.length()-1,id.length());*/

                    //String pythKey = FirebaseDatabase.getInstance().getReference().child("markers").push().getKey();

                    DatabaseReference ref = mDataBase.child("markers").push();
                    String pathKey = ref.getKey();
                    marker.setTitle(pathKey);
                    marker.setSnippet(pathKey+"\n"+inputMessage+"\n"+mAuth.getCurrentUser().getEmail());
                    ref.setValue(new Markers(
                            marker.getSnippet(),
                            marker.getPosition().latitude,
                            marker.getPosition().longitude,
                            type,
                            marker.getTitle()
                    )).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "addMarkerToDatabase:success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d(TAG, "addMarkerToDatabase:failure. " + e);
                        }
                    });

                    ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);

                    /*mDataBase.child("markers").push().setValue(
                            new Markers(
                                    inputMessage,
                                    marker.getPosition().latitude,
                                    marker.getPosition().longitude,
                                    type,
                                    marker.getTitle()
                            )
                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "addMarkerToDatabase:success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d(TAG, "addMarkerToDatabase:failure. "+e);
                        }
                    });*/

                    /*FirebaseDatabase.getInstance().getReference().child(pythKey).setValue(
                            new Markers(
                                    inputMessage,
                                    marker.getPosition().latitude,
                                    marker.getPosition().longitude,
                                    type,
                                    marker.getTitle()
                            )
                    );*/

                    /*FirebaseDatabase.getInstance().getReference().orderByChild("id").equalTo(marker.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });*/

                    dialog.dismiss();

                    selectButton = false;
                }
            }
        });

        window.show();
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
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
            getCurrentLocation();
        }
    }

    /*@Override
    public void onLocationChanged(@NonNull Location location) {
        LatLng startPosition = new LatLng(location.getLatitude(), location.getLongitude());;
        if (currentMarker==null){
            Marker newProblem = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .draggable(false));
        }
    }*/

    /*private void addPostEventListener(DatabaseReference mPostReference) {
        client = LocationServices.getFusedLocationProviderClient(getContext());
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
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get Markers object and use the values to update the UI
                                Markers markerForCreate = dataSnapshot.getValue(Markers.class);
                                LatLng latLng = new LatLng(markerForCreate.getLatitude(), markerForCreate.getLongitude());
                                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).snippet(markerForCreate.getSnippet()).title(markerForCreate.getId()));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            }
                        };
                        mPostReference.addValueEventListener(postListener);
                    }
                });
            }
        });
    }*/
}