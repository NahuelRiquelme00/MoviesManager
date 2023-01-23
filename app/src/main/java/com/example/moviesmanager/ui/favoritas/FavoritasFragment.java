package com.example.moviesmanager.ui.favoritas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.moviesmanager.databinding.FragmentFavoritasBinding;
import com.example.moviesmanager.viewmodels.FavoritasViewModel;

public class FavoritasFragment extends Fragment {

    private FragmentFavoritasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritasViewModel favoritasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoritasViewModel.class);

        binding = FragmentFavoritasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFavoritas;
        favoritasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}