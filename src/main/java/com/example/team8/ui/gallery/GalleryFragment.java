package com.example.team8.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.team8.DetalleProductoActivity;
import com.example.team8.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GalleryFragment extends Fragment {

    private TextView tev_galeria;
    private Spinner spn_categorias;

    private RecyclerView rev_productos;
    private RecyclerView.Adapter mAdapter;

    private String productos = "[{\"nombre\":\"Guantes\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"imagen\":\"https://w7.pngwing.com/pngs/477/33/png-transparent-goalkeeper-guante-de-guardameta-glove-nike-football-goalkeeper-gloves-orange-adidas-goalkeeper.png\",\"sucursal\":[{\"nombre\":\"Sucursal A\",\"direccion\":\"Diección A\",\"encargado\":{\"nombre\":\"Encargado A\"}},{\"nombre\":\"Sucursal B\",\"direccion\":\"Diección B\",\"encargado\":{\"nombre\":\"Encargado B\"}}]},{\"nombre\":\"Guayos\",\"categoria\":\"Futbol\",\"precio\":120000,\"enstock\":false,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_818984-MCO44044382745_112020-O.jpg\",\"sucursal\":[{\"nombre\":\"Sucursal C\",\"direccion\":\"Diección C\",\"encargado\":{\"nombre\":\"Encargado C\"}},{\"nombre\":\"Sucursal D\",\"direccion\":\"Diección D\",\"encargado\":{\"nombre\":\"Encargado D\"}}]},{\"nombre\":\"Balón\",\"categoria\":\"Futbol\",\"precio\":10000,\"enstock\":true,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_675798-MCO43940869595_102020-V.jpg\",\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Casco\",\"categoria\":\"Futbol\",\"precio\":650000,\"enstock\":true,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_995259-MCO40463347072_012020-O.jpg\",\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Medias\",\"categoria\":\"Futbol\",\"precio\":4000,\"enstock\":true,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_628990-MLA46105649413_052021-W.jpg\",\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Canilleras\",\"categoria\":\"Futbol\",\"precio\":6000,\"enstock\":true,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_841158-MCO44011563751_112020-V.jpg\",\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]}]";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        tev_galeria = root.findViewById(R.id.tev_galeria);
        spn_categorias = root.findViewById(R.id.spn_categorias);

        String[] categorias = new String[]{"Beisbol", "Atletismo", "Karate", "Salto triple"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                categorias);


        spn_categorias.setAdapter(adaptador);

        spn_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoria = spn_categorias.getSelectedItem().toString();
                tev_galeria.setText(categoria);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //tev_galeria.setText("Grupo 10");

        rev_productos = root.findViewById(R.id.rev_productos);
        rev_productos.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rev_productos.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        try {
            JSONArray jsonProductos = new JSONArray(productos);

            //mAdapter = new ProductosAdapter(jsonProductos, getActivity());

            //rev_productos.setAdapter(mAdapter);


            JSONObject producto0 = jsonProductos.getJSONObject(0);

            String nombre = producto0.getString("nombre");

            JSONArray sucursal = producto0.getJSONArray("sucursal");

            JSONObject sucursal1 = sucursal.getJSONObject(1);

            String nombreSucursal = sucursal1.getString("nombre");

            Toast.makeText(getActivity(), "NOMBRE: "+ nombreSucursal, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            JSONArray productos = new JSONArray();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());

                                String nombre = document.getData().get("nombre").toString();
                                String categoria = document.getData().get("categoria").toString();
                                int precio = Integer.parseInt(document.getData().get("precio").toString());
                                boolean enstock = Boolean.parseBoolean(document.getData().get("enstock").toString());
                                String imagen = document.getData().get("imagen").toString();

                                JSONObject producto = new JSONObject();
                                try {
                                    producto.put("nombre", nombre);
                                    producto.put("categoria", categoria);
                                    producto.put("precio", precio);
                                    producto.put("enstock", enstock);
                                    producto.put("imagen", imagen);

                                    productos.put(producto);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            mAdapter = new ProductosAdapter(productos, getActivity());

                            rev_productos.setAdapter(mAdapter);


                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return root;
    }
}

class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    private JSONArray productos;
    private Activity miActividad;

    public ProductosAdapter(JSONArray productos, Activity miActividad) {
        this.productos = productos;
        this.miActividad = miActividad;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_productos, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            Log.e("POSICION", "POS: " + position);
            String nombre = productos.getJSONObject(position).getString("nombre");
            String categoria = productos.getJSONObject(position).getString("categoria");
            int precio = productos.getJSONObject(position).getInt("precio");
            String imagen = productos.getJSONObject(position).getString("imagen");

            holder.tev_item_name.setText(nombre);
            holder.tev_item_categoria.setText(categoria);
            holder.tev_item_precio.setText("$" + precio);


            if (imagen.equals("picasso")) {

                holder.imv_item_producto.setImageResource(miActividad.getResources().getIdentifier(imagen, "drawable", miActividad.getPackageName()));

                //holder.imv_item_producto.setImageDrawable(miActividad.getDrawable(R.drawable.picasso));
            } else {
                Glide.with(miActividad)
                        .load(imagen)
                        .placeholder(new ColorDrawable(Color.BLACK))
                        .into(holder.imv_item_producto);
            }

            holder.imv_item_producto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Log.e("PRODUCTOS", productos.getJSONObject(position).toString());
                        Intent intent = new Intent(miActividad, DetalleProductoActivity.class);

                        intent.putExtra("producto", productos.getJSONObject(position).toString());

                        miActividad.startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.btn_item_favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Log.e("PRODUCTO_FAVORITE", productos.getJSONObject(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });




        } catch (JSONException e) {
            holder.tev_item_name.setText("error");
        }

    }

    @Override
    public int getItemCount() {
        Log.e("CANTIDAD_PRODUCTOS", "" + this.productos.length());
        return this.productos.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tev_item_name;
        private TextView tev_item_categoria;
        private TextView tev_item_precio;
        private ImageView imv_item_producto;
        private Button btn_item_favorito;
        public ViewHolder(View v) {
            super(v);
            tev_item_name = v.findViewById(R.id.tev_item_name);
            tev_item_categoria = v.findViewById(R.id.tev_item_categoria);
            tev_item_precio = v.findViewById(R.id.tev_item_precio);
            imv_item_producto = v.findViewById(R.id.imv_item_producto);
            btn_item_favorito = v.findViewById(R.id.btn_item_favorito);

        }
    }
}