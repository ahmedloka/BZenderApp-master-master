package apps.sharabash.bzender.activities.AddTender;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import apps.sharabash.bzender.Models.AddTenders.AddTinderPojo;
import apps.sharabash.bzender.Models.AddTenders.AddTinderResponse;
import apps.sharabash.bzender.Models.AddTenders.AllCitiesModel;
import apps.sharabash.bzender.Network.NetworkUtil;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.Validation;
import apps.sharabash.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.sharabash.bzender.Utills.Constant.buildDialog;

public class AddTinderPresenter {

    private static final String TAG = "addTinder";
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final AddTinderInterface addTinderInterface;
    private final DialogLoader dialogLoader;
    private final DialogLoader dialogLoaderTwo;
    private final SharedPreferences sharedPreferences;


    public AddTinderPresenter(Context mContext, AddTinderInterface addTinderInterface) {
        this.mContext = mContext;
        mSubscriptions = new CompositeSubscription();
        this.addTinderInterface = addTinderInterface;
        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

        dialogLoader = new DialogLoader();
        dialogLoaderTwo = new DialogLoader();


    }

    public void validationAddTinder(String tenderName, String desc
            , String cityId, String startTime, String endTime,
                                    String categoryID, String address) {

        AddTinderPojo addTinderPojo = new AddTinderPojo();

        addTinderPojo.setTenderName(tenderName);
        addTinderPojo.setTenderDescrioption(desc);
        addTinderPojo.setCityId(cityId);
        addTinderPojo.setStartDateTender(startTime);
        addTinderPojo.setEndDateTender(endTime);
        addTinderPojo.setCategoryID(categoryID);
        addTinderPojo.setAddress(address);

        Log.d(TAG, "validationAddTinder: " + addTinderPojo.toString());

        addTinder(addTinderPojo);


    }

    private void addTinder(AddTinderPojo addTinderPojo) {

        if (Validation.isConnected(mContext)) {

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString("UserID", ""))
                    .addTinder(addTinderPojo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseTender, this::handleError));

        } else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }

    }

    private void handleError(Throwable throwable) {
        Constant.handleError(mContext, throwable);


        if (dialogLoaderTwo.isAdded())
            dialogLoaderTwo.dismiss();


    }

    private void handleResponseTender(AddTinderResponse addTinderResponse) {

        Constant.ADD_TENDER_ID = addTinderResponse.getTenderId();
        addTinderInterface.tenderAddedSuccessfully();
        Log.d(TAG, "YOU_CLICK_ME: " + Constant.ADD_TENDER_ID + " " + addTinderResponse.toString() + " " + addTinderResponse.getTenderId());
//        Log.d(TAG, "TENDER ID: " + addTinderResponse.getTenderId());
//        Log.d(TAG, "handleResponse: + success");
//        Log.d(TAG, "handleResponse: " + SELECTED_TENDER_TYPE);
//
//
//        Intent intent;
//        if (SELECTED_TENDER_TYPE == 0) {
//            intent = new Intent(mContext, FillDataCarActivity.class);
//        } else {
//            intent = new Intent(mContext, FillDataElectricalActivity.class);
//        }
//        mContext.startActivity(intent);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        Animatoo.animateSlideRight(mContext);
    }


    public void getAllCities() {
        if (Validation.isConnected(mContext)) {
            dialogLoaderTwo.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getAllCities()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Toast.makeText(mContext, "error happend", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleResponse(List<AllCitiesModel> allCitiesModel) {
        if (dialogLoaderTwo.isAdded()) {
            dialogLoaderTwo.dismiss();
        }
        dialogLoaderTwo.dismiss();
        Log.d(TAG + "cities", "handleResponse: " + allCitiesModel.get(0).getEnglishName());
        addTinderInterface.getAllCities(allCitiesModel);

    }


}






