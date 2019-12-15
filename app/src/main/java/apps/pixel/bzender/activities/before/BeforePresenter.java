package apps.pixel.bzender.activities.before;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import apps.pixel.bzender.Models.verify.bussniess.VerifyBussniess;
import apps.pixel.bzender.Models.verify.bussniess.VerifyBussniessResponse;
import apps.pixel.bzender.Models.verify.invidual.VerifyInvidual;
import apps.pixel.bzender.Models.verify.invidual.VerifyInvidualResponse;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.activities.AddTender.AddTender;
import apps.pixel.bzender.activities.Tenders.AllTenderActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.ErrorDialog;
import static apps.pixel.bzender.activities.TenderCarDetails.dialogBeforeDetails;
import static apps.pixel.bzender.activities.TenderElectricalDetails.dialogBeforeElectrical;
import static apps.pixel.bzender.activities.before.BeforerActivity.mProgressBar;
import static apps.pixel.bzender.activities.tenderRealEstateDetails.TenderRealEstateDetails.dialogBeforeRealEstate;

public class BeforePresenter {

    private static final String TAG = "addTinder";
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final SharedPreferences sharedPreferences;
    private BeforeInterface beforeInterface;

    private String fromWhere;

    public BeforePresenter(Context mContext, BeforeInterface beforeInterface) {
        this.mContext = mContext;
        mSubscriptions = new CompositeSubscription();
        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        this.beforeInterface = beforeInterface;
        fromWhere = sharedPreferences.getString(Constant.FROM, "");
    }

    //________________________________________________________________________________________________________________________________
    public void validateInvidual(String nationalId) {

        if (sharedPreferences.getString(Constant.INVIDUAL_PERSON, "").equals("true")) {
            Intent intent;
            if (fromWhere.equals("add".trim())) {
                intent = new Intent(mContext, AddTender.class);
                mContext.startActivity(intent);
                Animatoo.animateSlideRight(mContext);
                ((Activity) mContext).finish();

            } else {
                try {
                    if (dialogBeforeRealEstate.isAdded())
                        dialogBeforeRealEstate.dismiss();
                } catch (NullPointerException ignored) {

                }

                try {
                    if (dialogBeforeElectrical.isAdded())
                        dialogBeforeElectrical.dismiss();
                } catch (NullPointerException ignored) {

                }

                try {
                    if (dialogBeforeDetails.isAdded())
                        dialogBeforeDetails.dismiss();
                } catch (NullPointerException ignored) {

                }
            }
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

        mProgressBar.setVisibility(View.VISIBLE);
        if (Validation.isConnected(mContext)) {
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

        mProgressBar.setVisibility(View.GONE);
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

        try {
            if (dialogBeforeRealEstate.isAdded())
                dialogBeforeRealEstate.dismiss();
        } catch (NullPointerException ignored) {

        }

        try {
            if (dialogBeforeElectrical.isAdded())
                dialogBeforeElectrical.dismiss();
        } catch (NullPointerException ignored) {

        }

        try {
            if (dialogBeforeDetails.isAdded())
                dialogBeforeDetails.dismiss();
        } catch (NullPointerException ignored) {

        }


    }

    //______________________
    public void validateBussiness(String taxNum, String commRegNum) {

        if (sharedPreferences.getString(Constant.BUSSINESS_PERSON, "").equals("true")) {

            Intent intent;
            if (fromWhere.equals("add".trim())) {
                intent = new Intent(mContext, AddTender.class);
                mContext.startActivity(intent);
                Animatoo.animateSlideRight(mContext);
                ((Activity) mContext).finish();

            } else {
                try {
                    if (dialogBeforeRealEstate.isAdded())
                        dialogBeforeRealEstate.dismiss();
                } catch (NullPointerException ignored) {

                }

                try {
                    if (dialogBeforeElectrical.isAdded())
                        dialogBeforeElectrical.dismiss();
                } catch (NullPointerException ignored) {

                }

                try {
                    if (dialogBeforeDetails.isAdded())
                        dialogBeforeDetails.dismiss();
                } catch (NullPointerException ignored) {

                }
            }

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

        mProgressBar.setVisibility(View.VISIBLE);
        if (Validation.isConnected(mContext)) {
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
        mProgressBar.setVisibility(View.GONE);
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
        mProgressBar.setVisibility(View.GONE);
        Constant.handleError(mContext, throwable);


    }
}
