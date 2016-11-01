package ai.tiktik;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends Activity {

    TextView location_id;
    TextView network;

    LocationManager locationManager;
    LocationListener locationListener;
    FloatingActionButton refresh_main;
    TextView carrier_name;
    TextView signal_strength;
    TextView current_time ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        refresh_main = (FloatingActionButton) findViewById(R.id.refresh_main);
        location_id = (TextView) findViewById(R.id.location_id);
        network = (TextView) findViewById(R.id.network);
        carrier_name = (TextView) findViewById(R.id.carrier_name);
        signal_strength = (TextView) findViewById(R.id.signal_strength);
        current_time = (TextView)findViewById(R.id.current_time);




        //code to know the network details of user
        networkName();
        // code to know the carrier name of the user
        carrierName();
        //code to know the network strength of the phone
        networkStrength();
        // here im calling the phone current time
        currentTime();



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(DetailActivity.this, "" + location.getLongitude() + "-->" + location.getLatitude()
                        , Toast.LENGTH_LONG).show();


                Log.d("LAtitude", "longitude" + location.getLongitude() + "/" + "latitude" + location.getLatitude());
                String data = String.valueOf(location.getLongitude());
                Toast.makeText(DetailActivity.this, "" + data, Toast.LENGTH_SHORT).show();

                location_id.setText(data);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);

            }
        };
        // permission  for marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            conFigButton();

        }
    }


    // here result is stored
    // code to open Dialog box
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                // if result is cancelled  the array is empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    // here it is calling method to get the location
                    conFigButton();
                return;
        }
    }

    private void conFigButton() {
        refresh_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);


            }
        });
    }

    public void carrierName() {
        TelephonyManager manager = (TelephonyManager) DetailActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        carrier_name.setText(carrierName);

    }

    public void networkName() {
        connection cn = new connection();
        String data = cn.getNetworkClass(this);
        Log.d("Stringssss", "Stringsss" + data);
        network.setText(data);

    }

    public void networkStrength() {

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellList = telephonyManager.getAllCellInfo();
        CellInfoWcdma cellinfoWcdma = null;
        if (cellList != null && !cellList.isEmpty()) {
            cellinfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
            CellSignalStrengthWcdma cellSignalStrengthWcdma = cellinfoWcdma.getCellSignalStrength();
            if (Build.VERSION.SDK_INT >= 18) {
                String data = String.valueOf(cellSignalStrengthWcdma.getDbm());
                Toast.makeText(DetailActivity.this,""+data,Toast.LENGTH_SHORT).show();

                signal_strength.setText(data+"dpm");
            }
        }
    }

    public void currentTime(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm:ss a");
        String strDate = mdformat.format(calendar.getTime());

        current_time.setText(strDate);


        Toast.makeText(DetailActivity.this,""+strDate,Toast.LENGTH_SHORT).show();
    }
            }









