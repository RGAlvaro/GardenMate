package com.example.gardenmate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Maceta {
    private String tipo,imagen,usuario,terminal;

    public Maceta(String tipo, String imagen, String usuario, String terminal) {
        this.tipo = tipo;
        this.imagen = imagen;
        this.usuario = usuario;
        this.terminal = terminal;
    }

    public void CrearMaceta(Context context) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("cargando...");


        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "http://gardenmate.xyz/subirMaceta.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("Maceta Creada")) {

                            Toast.makeText(context, "Maceta Creada", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Toast.makeText(context, "error en la creacion de maceta", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("usuario",usuario);
                params.put("nombre",tipo);
                params.put("imagen",imagen);
                params.put("terminal",terminal);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    public String getTipo() { return tipo;}

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getTerminal() {return terminal;}

    public void setTerminal(String terminal) {this.terminal = terminal;}
}
