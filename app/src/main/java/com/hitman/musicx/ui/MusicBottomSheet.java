package com.hitman.musicx.ui;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hitman.musicx.MainActivity;
import com.hitman.musicx.R;


public class MusicBottomSheet extends BottomSheetDialogFragment {
    BottomSheetBehavior<View> bottomSheetBehavior;
    ImageButton angleDown;
    public MusicBottomSheet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_music_bottom_sheet, container, false);
                BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(requireActivity());

        View bottomSheetView= LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_music_bottom_sheet,null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetBehavior=BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CoordinatorLayout layout= bottomSheetDialog.findViewById(R.id.bottom_sheet_root_layout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        angleDown=(ImageButton) view.findViewById(R.id.angle_down);


        bottomSheetDialog.show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        angleDown.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }
}