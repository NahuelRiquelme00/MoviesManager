package com.example.moviesmanager.ui.ver_mas_tarde;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moviesmanager.R;
import com.example.moviesmanager.databinding.FragmentVerMasTardeBinding;
import com.example.moviesmanager.viewmodels.VerMasTardeViewModel;

public class VerMasTardeFragment extends Fragment {

    private FragmentVerMasTardeBinding binding;

    private Spinner spinnerVerMasTarde;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VerMasTardeViewModel ver_mas_tardeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VerMasTardeViewModel.class);

        binding = FragmentVerMasTardeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerVerMasTarde = binding.spinnerVerMasTardeOrden;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVerMasTarde.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}