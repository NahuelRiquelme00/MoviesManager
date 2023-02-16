package com.example.moviesmanager.views.ui;

import android.app.AlertDialog;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.R;
import com.example.moviesmanager.database.ConsultarDB;
import com.example.moviesmanager.databinding.FragmentReviewBinding;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.Review;
import com.example.moviesmanager.viewmodels.ReviewViewModel;

public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private TextView mTitulo;
    private ImageView mPoster;
    private EditText mReview;
    private Button mGuardar;
    private Button mCancelar;
    private Button mLimpiar;
    private LayoutInflater layoutInflater;
    private View cancelarADView, limpiarADView;
    private Integer id_pelicula;
    private ConsultarDB db;
    private ReviewViewModel reviewViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        reviewViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(ReviewViewModel.class);

        //Binding
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mTitulo = binding.textViewReviewNombre;
        mPoster = binding.imageViewReviewPoster;
        mReview = binding.editTextReviewReview;
        mGuardar = binding.buttonReviewGuardar;
        mCancelar = binding.buttonReviewCancelar;
        mLimpiar = binding.buttonReviewLimpiar;
        layoutInflater = getActivity().getLayoutInflater();

        db = ConsultarDB.getInstance(getContext().getApplicationContext());

        //Obtengo el id de la pelicula y obtengo los detalles
        id_pelicula = getArguments().getInt("id_pelicula");
        obtenerDetalles(id_pelicula);

        ObservarCambios();

        mGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mReview.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Error: no se puede guardar una review vacía", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        Review review = new Review(id_pelicula);
                        review.setReview(mReview.getText().toString());
                        db.daoReseña().insertarReview(review);
                        Toast.makeText(getContext(), "Review guardada", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al guardar review " + ex, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderADCancelar = new AlertDialog.Builder(getContext());
                cancelarADView = layoutInflater.inflate(R.layout.alert_dialog_review_cancelar, null);
                final Button btCancelarNo = (Button) cancelarADView.findViewById(R.id.buttonADReviewCancelarNo);
                final Button btCancelarSi = (Button) cancelarADView.findViewById(R.id.buttonADReviewCancelarSi);
                builderADCancelar.setView(cancelarADView);
                AlertDialog dialogCancelar = builderADCancelar.create();
                dialogCancelar.show();
                btCancelarNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCancelar.cancel();
                    }
                });
                btCancelarSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCancelar.dismiss();
                        getActivity().onBackPressed();
                    }
                });
            }
        });

        mLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mReview.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Error: Ingrese una review primero", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builderADLimpiar = new AlertDialog.Builder(getContext());
                    limpiarADView = layoutInflater.inflate(R.layout.alert_dialog_review_limpiar, null);
                    final Button btLimpiarNo = (Button) limpiarADView.findViewById(R.id.buttonADReviewLimpiarNo);
                    final Button btLimpiarSi = (Button) limpiarADView.findViewById(R.id.buttonADReviewLimpiarSi);
                    builderADLimpiar.setView(limpiarADView);
                    AlertDialog dialogLimpiar = builderADLimpiar.create();
                    dialogLimpiar.show();
                    btLimpiarNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogLimpiar.cancel();
                        }
                    });
                    btLimpiarSi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogLimpiar.dismiss();
                            mReview.setText("");
                        }
                    });
                }
            }
        });

        return root;
    }

    private void ObservarCambios() {
        reviewViewModel.getDetalles().observe((LifecycleOwner) getContext(), new Observer<Pelicula>() {
            @Override
            public void onChanged(Pelicula pelicula) {
                if(pelicula != null){
                    mTitulo.setText(pelicula.getTitle());
                    if(getContext()!= null){
                        Glide.with(getContext()).load(pelicula.getPoster_path()).into(mPoster);
                    }
                    if(db.daoReseña().existsById(id_pelicula)){
                        String review = db.daoReseña().obtenerReview(id_pelicula);
                        mReview.setText(review);
                    }
                }
            }
        });
    }

    private void obtenerDetalles(Integer id_pelicula) {
        reviewViewModel.obtenerDetalles(id_pelicula);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
