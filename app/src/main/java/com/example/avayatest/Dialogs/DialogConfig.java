package com.example.avayatest.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.avayatest.Clases.Utils;
import com.example.avayatest.R;

/***
 * Clase que extende de Dialog, es utilizada para mostrar un dialogo personalizado en el que el usuarop
 * debe ingresar sus datos, necesarios para poder continuar.
 */
public class DialogConfig extends Dialog {

    private EditText et_dialogConfig_mail, et_dialogConfig_corriente,
            et_dialogConfig_ahorro, et_dialogConfig_prestamo, et_dialogConfig_phone;
    private Button
            /***
             *Boton para cerrar el Dialog
              */
            btn_dialog_exit,
    /***
     * Boton para guardar los datos.
     */
            btn_dialog_save;


    public DialogConfig(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_config);
        setViews();
    }

    /***
     * Inicializa las vistas del Dialog
     */
    private void setViews() {
        et_dialogConfig_mail = findViewById(R.id.et_dialogConfig_mail);
        et_dialogConfig_corriente = findViewById(R.id.et_dialogConfig_corriente);
        et_dialogConfig_ahorro =  findViewById(R.id.et_dialogConfig_ahorro);
        et_dialogConfig_prestamo = findViewById(R.id.et_dialogConfig_prestamo);
        et_dialogConfig_phone = findViewById(R.id.et_dialogConfig_phone);
        btn_dialog_exit = findViewById(R.id.btn_dialog_exit);
        btn_dialog_save = findViewById(R.id.btn_dialog_save);

        /***
         *Cerrar Dialogo
         */
        btn_dialog_exit.setOnClickListener(v -> {
            DialogConfig.this.dismiss();
        });

        btn_dialog_save.setOnClickListener( s-> {
            if(et_dialogConfig_mail.getText().toString().equals("") || et_dialogConfig_corriente.getText().toString().equals("") || et_dialogConfig_ahorro.getText().toString().equals("") || et_dialogConfig_phone.getText().toString().equals("")){
                Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            Utils.DATOS_CLIENTE.setMail(et_dialogConfig_mail.getText().toString());
            Utils.DATOS_CLIENTE.setCuenta_corriente(et_dialogConfig_corriente.getText().toString());
            Utils.DATOS_CLIENTE.setCuenta_ahorro(et_dialogConfig_ahorro.getText().toString());
            Utils.DATOS_CLIENTE.setCuenta_prestamo(et_dialogConfig_prestamo.getText().toString());
            Utils.DATOS_CLIENTE.setTelefono(et_dialogConfig_phone.getText().toString());
            DialogConfig.this.dismiss();
        });
    }
}
