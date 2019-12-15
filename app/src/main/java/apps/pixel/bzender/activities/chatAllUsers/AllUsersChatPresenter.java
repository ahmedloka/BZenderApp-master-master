package apps.pixel.bzender.activities.chatAllUsers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import apps.pixel.bzender.Models.chatList.AllUserMessage;
import apps.pixel.bzender.Network.NetworkUtil;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.Validation;
import apps.pixel.bzender.activities.Complaint.AllUsersChatInterface;
import apps.pixel.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.pixel.bzender.Utills.Constant.buildDialog;

class AllUsersChatPresenter {

    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final AllUsersChatInterface allUsersChatInterface;
    private final SharedPreferences sharedPreferences;
    private final DialogLoader dialogLoader = new DialogLoader();

    public AllUsersChatPresenter(Context context, AllUsersChatInterface allUsersChatInterface) {
        this.mContext = context;
        this.allUsersChatInterface = allUsersChatInterface;
        mSubscriptions = new CompositeSubscription();
        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    public void getAllUsersMessage() {
        if (Validation.isConnected(mContext)) {
            if (dialogLoader.isAdded()){
                return;
            }else {
                dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getAllUserChat()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponse(AllUserMessage allUserMessage) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        allUsersChatInterface.getAllUserChatData(allUserMessage);
    }


    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(mContext, throwable);

    }


}
