package apps.pixel.bzender.activities.imagesBook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import apps.pixel.bzender.Models.imgs.UploadCarIamge;
import apps.pixel.bzender.Models.imgs.UploadCarRequest;
import apps.pixel.bzender.Models.imgs.UploadElectricalImage;
import apps.pixel.bzender.Models.imgs.UploadElectricalRequest;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.activities.uploadRealEState.UploadRealEstateImageBody;
import apps.pixel.bzender.activities.uploadRealEState.UploadRealEstateResponse;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.buildDialog;

class ImagesPresenter {
    private final DialogLoader dialogLoader;
    private final DialogLoader dialogLoaderTwo;
    private final DialogLoader dialogLoaderThree;

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;


    ImagesPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        dialogLoaderTwo = new DialogLoader();
        dialogLoaderThree = new DialogLoader();
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }


    //REAL_ESTATE
    void uploadRealEstateImage(String id, String base46) {
        UploadRealEstateImageBody uploadRealEstateImageBody = new UploadRealEstateImageBody();
        uploadRealEstateImageBody.setImage(base46);
        uploadRealEstateImageBody.setTenderRealstateBooking_Id(id);

        if (Validation.isConnected(context)) {
            if (!dialogLoaderThree.isAdded()) {
                if (dialogLoaderThree.isAdded()) {
                    return;
                } else {
                    dialogLoaderThree.show(((AppCompatActivity) context).getSupportFragmentManager(), "4");
                }
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .uploadBookingRealEstate(uploadRealEstateImageBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseRealEstate, this::handleError));
        } else {
            buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
        }


    }

    private void handleResponseRealEstate(UploadRealEstateResponse uploadRealEstateResponse) {

        if (dialogLoaderThree.isAdded())
            dialogLoaderThree.dismiss();


    }

    //CAR_________________
    void uploadCarImage(String id, String base46) {
        UploadCarIamge uploadCarIamge = new UploadCarIamge();
        uploadCarIamge.setImage(base46);
        uploadCarIamge.setTenderCarBookingId(id);

        if (Validation.isConnected(context)) {
            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "2");
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .uploadCarImage(uploadCarIamge)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUploadCar, this::handleError));
        } else {
            buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
        }


    }


    private void handleResponseUploadCar(UploadCarRequest uploadCarRequest) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Log.d("RESPONSE", "handleResponseUploadElectrical: " + uploadCarRequest.toString());
    }

    //______________________
    // ELECTRICAL ____________________
    void uploadElectricalImage(String id, String base46) {
        UploadElectricalImage uploadElectricalImage = new UploadElectricalImage();
        uploadElectricalImage.setImage(base46);
        uploadElectricalImage.setTenderElectricalBookingId(id);

        if (Validation.isConnected(context)) {
            if (dialogLoaderTwo.isAdded()) {
                return;
            } else {
                dialogLoaderTwo.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .uploadElectricalImage(uploadElectricalImage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUploadElectrical, this::handleError));
        } else {
            buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
        }


    }

    private void handleResponseUploadElectrical(UploadElectricalRequest uploadElectricalRequest) {
        if (dialogLoaderTwo.isAdded()) {
            dialogLoader.dismiss();
        }

        Log.d("RESPONSE", "handleResponseUploadElectrical: " + uploadElectricalRequest.toString());
    }
    //_________________

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        if (dialogLoaderTwo.isAdded()) {
            dialogLoaderTwo.dismiss();
        }

        if (dialogLoaderThree.isAdded())
            dialogLoaderThree.dismiss();
        Constant.handleError(context, throwable);

    }


}