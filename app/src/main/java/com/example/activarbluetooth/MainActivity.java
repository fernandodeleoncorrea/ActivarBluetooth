package com.example.activarbluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BT = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
    }

    public void habilitarBluetooth(View v){
        solicitarPermiso();

        BluetoothAdapter mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBlueToothAdapter == null){
            Toast.makeText(this, "NO hay bt", Toast.LENGTH_SHORT).show();
        }

        if(!mBlueToothAdapter.isEnabled()){
            Intent habilitarBTintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(habilitarBTintent, CODIGO_SOLICITUD_HABILITAR_BT);
        }
    }

    public boolean chequearStatusPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if (resultado == PackageManager.PERMISSION_GRANTED){
            return true;
        }   else {
            return false;
        }
    }

    public void solicitarPermiso(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH))
        {
            Toast.makeText(this, "El permiso ya fue otorgado", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                if(chequearStatusPermiso()){
                    Toast.makeText(this, "Ya esta activo el permiso de BT", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "NO esta activo el permiso de BT", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}