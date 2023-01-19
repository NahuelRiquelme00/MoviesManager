package com.example.moviesmanager.ui.ver_mas_tarde;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesmanager.databinding.FragmentVerMasTardeBinding;
import com.example.moviesmanager.viewmodels.VerMasTardeViewModel;

public class VerMasTardeFragment extends Fragment {

    private FragmentVerMasTardeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VerMasTardeViewModel ver_mas_tardeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VerMasTardeViewModel.class);

        binding = FragmentVerMasTardeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textVerMasTarde;
        ver_mas_tardeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}