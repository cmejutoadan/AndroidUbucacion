/*http://developer.android.com/training/location/retrieve-current.html*/

/*recordar:
* permisos en manifest
*  <meta-data android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" /> en el xml
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

    y en module app
        compile 'com.google.android.gms:play-services:6.5.87'*/

package com.example.cmejuto.ejemploubucacion;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

//implemntar las google apis
public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
//tener los objetos mgoogleapiclient y mlastlocation accesible para toda la clase
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private double latitud, longitud;
//conectar en el onstart
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
//desconectar en el onstop
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//llamamos al método en oncreate
        buildGoogleApiClient();
    }
//hacemos le build de
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

//accedemos a la última localización en onconected
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitud = mLastLocation.getLatitude();
            longitud = mLastLocation.getLongitude();
            Log.d("coordenadas", latitud + " - " + longitud);

            Toast.makeText(this,  latitud + " - " + longitud, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "localización no detectada", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("log", "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("log", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }
}
