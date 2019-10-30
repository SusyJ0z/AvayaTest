package com.example.avayatest.Activities;

import android.os.Bundle;

import com.example.avayatest.Clases.Utils;
import com.example.avayatest.Dialogs.DialogConfig;
import com.example.avayatest.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_saldoCredito, btn_saldoAhorro, btn_solicitud, btn_Hablar, btn_exit;
    private TextView tv_inicial, tv_info;
    private LinearLayout ll_general;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setviews();

    }

    private void setviews() {
        tv_inicial = findViewById(R.id.tv_inicial);
        ll_general = findViewById(R.id.ll_general);
        tv_info = findViewById(R.id.tv_info);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
