package com.example.app_stock_fastapi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.iv).setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            controller.navigate(R.id.action_mainFragment_to_infoFragment, bundle);
        });

        view.findViewById(R.id.searchButton).setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            controller.navigate(R.id.action_mainFragment_to_symbolFragment, bundle);
        });

        view.findViewById(R.id.queryButton).setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            controller.navigate(R.id.action_mainFragment_to_symbolListFragment, bundle);
        });
    }
}