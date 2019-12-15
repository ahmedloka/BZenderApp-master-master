package apps.pixel.bzender.activities.real_state;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.List;

import apps.pixel.bzender.Models.realState.AddTenderRealStateBody;
import apps.pixel.bzender.Models.realState.AddTenderResponse;
import apps.pixel.bzender.Models.realState.Amenities;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.ButtonBook;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.MyTextView;
import apps.pixel.bzender.Utills.MyTextViewBold;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.activities.Home.Home;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RealEstatePresenter {

    private static final String TAG = "addTinder";
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;
    private final SharedPreferences sharedPreferences;

    public RealEstatePresenter(Context context) {
        this.mContext = context;
        mSubscriptions = new CompositeSubscription();
        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

        dialogLoader = new DialogLoader();
    }

    public void addTenderWithoutAmin(String areaRangeMin, String areaRangeMax, String minPrice, String maxPrice, String periodOfRenting,
                                     String specificArea, String desc, String licence, String bedroomsCount, String levelInBuilding,
                                     String location_Id, String activityForId, String needToId, String typeOfPropertyId, String tenderId
    ) {

        AddTenderRealStateBody addTenderRealStateBody = new AddTenderRealStateBody();
        addTenderRealStateBody.setMinSize(areaRangeMin);
        addTenderRealStateBody.setMaxSize(areaRangeMax);
        addTenderRealStateBody.setMinPrice(minPrice);
        addTenderRealStateBody.setMaxPrice(maxPrice);
        addTenderRealStateBody.setPeriodOfRenting(periodOfRenting);
        addTenderRealStateBody.setSpecificArea(specificArea);
        addTenderRealStateBody.setDescription(desc);
        addTenderRealStateBody.setLicence(licence);
        addTenderRealStateBody.setBedroomsCount(bedroomsCount);
        addTenderRealStateBody.setLevelInBuilding(levelInBuilding);
        addTenderRealStateBody.setLocation_Id(location_Id);
        addTenderRealStateBody.setActivityFor_Id(activityForId);
        addTenderRealStateBody.setNeedTo_Id(needToId);
        addTenderRealStateBody.setTypeOfProperty_Id(typeOfPropertyId);
        addTenderRealStateBody.setTender_Id(tenderId);

        addRealStateTender(addTenderRealStateBody);
    }

    public void addTenderWithAmin(String areaRangeMin, String areaRangeMax, String minPrice, String maxPrice, String periodOfRenting,
                                  String specificArea, String desc, String licence, String bedroomsCount, String levelInBuilding,
                                  String location_Id, String activityForId, String needToId, String typeOfPropertyId, String tenderId,
                                  List<Amenities> listAmin
    ) {

        AddTenderRealStateBody addTenderRealStateBody = new AddTenderRealStateBody();
        addTenderRealStateBody.setMinSize(areaRangeMin);
        addTenderRealStateBody.setMaxSize(areaRangeMax);
        addTenderRealStateBody.setMinPrice(minPrice);
        addTenderRealStateBody.setMaxPrice(maxPrice);
        addTenderRealStateBody.setPeriodOfRenting(periodOfRenting);
        addTenderRealStateBody.setSpecificArea(specificArea);
        addTenderRealStateBody.setDescription(desc);
        addTenderRealStateBody.setLicence(licence);
        addTenderRealStateBody.setBedroomsCount(bedroomsCount);
        addTenderRealStateBody.setLevelInBuilding(levelInBuilding);
        addTenderRealStateBody.setLocation_Id(location_Id);
        addTenderRealStateBody.setActivityFor_Id(activityForId);
        addTenderRealStateBody.setNeedTo_Id(needToId);
        addTenderRealStateBody.setTypeOfProperty_Id(typeOfPropertyId);
        addTenderRealStateBody.setTender_Id(tenderId);
        addTenderRealStateBody.setAmenities(listAmin);

        addRealStateTender(addTenderRealStateBody);
    }

    private void addRealStateTender(AddTenderRealStateBody addTenderRealStateBody) {
        if (Validation.isConnected(mContext)) {

            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            }

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString("UserID", ""))
                    .addTenderRealState(addTenderRealStateBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseTender, this::handleError));


        } else {
            Constant.showErrorDialog(mContext, mContext.getString(R.string.check_network_and));
        }
    }

    private void handleResponseTender(AddTenderResponse addTenderResponse) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        showDialogCreate();

        //Log.d(TAG, "handleResponseTender: " + addTenderResponse.getMessage() + "SUCCESS");
    }

    private void handleError(Throwable throwable) {
        Constant.handleError(mContext, throwable);


        if (dialogLoader.isAdded())
            dialogLoader.dismiss();


    }

    private void showDialogCreate() {
        MyTextViewBold txtCongrats;
        MyTextView txtDesc;
        ButtonBook mBtnOk;

        AlertDialog.Builder builder1;
        final AlertDialog dialog1;
        builder1 = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.dialog_add_tender_seccessfully, null);

        mBtnOk = mView.findViewById(R.id.btn_ok);
        txtCongrats = mView.findViewById(R.id.txt_congrats);
        txtDesc = mView.findViewById(R.id.txt_desc);

        txtCongrats.setText(mContext.getText(R.string.congratulations));
        txtDesc.setText(mContext.getText(R.string.complete_add_tinder_sec));


        builder1.setView(mView);
        builder1.setCancelable(true);

        dialog1 = builder1.create();

        Window window = dialog1.getWindow();

        window.setGravity(Gravity.CENTER);

        mBtnOk.setOnClickListener(v -> {
            dialog1.dismiss();
            Intent intent = new Intent(mContext, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
            Animatoo.animateFade(mContext);
        });


        dialog1.show();


    }
}
