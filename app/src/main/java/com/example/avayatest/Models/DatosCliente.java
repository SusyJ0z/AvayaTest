package com.example.avayatest.Models;

public class DatosCliente {

    public String getMail() {        return mail;    }
    public void setMail(String mail) {        this.mail = mail;    }
    public String getCuenta_corriente() {        return cuenta_corriente;    }
    public void setCuenta_corriente(String cuenta_corriente) {        this.cuenta_corriente = cuenta_corriente;    }
    public String getCuenta_ahorro() {        return cuenta_ahorro;    }
    public void setCuenta_ahorro(String cuenta_ahorro) {        this.cuenta_ahorro = cuenta_ahorro;    }
    public String getTelefono() {        return telefono;    }
    public void setTelefono(String telefono) {        this.telefono = telefono;    }
    public String getCuenta_prestamo() {        return cuenta_prestamo;    }
    public void setCuenta_prestamo(String cuenta_prestamo) {        this.cuenta_prestamo = cuenta_prestamo;    }

    /***
     * Metodo para verificar que los campos de la clase esten llenos
     * @return true si ninguna propiedad es vacia, false en otro caso.
     */
    public boolean isReady(){
        if(mail.equals("") || cuenta_ahorro.equals("") || cuenta_corriente.equals("") || cuenta_prestamo.equals("") || telefono.equals(""))
            return false;
        else
            return true;
    }

    private String mail = "";
    private String cuenta_corriente ="";
    private String cuenta_ahorro ="";
    private String telefono ="";
    private String cuenta_prestamo = "";
}
