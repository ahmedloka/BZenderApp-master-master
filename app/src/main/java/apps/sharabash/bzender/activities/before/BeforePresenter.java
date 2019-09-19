package apps.sharabash.bzender.activities.before;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONObject;

import apps.sharabash.bzender.Models.verify.bussniess.VerifyBussniess;
import apps.sharabash.bzender.Models.verify.bussniess.VerifyBussniessResponse;
import apps.sharabash.bzender.Models.verify.invidual.VerifyInvidual;
import apps.sharabash.bzender.Models.verify.invidual.VerifyInvidualResponse;
import apps.sharabash.bzender.Network.NetworkUtil;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.Validation;
import apps.sharabash.bzender.activities.AddTender.AddTender;
import apps.sharabash.bzender.activities.Tenders.AllTenderActivity;
import apps.sharabash.bzender.dialog.DialogLoader;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.sharabash.bzender.Utills.Constant.ErrorDialog;

public class BeforePresenter {

    private static final String TAG = "addTinder";
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;
    private final DialogLoader dialogLoaderTwo;
    private final SharedPreferences sharedPreferences;
    private BeforeInterface beforeInterface;

    private String fromWhere;

    public BeforePresenter(Context mContext, BeforeInterface beforeInterface) {
        this.mContext = mContext;
        mSubscriptions = new CompositeSubscription();
        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        this.beforeInterface = beforeInterface;
        dialogLoader = new DialogLoader();
        dialogLoaderTwo = new DialogLoader();

        fromWhere = sharedPreferences.getString(Constant.FROM, "");
    }

    //______________________
    public void validateInvidual(String nationalId) {


        if (sharedPreferences.getString(Constant.INVIDUAL_PERSON, "").equals("true")) {
            Intent intent;
            if (fromWhere.equals("add".trim())) {
                intent = new Intent(mContext, AddTender.class);
            } else {
                intent = new Intent(mContext, AllTenderActivity.class);
            }
            mContext.startActivity(intent);
            Animatoo.animateSlideRight(mContext);
            ((Activity) mContext).finish();

        } else {
            if (Validation.validateFields(nationalId)) {
                ErrorDialog((Activity) mContext, mContext.getString(R.string.national_id_required));
            } else {
                VerifyInvidual verifyInvidual = new VerifyInvidual();
                verifyInvidual.setNationalID(nationalId);

                verifyInvidualPerson(verifyInvidual);
            }
        }
    }

    private void verifyInvidualPerson(VerifyInvidual verifyInvidual) {
        if (!dialogLoader.isAdded()) {
            dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
        }

        if (Validation.isConnected(mContext)) {
            dialogLoaderTwo.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .verifyInvidual(verifyInvidual)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseInvidual, this::handleError));
        } else {
            Constant.showErrorDialog(mContext, mContext.getString(R.string.check_network_and));
        }

    }

    private void handleResponseInvidual(VerifyInvidualResponse verifyInvidualResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }


        beforeInterface.verfiedInvidualSuccessful(verifyInvidualResponse);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.INVIDUAL_PERSON, "true");
        editor.apply();

        Intent intent;
        if (fromWhere.equals("add".trim())) {
            Constant.showSuccessVERFIED(mContext, mContext.getString(R.string.verfied_successfullty), AddTender.class);
        } else {
            Constant.showSuccessVERFIED(mContext, mContext.getString(R.string.verfied_successfullty), AllTenderActivity.class);
        }


    }

    //______________________
    public void validateBussiness(String taxNum, String commRegNum) {

        if (sharedPreferences.getString(Constant.BUSSINESS_PERSON, "").equals("true")) {

            Intent intent;
            if (fromWhere.equals("add".trim())) {
                intent = new Intent(mContext, AddTender.class);
            } else {
                intent = new Intent(mContext, AllTenderActivity.class);
            }
            mContext.startActivity(intent);
            Animatoo.animateSlideRight(mContext);
            ((Activity) mContext).finish();

        } else {

            if (Validation.validateFields(taxNum)) {
                ErrorDialog((Activity) mContext, mContext.getString(R.string.tax_num_required));
            } else if (Validation.validateFields(commRegNum)) {
                ErrorDialog((Activity) mContext, mContext.getString(R.string.commercial_record_required));
            } else {
                VerifyBussniess verifyBussniess = new VerifyBussniess();
                verifyBussniess.setCommRegNum(commRegNum);
                verifyBussniess.setTaxNum(taxNum);

                verifyBussniessPerson(verifyBussniess);
            }
        }
    }

    private void verifyBussniessPerson(VerifyBussniess verifyBussniess) {
        if (!dialogLoaderTwo.isAdded()) {
            dialogLoaderTwo.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
        }

        if (Validation.isConnected(mContext)) {
            dialogLoaderTwo.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .verifyBussniess(verifyBussniess)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseBussniess, this::handleError));
        } else {
            Constant.showErrorDialog(mContext, mContext.getString(R.string.check_network_and));
        }

    }

    private void handleResponseBussniess(VerifyBussniessResponse verifyBussniessResponse) {
        if (dialogLoaderTwo.isAdded()) {
            dialogLoaderTwo.dismiss();
        }
        beforeInterface.verfiedBussniessSuccessful(verifyBussniessResponse);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.BUSSINESS_PERSON, "true");
        editor.apply();
        Intent intent;
        if (fromWhere.equals("add".trim())) {
            Constant.showSuccessVERFIED(mContext, mContext.getString(R.string.verfied_successfullty), AddTender.class);
        } else {
            Constant.showSuccessVERFIED(mContext, mContext.getString(R.string.verfied_successfullty), AllTenderActivity.class);

        }

    }

    private void handleError(Throwable throwable) {
        String message = "";
        if (throwable instanceof retrofit2.HttpException) {
            try {
                retrofit2.HttpException error = (retrofit2.HttpException) throwable;
                JSONObject jsonObject = new JSONObject(((HttpException) throwable).response().errorBody().string());
                message = jsonObject.getString("Message");
            } catch (Exception e) {
                message = throwable.getMessage();
            }
            Constant.getErrorDependingOnResponse(mContext, message);

        }

        if (dialogLoaderTwo.isAdded())
            dialogLoaderTwo.dismiss();

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

    }
}
