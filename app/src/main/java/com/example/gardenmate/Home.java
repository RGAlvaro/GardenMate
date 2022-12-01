package com.example.gardenmate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    RecyclerView recicler;

    Intent intent;

    String rutaFoto;
    String nuevoNombre;
    String terminal;
    Uri resultUri;

    Adaptador adaptador;
    Button aniadeMaceta,crearMaceta;
    ImageView abrirCamara;
    Bundle intentExtras;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText editarNombre,editarTerminal;

    String usuario,nombreMaceta;
    private static final String URL_MACETAS = "http://gardenmate.xyz/recyclerView.php";
    ActivityResultLauncher<Intent> activityResultLauncher;

    ArrayList<Maceta> listaMacetas;

    Response.Listener<String> listener_respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        intentExtras = new Bundle();
        intent= new Intent(getApplicationContext(), Extraccion.class);

        intentExtras = getIntent().getExtras();
        usuario = intentExtras.getString("usuario");
        listaMacetas= new ArrayList<>();

        recicler = (RecyclerView)findViewById(R.id.recicler);
        recicler.setHasFixedSize(true);
        recicler.setLayoutManager(new LinearLayoutManager(this));

        Volley.newRequestQueue(this).add(crearRequest());

        aniadeMaceta= findViewById(R.id.bt_aniadePlanta);
        aniadeMaceta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CrearNuevoCuadroDialogo();
            }
        });

    }

    public StringRequest crearRequest(){

        listener_respuesta= new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Conversion del string en un array JSON

                    JSONArray array = new JSONArray(response);

                    //Se recorren todos los objetos
                    for (int i = 0; i < array.length(); i++) {

                        //Se obtienen los objetos medicion del array JSON
                        JSONObject macetas = array.getJSONObject(i);

                        //se añade la medición a nuestro arrayList de mediciones
                        listaMacetas.add(new Maceta(
                                macetas.getString("NOMBRE"),
                                macetas.getString("IMAGEN"),
                                usuario,
                                macetas.getString("TERMINAL")
                                ));
                    }

                    adaptador= new Adaptador(Home.this,listaMacetas);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intentExtras.putString("maceta", listaMacetas.get(recicler.getChildAdapterPosition(v)).getTerminal());
                            intent.putExtras(intentExtras);
                            VerMaceta();
                        }
                    });
                    recicler.setAdapter(adaptador);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, URL_MACETAS, listener_respuesta, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("usuario",usuario);
                return params;

            }
        };

        return request;
    }

    public void CrearNuevoCuadroDialogo(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup = getLayoutInflater().inflate(R.layout.activity_popup,null);
        dialogBuilder.setView(popup);
        dialog = dialogBuilder.create();
        dialog.show();
        editarNombre = (EditText) popup.findViewById(R.id.nombrePlanta);
        editarTerminal = (EditText) popup.findViewById(R.id.edit_terminal);
        abrirCamara = (ImageView) popup.findViewById(R.id.abrirCamara);
        crearMaceta = (Button) popup.findViewById(R.id.botonCreaMaceta);
        //dialogBuilder.setView(editarNombre);

        crearMaceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoNombre= editarNombre.getText().toString().trim();
                terminal = editarTerminal.getText().toString().trim();
                if(CrearMaceta()){
                    startActivity(getIntent());
                    finish();
                    //startActivity(new Intent(getApplicationContext(), Home.class));
                }else{

                }

            }
        });
        abrirCamara.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean escoger=true;
                if(escoger==true){
                    if(!ComprobarPermisoCamara()){
                        PedirPermisoCamara();
                        PedirPermisoAlmacenamiento();
                        EscogerImagen();
                    }else EscogerImagen();
                }else {
                    if(!ComprobarPermisoAlmacenamiento()){
                        PedirPermisoAlmacenamiento();
                        EscogerImagen();
                    }else EscogerImagen();
                }
            }
        });

    }

    private void EscogerImagen(){
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);

    }

    private boolean ComprobarPermisoCamara(){
        boolean permiso1= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED;
        boolean permiso2= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
        return permiso1 && permiso2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void PedirPermisoCamara(){
        requestPermissions( new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

    }

    private boolean ComprobarPermisoAlmacenamiento() {
        boolean permiso2= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
        return permiso2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void PedirPermisoAlmacenamiento(){
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }


    public void VerMaceta() {
        startActivity(intent);
    }

    public boolean CrearMaceta() {
        return saveImage(URIaBitmap(this,resultUri));
    }

    private boolean saveImage(Bitmap finalBitmap) {

        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "GardenMate" + File.separator + "photos");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = nuevoNombre + timeStamp + ".jpg";
        rutaFoto= myDir.getAbsolutePath()+"/"+filename;
        Log.i("Image path: ",filename);
        Maceta nueva = new Maceta(nuevoNombre,rutaFoto,usuario,terminal);

        File file = new File(myDir,filename);
        if(file.exists()) file.delete();
        try{
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
            nueva.CrearMaceta(this);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public  Bitmap URIaBitmap(Context context, Uri uri) {
        try {

            InputStream input = context.getContentResolver().openInputStream(uri);
            if (input == null) {
                return null;
            }
            return BitmapFactory.decodeStream(input);
        }
        catch (FileNotFoundException e)
        {

        }
        return null;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Picasso.get().load(resultUri).into(abrirCamara);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
