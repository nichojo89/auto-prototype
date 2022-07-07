package com.example.carapihelloworld;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class InspectionReportFragment extends Fragment {
    Spinner mSpinner;
    Spinner mEmergencyBrakeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspection_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
            mSpinner = getView().findViewById(R.id.spinner_languages);
            mEmergencyBrakeSpinner = getView().findViewById(R.id.spinner_emergency_brake);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(), R.array.conditions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);
        mEmergencyBrakeSpinner.setAdapter(adapter);
    }

}