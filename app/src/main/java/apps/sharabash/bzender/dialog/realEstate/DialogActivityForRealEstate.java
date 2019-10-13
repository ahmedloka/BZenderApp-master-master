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
import apps.sharabash.bzender.adapters.realEstate.ActivityForAdapter;
import apps.sharabash.bzender.adapters.realEstate.BedRoomsAdapterRV;

import static android.content.Context.MODE_PRIVATE;

public class DialogActivityForRealEstate extends DialogFragment implements BedRoomsAdapterRV.OnCheckChange, ActivityForAdapter.OnClickHandler {
    private RecyclerView mRV;
    private ButtonBold btnOk;
    private List<String> listDAta;
    private List<String> listPositions;
    private ActivityForAdapter activityForAdapter;

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

        View view = inflater.inflate(R.layout.dialog_activity_for, container, true);
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

        sharedPreferences = getContext().getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        lang = sharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(v -> getDialog().dismiss());


        listDAta = new ArrayList<>();
        listPositions = new ArrayList<>();
        for (int i = 0; i < Constant.ACTIVITY_FOR.size(); i++) {
            listPositions.add(Constant.ACTIVITY_FOR.get(i).getId());
            if (lang.equals("ar"))
                listDAta.add(Constant.ACTIVITY_FOR.get(i).getArabicName());
            else
                listDAta.add(Constant.ACTIVITY_FOR.get(i).getEnglishName());

        }


        mRV = view.findViewById(R.id.rv_data);
        mRV.setHasFixedSize(false);
        mRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        activityForAdapter = new ActivityForAdapter(getContext(), listDAta, this);
        mRV.setAdapter(activityForAdapter);
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("POSITION_", "onClick: " + listPositions.get(Integer.valueOf(position)));
        String activity = listPositions.get(Integer.valueOf(position)).trim();
        editor.putString(Constant.KEY_ACTIVITY_FOR, activity);
        editor.apply();

        AddRealStateActivity.txtActivityFor.setText(listDAta.get(Integer.valueOf(position)));
        getDialog().dismiss();
    }
}


