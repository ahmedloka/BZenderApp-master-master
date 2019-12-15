package apps.pixel.bzender.dialog.realEstate;

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

import apps.pixel.bzender.Models.realState.Amenities;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.ButtonBold;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.activities.real_state.AddRealStateActivity;
import apps.pixel.bzender.adapters.realEstate.AmenitiesAdapter;

import static android.content.Context.MODE_PRIVATE;

public class DialogAmenities extends DialogFragment implements AmenitiesAdapter.OnCheckChange, AmenitiesAdapter.OnItemCheckListener {
    private RecyclerView mRV;
    private ButtonBold btnOk;
    private List<String> listNames;
    private List<String> poitionsList;
    private AmenitiesAdapter amenitiesAdapter;

    public static List<Amenities> currentSelectedItems;
    private List<String> currentSelectedNames;


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

        View view = inflater.inflate(R.layout.dialog_amenities, container, true);
        if (getDialog() != null && getDialog().getWindow() != null) {

            getDialog().setCanceledOnTouchOutside(true);
            getDialog().setCancelable(true);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }


        initViews(view);


        return view;
    }

    private void initViews(View view) {

        SharedPreferences mSharedPreferences = getContext().getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        lang = mSharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        currentSelectedItems = new ArrayList<>();
        currentSelectedNames = new ArrayList<>();

        btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        listNames = new ArrayList<>();
        poitionsList = new ArrayList<>();

        mRV = view.findViewById(R.id.rv_data);
        mRV.setHasFixedSize(false);
        mRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < Constant.AMENTITIES.size(); i++) {
            poitionsList.add(Constant.AMENTITIES.get(i).getId());
            if (lang.equals("ar")) {
                listNames.add(Constant.AMENTITIES.get(i).getArabicName());
            } else {
                Log.d("DATA_A", "onActivityCreated: " + Constant.AMENTITIES.get(i).getEnglishName());
                listNames.add(Constant.AMENTITIES.get(i).getEnglishName());
            }
        }

        amenitiesAdapter = new AmenitiesAdapter(getContext(), listNames, poitionsList, this, this);
        mRV.setAdapter(amenitiesAdapter);
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
    public void onCheckChange(String position, List<String> selectedIds, List<String> names) {
        Log.d("SELECTED_ITEMS", "onCheckChange: " + position);

    }

    @Override
    public void onItemCheck(String item, String name) {
        currentSelectedItems.add(new Amenities(item));
        currentSelectedNames.add(name);
        for (int i = 0; i < currentSelectedItems.size(); i++) {
            Log.d("DATAAAa_", "onItemCheck: " + currentSelectedNames.get(i));
            AddRealStateActivity.txtAmenities.append(" " + currentSelectedNames.get(i) + " , ");
        }
    }

    @Override
    public void onItemUncheck(String item, String name) {
        currentSelectedItems.remove(item);
        currentSelectedNames.remove(name);
        for (int i = 0; i < currentSelectedItems.size(); i++) {
            Log.d("DATAAAa_", "onItemCheck: " + currentSelectedNames.get(i));
            AddRealStateActivity.txtAmenities.append(" " + currentSelectedNames.get(i) + " , ");
        }
    }
}

