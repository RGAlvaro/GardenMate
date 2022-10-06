package com.example.gardenmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<ImagenViewHolder>{

    private Context contexto;
    private List<Maceta> macetas;

    public Adaptador(Context contexto, List<Maceta> macetas) {
        this.contexto = contexto;
        this.macetas = macetas;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_macetas,parent,false);
        return new ImagenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class ImagenViewHolder extends RecyclerView.ViewHolder{

    TextView tipo_maceta;
    ImageView imagen_maceta;

    public ImagenViewHolder(@NonNull View itemView) {
        super(itemView);
        imagen_maceta = itemView.findViewById(R.id.imagen_maceta);
        tipo_maceta = itemView.findViewById(R.id.tipo_maceta);

    }
}
