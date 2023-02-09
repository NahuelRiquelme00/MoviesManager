package com.example.moviesmanager.ui.review;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.database.AppDataBase;
import com.example.moviesmanager.databinding.FragmentReviewBinding;
import com.example.moviesmanager.models.Review;

public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private Button buttonCancelar;
    private Button buttonGuardar;
    private AppDataBase db;
    private TextView nombrePelicula;
    private String stringPoster;
    private ImageView imageViewPoster;
    private Integer idPelicula;
    private EditText editTextReview;
    private Boolean reviewExistente;
    private String reviewNueva;
    private String reviewActual;
    private Boolean revActualizada;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextReview = binding.editTextReviewReview;

        //Obtengo nombre y poster de la pelicula
        imageViewPoster = binding.imageViewReviewPoster;
        nombrePelicula = binding.textViewReviewNombre;
        nombrePelicula.setText(getArguments().getString("nombre_pelicula"));
        stringPoster = getArguments().getString("poster_path_pelicula");
        Glide.with(getContext()).load(stringPoster).into(imageViewPoster);

        //Obtengo el ID de la pelicula
        idPelicula = getArguments().getInt("id_pelicula");

        //Conexion a la BDD
        try {
            db = AppDataBase.getInstance(getContext().getApplicationContext());
        }catch (Exception ex){
            Toast.makeText(getContext(), "Error al consultar db " + ex, Toast.LENGTH_SHORT).show();
        }

        //Verificar si la pelicula tiene una review para cargarla
        if(db.daoReseña().existsById(idPelicula)){
            reviewExistente = true;
            reviewActual = db.daoReseña().obtenerReview(idPelicula);
            editTextReview.setText(reviewActual);
        } else {
            reviewExistente = false;
            reviewActual = "";
        }

        //Boton cancelar
        buttonCancelar = binding.buttonReviewCancelar;
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cancelar")
                        .setMessage("Esta seguro que desea cancelar?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().onBackPressed();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Boton Guardar
        buttonGuardar = binding.buttonReviewGuardar;
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recupero la review del usuario
                reviewNueva = editTextReview.getText().toString();
                revActualizada = reviewActualizada(reviewActual, reviewNueva);
                if(reviewExistente && revActualizada){
                    //Actualizar review
                    try{
                        db.daoReseña().actualizarReview(idPelicula, reviewNueva);
                        Toast.makeText(getContext(), "Review actualizada", Toast.LENGTH_SHORT).show();
                        reviewActual = reviewNueva;
                    } catch (Exception e){
                        Toast.makeText(getContext(), "Error al cargar review " + e, Toast.LENGTH_SHORT).show();
                    }
                } else if(revActualizada){
                    //Crear review
                    try{
                        db.daoReseña().insertarReview(new Review(idPelicula, reviewNueva));
                        Toast.makeText(getContext(), "Review creada", Toast.LENGTH_SHORT).show();
                        reviewActual = reviewNueva;
                    } catch (Exception e){
                        Toast.makeText(getContext(), "Error al cargar review " + e, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //La review actual no fue modificada
                    Toast.makeText(getContext(), "Debe crear o modificar la review para poder guardarla", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

    private Boolean reviewActualizada(String vieja, String nueva){
        if(vieja.equals(nueva)){
            return false;
        } else{
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


