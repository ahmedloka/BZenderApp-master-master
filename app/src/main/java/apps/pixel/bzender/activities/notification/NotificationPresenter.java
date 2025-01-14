package apps.pixel.bzender.activities.notification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import apps.pixel.bzender.Models.notification.NotificationResponse;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.buildDialog;

class NotificationPresenter {


    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final NotificationInterface notificationInterface;
    private final DialogLoader dialogLoader;

    public NotificationPresenter(Context context, NotificationInterface notificationInterface) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        this.notificationInterface = notificationInterface;
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    public void getAllNotifications(int page) {
        if (Validation.isConnected(context)) {

            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "1");
            }

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getNotifications(page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponse(NotificationResponse notificationResponse) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        Log.d("SUCCESS", "handleResponse: " + notificationResponse.toString());
        notificationInterface.getAllNotification(notificationResponse);
    }


    private void handleError(Throwable throwable) {
        Constant.handleError(context, throwable);

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

    }


}
