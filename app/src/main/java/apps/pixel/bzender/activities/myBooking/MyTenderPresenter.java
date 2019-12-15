package apps.pixel.bzender.activities.myBooking;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import apps.pixel.bzender.Models.my_tenders.MyBookingBody;
import apps.pixel.bzender.Models.my_tenders.MyTendersBody;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyTenderPresenter {

    private static final String TAG = "addTinder";
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final MyTenderInterface myTenderInterface;
    private final SharedPreferences sharedPreferences;
    private final DialogLoader dialogLoader;
    private final DialogLoader dialogLoaderTwo;

    public MyTenderPresenter(Context mContext, MyTenderInterface myTenderInterface) {
        this.mContext = mContext;

        dialogLoader = new DialogLoader();
        dialogLoaderTwo = new DialogLoader();

        mSubscriptions = new CompositeSubscription();
        this.myTenderInterface = myTenderInterface;

        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

    }

    public void getMyooking() {
        if (Validation.isConnected(mContext)) {
            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getMyBooking()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
           // Toast.makeText(mContext, "error happend", Toast.LENGTH_SHORT).show();
        }

    }


    public void getMyTender() {
        if (Validation.isConnected(mContext)) {
            if (dialogLoaderTwo.isAdded()) {
                return;
            } else {
                dialogLoaderTwo.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            }            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getMyTender()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseMyTender, this::handleError));
        } else {
            // Toast.makeText(mContext, "error happend", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleResponseMyTender(List<MyTendersBody> myTendersBody) {
        if (dialogLoaderTwo.isAdded()) {
            dialogLoaderTwo.dismiss();
        }

        Log.d(TAG, "handleResponseMyTender: " + myTendersBody.toString());
        myTenderInterface.getMyTender(myTendersBody);

    }


    private void handleResponse(MyBookingBody myTenderModels) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        Log.d(TAG, "handleResponse: " + myTenderModels.getBookingList().toString());
        myTenderInterface.getMyooking(myTenderModels);
    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        if (dialogLoaderTwo.isAdded()) {
            dialogLoaderTwo.dismiss();
        }

        Constant.handleError(mContext, throwable);

        //hud.dismiss();

    }


}
