package apps.pixel.bzender.activities.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import apps.pixel.bzender.Models.profile.ProfileModel;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfilePresenter {

    private static final String TAG = "addTinder";
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;

    private final SharedPreferences sharedPreferences;

    private final ProfileInterface profileInterface;

    private final DialogLoader dialogLoader;

    public ProfilePresenter(Context mContext, ProfileInterface profileInterface) {
        this.mContext = mContext;

        dialogLoader = new DialogLoader();
        this.profileInterface = profileInterface;

        mSubscriptions = new CompositeSubscription();

        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

    }


    public void getProfileData() {
        if (Validation.isConnected(mContext)) {
            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getProfileData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
           // Toast.makeText(mContext, "error happend", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleError(Throwable throwable) {
        Constant.handleError(mContext, throwable);

    }

    private void handleResponse(ProfileModel profileModel) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();
        Log.d(TAG, "handleResponseProfileData: " + profileModel.toString());
        profileInterface.getProfile(profileModel);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        try {
            edit.putString(Constant.IMAGE_URL, "http://pixelserver-001-site29.ctempurl.com/Content/Images/" + profileModel.getImgUrl().trim());
            edit.apply();
        } catch (NullPointerException ignored) {
            edit.putString(Constant.IMAGE_URL, "");
            edit.apply();
        }


    }

}
