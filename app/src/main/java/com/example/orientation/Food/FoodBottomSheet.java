package com.example.orientation.Food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orientation.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FoodBottomSheet extends BottomSheetDialogFragment {
    String description;
    TextView desc;
    public FoodBottomSheet(String descrip) {
        description = descrip;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet,container,false);
        desc = (TextView) view.findViewById(R.id.descr);
        desc.setText(description);
        return view;
    }
}
