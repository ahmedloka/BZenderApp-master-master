package apps.pixel.bzender.activities.forget_password;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import apps.pixel.bzender.Models.ForgetPasswordBody;
import apps.pixel.bzender.Models.ForgetPasswordResponse;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.buildDialog;

class ForgetPasswordPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;


    public ForgetPasswordPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    void forgetPassword(String email) {
        if (Validation.validateFields(email)) {
            Constant.showErrorDialog(context, context.getString(R.string.email_error));
        } else {
            if (Validation.isConnected(context)) {

                if (dialogLoader.isAdded()){
                    return;
                }else {
                    dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                }



                ForgetPasswordBody forgetPasswordBody = new ForgetPasswordBody();
                forgetPasswordBody.setEmail(email);

                mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                        .forgetPasword(forgetPasswordBody)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            } else {
                buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
            }


        }

    }

    private void handleResponse(ForgetPasswordResponse forgetPasswordResponse) {
        Log.d("MAIL_SENT", "handleResponse: " + forgetPasswordResponse.toString());

        Constant.showSuccessDialogAndSetClassForIntent(context, context.getString(R.string.response_sexteen));
    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        //  dialogLoaderTwo.dismiss();
        Constant.handleError(context, throwable);

    }

}
