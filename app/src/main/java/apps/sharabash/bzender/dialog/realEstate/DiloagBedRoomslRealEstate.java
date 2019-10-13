package apps.sharabash.bzender.dialog.realEstate;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.ButtonBold;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.activities.real_state.AddRealStateActivity;
import apps.sharabash.bzender.adapters.realEstate.BedRoomsAdapterRV;

import static android.content.Context.MODE_PRIVATE;

public class DiloagBedRoomslRealEstate extends DialogFragment implements BedRoomsAdapterRV.OnCheckChange {
    private RecyclerView mRV;
    private ButtonBold btnOk;
    private List<String> listDAta;
    private BedRoomsAdapterRV bedRoomsADapter;

    private String lang;

    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This removes black background below corners.
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_Dialog);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_bed_rooms, container, true);
        if (getDialog() != null && getDialog().getWindow() != null) {

            getDialog().setCanceledOnTouchOutside(true);
            getDialog().setCancelable(true);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }


        initViews(view);


        return view;
    }

    private void initViews(View view) {

        SharedPreferences mSharedPreferences = getContext().getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        lang = mSharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        listDAta = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            listDAta.add(String.valueOf(i));
        }


        mRV = view.findViewById(R.id.rv_data);
        mRV.setHasFixedSize(false);
        mRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        bedRoomsADapter = new BedRoomsAdapterRV(getContext(), listDAta, this);
        mRV.setAdapter(bedRoomsADapter);
        Constant.runLayoutAnimation(mRV);

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }

    }

    @Override
    public void onClick(String position) {
        AddRealStateActivity.txtBedrooms.setText(position);
        getDialog().dismiss();
    }
}

