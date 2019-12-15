package apps.pixel.bzender.activities.packages;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import apps.pixel.bzender.Models.ResponsePackages;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.buildDialog;

public class PackagesPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final PackagesInterface packagesInterface;
    private final DialogLoader dialogLoader;


    public PackagesPresenter(Context context, PackagesInterface packagesInterface) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        this.packagesInterface = packagesInterface;
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    public void getAllPackages() {
        if (Validation.isConnected(context)) {

            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "1");
            }

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getPackages()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponse(List<ResponsePackages> responsePackages) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        packagesInterface.getAllPackages(responsePackages);
    }

    private void handleError(Throwable throwable) {
        Constant.handleError(context, throwable);

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

    }
}
