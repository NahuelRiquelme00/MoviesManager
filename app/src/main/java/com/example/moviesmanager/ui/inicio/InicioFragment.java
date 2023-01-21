package com.example.moviesmanager.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.adapter.PeliculaAdapterInicio;
import com.example.moviesmanager.databinding.FragmentInicioBinding;
import com.example.moviesmanager.viewmodels.InicioViewModel;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    //private RecyclerView recyclerViewInicioFavoritas;
    //private PeliculaAdapterInicio peliculaAdapterInicioFavoritas;
    //private LinearLayoutManager layoutManagerInicioFavoritas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Cargar Favoritas
        //peliculaAdapterInicioFavoritas = new PeliculaAdapterInicio(,getContext());
        //recyclerViewInicioFavoritas = binding.recyclerViewInicioFavoritas;
        //recyclerViewInicioFavoritas.setHasFixedSize(true);
        //layoutManagerInicioFavoritas = new LinearLayoutManager(getContext());
        //layoutManagerInicioFavoritas.setOrientation(LinearLayoutManager.HORIZONTAL);
        //recyclerViewInicioFavoritas.setLayoutManager(layoutManagerInicioFavoritas);
        //recyclerViewInicioFavoritas.setAdapter(peliculaAdapterInicioFavoritas);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}