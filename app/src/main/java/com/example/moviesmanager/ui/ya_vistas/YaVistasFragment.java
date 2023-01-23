package com.example.moviesmanager.ui.ya_vistas;

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
import com.example.moviesmanager.databinding.FragmentYaVistasBinding;
import com.example.moviesmanager.viewmodels.YaVistasViewModel;

public class YaVistasFragment extends Fragment {

    private FragmentYaVistasBinding binding;

    private Spinner spinnerYaVistas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        YaVistasViewModel yaVistasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(YaVistasViewModel.class);

        binding = FragmentYaVistasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerYaVistas = binding.spinnerYaVistasOrden;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYaVistas.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}