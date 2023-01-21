package com.example.moviesmanager.ui.recomendaciones;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesmanager.databinding.FragmentRecomendacionesBinding;
import com.example.moviesmanager.viewmodels.RecomendacionesViewModel;

public class RecomendacionesFragment extends Fragment {

    private FragmentRecomendacionesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecomendacionesViewModel recomendacionesViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RecomendacionesViewModel.class);

        binding = FragmentRecomendacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}