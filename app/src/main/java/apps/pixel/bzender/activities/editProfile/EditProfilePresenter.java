package apps.pixel.bzender.activities.editProfile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import apps.pixel.bzender.Models.EditProfileResponse;
import apps.pixel.bzender.Models.editProfile.EditProfileModel;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.buildDialog;

class EditProfilePresenter {


    private static final String TAG = "addTinder";
    private final DialogLoader dialogLoader;
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final SharedPreferences sharedPreferences;
    private final EditProfileInterface editProfileInterface;


    EditProfilePresenter(Context mContext, EditProfileInterface editProfileInterface) {
        this.mContext = mContext;

        dialogLoader = new DialogLoader();

        this.editProfileInterface = editProfileInterface;

        mSubscriptions = new CompositeSubscription();

        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

    }

    public void validationEditProfile(String Email, String UserName, String CityId, String base64) {

        if (Validation.validateFields(UserName)) {
            Constant.showErrorDialog(mContext, mContext.getString(R.string.user_name_error));

        } else if (Validation.validateFields(Email)) {
            Constant.showErrorDialog(mContext, mContext.getString(R.string.email_error));

        } else if (Validation.validateFields(CityId)) {
            Constant.showErrorDialog(mContext, mContext.getString(R.string.address_is_empty));
        } else {

            EditProfileModel editProfileModel = new EditProfileModel();

            editProfileModel.setImgUrl(base64);
            editProfileModel.setEmail(Email);
            editProfileModel.setUserName(UserName);
            editProfileModel.setCityId(CityId);

            editProfile(editProfileModel);


        }
    }

    private void editProfile(EditProfileModel editProfileModel) {
        if (Validation.isConnected(mContext)) {
            if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            }


            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .editProfile(editProfileModel)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleError(Throwable throwable) {

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();
        Constant.handleError(mContext, throwable);

    }

    private void handleResponse(EditProfileResponse editProfileModel) {
        Log.d(TAG, "handleResponse: " + editProfileModel.toString());

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.Username, editProfileModel.getUserName());
        edit.putString(Constant.IMAGE_URL, "http://pixelserver-001-site29.ctempurl.com/Content/Images/" + editProfileModel.getImgUrl().trim());
        edit.apply();


        Constant.showSuccessDialogAndSetClassForEditProfile(mContext, mContext.getString(R.string.show_success_for_edit));


        editProfileInterface.getEditProfileData(editProfileModel);
    }

}
