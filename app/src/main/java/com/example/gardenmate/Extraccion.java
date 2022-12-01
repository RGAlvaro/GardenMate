package com.example.gardenmate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.CheckBox;
import android.widget.RadioButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Extraccion extends AppCompatActivity {


    CheckBox check_hum, check_tem, check_tie;
    RadioButton radio_sem,radio_mes,radio_año;
    LineChart lineChart;

    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String URL_MEDICIONES = "http://www.gardenmate.xyz/extraer.php";

    ArrayList<Medicion> mediciones;


    Response.Listener<String> listener_respuesta;

    Bundle intentExtras;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_extraccion);
        lineChart = findViewById(R.id.grafica);
        mediciones= new ArrayList<>();

        intentExtras = new Bundle();
        //intent= getIntent();

        intentExtras = getIntent().getExtras();

        radio_sem= findViewById(R.id.radio_sem);
        radio_mes= findViewById(R.id.radio_mes);
        radio_año= findViewById(R.id.radio_año);

        check_hum= findViewById(R.id.checkHumedad);
        check_tem= findViewById(R.id.checkTemperatura);
        check_tie= findViewById(R.id.checkTierra);



        listener_respuesta= new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Conversion del string en un array JSON

                    JSONArray array = new JSONArray(response);



                    //Se recorren todos los objetos
                    for (int i = 0; i < array.length(); i++) {

                        //Se obtienen los objetos medicion del array JSON
                        JSONObject medicion = array.getJSONObject(i);

                        //se añade la medición a nuestro arrayList de mediciones
                        mediciones.add(new Medicion(
                                medicion.getDouble("HUMEDAD"),
                                medicion.getDouble("TEMPERATURA"),
                                medicion.getDouble("TIERRA"),
                                formato.parse(medicion.getString("FECHA"))
                        ));
                    }


                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MEDICIONES,listener_respuesta,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("terminal",intentExtras.getString("maceta"));
                return params;

            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void CargaMediciones(View view){

        CrearGrafica();

    }

    public ArrayList<Medicion> FechaDespues (ArrayList<Medicion> lista,int limite){
        Date d=new Date();
        d.setTime(System.currentTimeMillis());
        Calendar c= Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE,limite*-1);


        ArrayList <Medicion> aux= new ArrayList<>();

        for(Medicion m: lista){
            if(m.getFecha().after(c.getTime())){
                aux.add(m);
            }
        }


        return aux;
    }

    public int LimiteTiempo(){
        if(radio_año.isChecked()){
            return 365;
        }else
        if(radio_mes.isChecked()){
            return 30;
        }else
        if(radio_sem.isChecked()){
            return 7;
        }else{
            Toast.makeText(getApplicationContext(),"Por favor, seleccione un periodo de tiempo para mostrar.",Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    public void CrearGrafica(){

        ArrayList<Medicion> lista_limite_tiempo= FechaDespues(mediciones,LimiteTiempo());

        LineDataSet set_humedad = new LineDataSet(Humedad(lista_limite_tiempo), "humedad %");
        LineDataSet set_temperatura = new LineDataSet(Temperatura(lista_limite_tiempo), "temperatura ºC");
        LineDataSet set_tierra = new LineDataSet(Tierra(lista_limite_tiempo), "tierra %");


        set_humedad.setColor(Color.parseColor("#2fb5d6"));
        set_humedad.setCircleColor(Color.parseColor("#2fb5d6"));
        set_humedad.setLineWidth(0.9f);

        set_temperatura.setColor(Color.parseColor("#DE3520"));
        set_temperatura.setCircleColor(Color.parseColor("#DE3520"));
        set_humedad.setLineWidth(0.9f);

        set_tierra.setColor(Color.parseColor("#76421A"));
        set_tierra.setCircleColor(Color.parseColor("#76421A"));
        set_tierra.setLineWidth(0.9f);


        /*set_humedad.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set_temperatura.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set_tierra.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        YAxis y ;
        LimitLine ll1 = new LimitLine(90f, "Demasiada humedad");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        */

        
        ArrayList<ILineDataSet> datasets = new ArrayList<>();

        if(check_hum.isChecked()) {
            //lineChart.getXAxis().(ll1);
            datasets.add(set_humedad);
        }

        if(check_tem.isChecked()) {

            datasets.add(set_temperatura);
        }

        if(check_tie.isChecked()) {

            datasets.add(set_tierra);
        }
        if(!check_hum.isChecked() && !check_tie.isChecked() && !check_tem.isChecked()){
            Toast.makeText(getApplicationContext(),"Por favor, seleccione algún tipo de medición para mostrar.",Toast.LENGTH_LONG).show();
        }

        Description desc= new Description();
        desc.setEnabled(false);
        lineChart.setDescription(desc);

        LineData datos_grafica = new LineData(datasets);
        lineChart.setData(datos_grafica);
        lineChart.invalidate();
        lineChart.getXAxis().setDrawLabels(false);

    }

    public ArrayList<Entry> Humedad(ArrayList<Medicion> lista) {
        Timestamp tiempo;
        ArrayList<Entry> aux = new ArrayList<Entry>();

        for (Medicion a : lista) {
            tiempo = new Timestamp(a.getFecha().getTime());
            aux.add(new Entry(tiempo.getTime(), (float) a.getHumedad()));
        }
        if (aux.isEmpty()){
            Toast.makeText(getApplicationContext(), "No hay datos de Humedad para mostrar.",Toast.LENGTH_LONG);
        }
        return aux;
    }

    public ArrayList<Entry> Temperatura(ArrayList<Medicion> lista) {
        Timestamp tiempo;
        ArrayList<Entry> aux = new ArrayList<Entry>();

        for (Medicion a : lista) {
            tiempo = new Timestamp(a.getFecha().getTime());
            aux.add(new Entry(tiempo.getTime(), (float) a.getTemperatura()));
        }
        if(aux.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No hay datos de Temperatura para mostrar.", Toast.LENGTH_LONG);
        }
        return aux;
    }

    public ArrayList<Entry> Tierra(ArrayList<Medicion> lista) {
        Timestamp tiempo;
        ArrayList<Entry> aux = new ArrayList<Entry>();

        for (Medicion a : lista) {
            tiempo = new Timestamp(a.getFecha().getTime());
            aux.add(new Entry(tiempo.getTime(), (float) a.getTierra()));
        }
        if(aux.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No hay datos de Humedad de tierra para mostrar.", Toast.LENGTH_LONG);
        }
        return aux;
    }


    public SimpleDateFormat getFormato() {
        return formato;
    }

    public void setFormato(SimpleDateFormat formato) {
        this.formato = formato;
    }

    public static String getUrlMediciones() {
        return URL_MEDICIONES;
    }

    public ArrayList<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(ArrayList<Medicion> mediciones) {
        this.mediciones = mediciones;
    }
}

