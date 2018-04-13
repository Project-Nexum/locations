package com.example.gurpreetsingh.locations;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private DatabaseReference mDatabase;
    private Button btnSave;
    private Button btnRet;
    private EditText etName;
    private EditText etAdd;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRet = (Button) findViewById(R.id.btnRet);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        etName= (EditText) findViewById(R.id.etName);
        etAdd= (EditText) findViewById(R.id.etAdd);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }

    String add;
    double latitude;
    double longitude;

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        add = etAdd.getText().toString();

        Geocoder geocoder = new Geocoder(MainActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(add, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());

            latitude = address.getLatitude();
            longitude = address.getLongitude();

            Log.d(TAG, "Latitude : " + latitude + " Longitude : " + longitude);
        }

    }

    private void saveInformation(){
        String name = etName.getText().toString().trim();
        String address = etAdd.getText().toString().trim();
        geoLocate();
        //latitude;
        //longitude;
        User user = new User(name, address, latitude , longitude);
        mDatabase.child("Users").setValue(user);
        Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
    if (view == btnRet){
        finish();
    }
    if (view== btnSave){
        saveInformation();
        etName.getText().clear();
        etAdd.getText().clear();
    }
    }
}
