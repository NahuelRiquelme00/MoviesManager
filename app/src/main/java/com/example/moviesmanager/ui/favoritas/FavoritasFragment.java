package com.example.moviesmanager.ui.favoritas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.moviesmanager.R;
import com.example.moviesmanager.databinding.FragmentFavoritasBinding;
import com.example.moviesmanager.viewmodels.FavoritasViewModel;

public class FavoritasFragment extends Fragment {

    private FragmentFavoritasBinding binding;

    private Spinner spinnerFavs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritasViewModel favoritasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoritasViewModel.class);

        binding = FragmentFavoritasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerFavs = binding.spinnerFavsOrden;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFavs.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}