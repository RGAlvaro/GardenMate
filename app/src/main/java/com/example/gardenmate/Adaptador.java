package com.example.gardenmate;

import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MacetaViewHolder>
                       implements View.OnClickListener {

    private Context este;
    private List<Maceta> macetas;
    private View.OnClickListener listener;

    public Adaptador(Context ctxt, List<Maceta> listamaceta){
        este=ctxt;
        macetas=listamaceta;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class MacetaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        ImageView imagen;

        public MacetaViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvNombre = (TextView) view.findViewById(R.id.tipo_maceta);
            imagen = (ImageView) view.findViewById(R.id.imagen_maceta);
        }

        public TextView getTextView() {
            return tvNombre;
        }

        public ImageView getImagen() {
            return imagen;
        }
    }


    public Adaptador(List<Maceta> dataSet) {
        macetas = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MacetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.elemento_lista_macetas, viewGroup, false);

        view.setOnClickListener(this);

        return new MacetaViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MacetaViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Maceta maceta = macetas.get(position);
        Context context= viewHolder.getImagen().getContext();

        int id = context.getResources().getIdentifier(maceta.getImagen(), "drawable", context.getPackageName());
        File image= new File (maceta.getImagen());

        Glide.with(este).load(image).into(viewHolder.imagen);


        viewHolder.tvNombre.setText(maceta.getTipo());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return macetas.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener= listener;
    }

}

