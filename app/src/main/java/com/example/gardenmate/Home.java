package com.example.gardenmate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Response;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    TextView tipo;
    ImageView imagen;
    Button aniade_planta;
    private static final String URL_MEDICIONES = "http://www.gardenmate.xyz/extraer.php";
    ActivityResultLauncher<Intent> activityResultLauncher;

    ArrayList<Maceta> macetas;


    Response.Listener<String> listener_respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        aniade_planta= findViewById(R.id.bt_aniadeTerminal);
        macetas= new ArrayList<>();

        aniade_planta= findViewById(R.id.bt_aniadeTerminal);

        Maceta helecho= new Maceta("Helecho", "helecho_1");
        tipo = findViewById(R.id.tipo_maceta);
        tipo.setText(helecho.getTipo());
        int IdRecurso = Home.this.getResources().getIdentifier(helecho.getImagen(),"drawable", Home.this.getPackageName());

        imagen  = findViewById(R.id.imagen_maceta);

        imagen.setImageResource(IdRecurso);

        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CAMERA},101);
        }

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() !=null){
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap)  bundle.get("data");
                    imagen.setImageBitmap(bitmap);
                }
            }
        }

        );



        aniade_planta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    activityResultLauncher.launch(intent);
                }else{
                    Toast.makeText(Home.this, "No hay ninguna aplicación para la camara.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void VerMaceta(View view) {
        startActivity(new Intent(getApplicationContext(), Extraccion.class));
    }

}
