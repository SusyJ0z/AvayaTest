package com.example.avayatest.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.avayatest.Clases.ApiTest;
import com.example.avayatest.Clases.Utils;
import com.example.avayatest.Dialogs.DialogConfig;
import com.example.avayatest.Models.Parametros;
import com.example.avayatest.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btn_saldoCredito, btn_saldoAhorro, btn_solicitud, btn_Hablar, btn_exit;
    private TextView tv_inicial;
    /**
     * Text view inicial, aqui se muestran las instrucciones cuando aun no se almacenan los datos del usuario
      */
    private TextView tv_info;
    /***
     * Layout que aloja los botones del menu de opciones
     */
    private LinearLayout ll_general;
    /***
     * Valor entero para el request de seleccion de imagen para el logo
     */
    private Integer PICK_IMAGE_REQUEST = 45220;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setviews();

    }

    /***
     * Metodo para inicializar views
     */
    private void setviews() {
        tv_inicial = findViewById(R.id.tv_inicial);
        ll_general = findViewById(R.id.ll_general);
        tv_info = findViewById(R.id.tv_info);

        btn_saldoCredito = findViewById(R.id.btn_saldoCredito);
        btn_saldoAhorro = findViewById(R.id.btn_saldoAhorro);
        btn_solicitud = findViewById(R.id.btn_solicitud);
        btn_Hablar = findViewById(R.id.btn_Hablar);
        btn_exit = findViewById(R.id.btn_exit);

        btn_exit.setOnClickListener(exit -> {
            MainActivity.this.finish();
        });

        btn_saldoCredito.setOnClickListener(v -> { new SendPostRequestAsync().execute(new Parametros(Utils.DATOS_CLIENTE.getMail(), btn_saldoCredito.getText().toString(), Utils.DATOS_CLIENTE.getCuenta_corriente())); });
        btn_saldoAhorro.setOnClickListener(v -> { new SendPostRequestAsync().execute(new Parametros(Utils.DATOS_CLIENTE.getMail(), btn_saldoAhorro.getText().toString(), Utils.DATOS_CLIENTE.getCuenta_ahorro())); });
        btn_solicitud.setOnClickListener(v -> { new SendPostRequestAsync().execute(new Parametros(Utils.DATOS_CLIENTE.getMail(), btn_solicitud.getText().toString(), Utils.DATOS_CLIENTE.getCuenta_prestamo())); });
        btn_Hablar.setOnClickListener(v -> { new SendPostRequestAsync().execute(new Parametros(Utils.DATOS_CLIENTE.getMail(), btn_Hablar.getText().toString(), Utils.DATOS_CLIENTE.getTelefono())); });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(Utils.DATOS_CLIENTE.isReady()){
            menu.findItem(R.id.action_settings).setVisible(false);
            updateContentMain(true);
        }
        else
        {
            menu.findItem(R.id.action_settings).setVisible(true);
            updateContentMain(false);
        }
        return true;
    }

    /***
     * Update de el layout content_Main, oculta/muestra los botones dependiendo de si ya esta completa lainformacion del cliente.
     * @param b true si ya estan llenos los datos del usuario, false en caso contrario.
     */
    private void updateContentMain(boolean b) {
        if(b){
            ll_general.setVisibility(View.VISIBLE);
            tv_inicial.setVisibility(View.GONE);
            String info = "Mail: " + Utils.DATOS_CLIENTE.getMail();
            info += "\nCuenta Corriente: " + Utils.DATOS_CLIENTE.getCuenta_corriente();
            info += "\nCuenta Ahorro: " + Utils.DATOS_CLIENTE.getCuenta_ahorro();
            info += "\nCuenta Prestamo: " + Utils.DATOS_CLIENTE.getCuenta_prestamo();
            info += "\nCuenta Telefono: " + Utils.DATOS_CLIENTE.getTelefono();

            tv_info.setText(info);
        } else {
            ll_general.setVisibility(View.GONE);
            tv_inicial.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DialogConfig dialog = new DialogConfig(this);
            dialog.setCancelable(false);
            dialog.setOnDismissListener(d -> {
                if(Utils.DATOS_CLIENTE.isReady()){
                    item.setVisible(false);
                    updateContentMain(true);
                } else {
                    item.setVisible(true);
                    updateContentMain(false);
                }
            });
            dialog.show();
            return true;
        } else if(id == R.id.action_changeLogo) {
            //ChangeLogo();
            ChekFilePermission();
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * Metodo para verificar que la aplicacion tiene permisos para acceder al almacenamiento [READ_EXTERNAL_STORAGE]
     */
    private void ChekFilePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        45330);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            ChangeLogo();
        }
    }

    /***
     * Metodo para lanzar el intent de seleccion de imagen para el logo
     */
    private void ChangeLogo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Si el intent recivido es de la seleccion de imagen, se procede a generar el recurso que sera el logo de la aplicacion
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                getSupportActionBar().setLogo(drawable);
                getSupportActionBar().setDisplayUseLogoEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 45330: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    /***
     * Clase para ejecucion de codigo asincrono
     * Es necesario pasar el Objeto PArametros que contiene la informacion del usuario en el metodo Execute()
     */
    private class SendPostRequestAsync extends AsyncTask<Parametros, Void, Integer>{

        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Enviando solicitud...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Parametros... parametros) {
            return ApiTest.requestPostMethod(parametros[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dialog.dismiss();
            if(integer == 200)
                Toast.makeText(getApplicationContext(), "Solicitud enviada correctamente", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Error al enviar la solicitud", Toast.LENGTH_LONG).show();
        }
    }
}
