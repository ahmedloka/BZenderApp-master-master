package apps.sharabash.bzender.activities.webPaymnet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import apps.sharabash.bzender.Network.NetworkUtil;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.Validation;
import apps.sharabash.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.sharabash.bzender.Utills.Constant.buildDialog;

public class PaymentPresenter {

    private final DialogLoader dialogLoader;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;

    public PaymentPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    public void pay(String id) {
        if (!dialogLoader.isAdded()) {
            dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "1");
        }
        if (Validation.validateFields(id)) {
            Constant.showErrorDialog(context, context.getString(R.string.please_select_package));
        } else {
            if (Validation.isConnected(context)) {
                if (!dialogLoader.isAdded())
                    dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                        .openUrl(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));

            } else {
                buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
            }
        }

    }

    private void handleResponse(EmptyResponse emptyResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);


    }
}
