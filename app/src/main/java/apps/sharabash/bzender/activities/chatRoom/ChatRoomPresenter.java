package apps.sharabash.bzender.activities.chatRoom;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import apps.sharabash.bzender.Models.singleChat.ChatList;
import apps.sharabash.bzender.Models.singleChat.SingleChatResponse;
import apps.sharabash.bzender.Network.NetworkUtil;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.Validation;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.sharabash.bzender.Utills.Constant.buildDialog;

public class ChatRoomPresenter {
    private final Context mContext;
    private final CompositeSubscription mSubscriptions;
    private final ChatRoomInterface chatRoomInterface;
    private final SharedPreferences sharedPreferences;
    //private  DialogLoader dialogLoader;
    String roomId;
    int page = 0;
    int totalPages;

    ChatRoomPresenter(Context context, ChatRoomInterface chatRoomInterface) {
        this.mContext = context;
        this.chatRoomInterface = chatRoomInterface;
        mSubscriptions = new CompositeSubscription();
        //   dialogLoader = new DialogLoader();
        sharedPreferences = mContext.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        totalPages = sharedPreferences.getInt(Constant.PAGES_COUNT, 0);
    }

    void getChatRoomData(String roomId, int page) {
        //   dialogLoader.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "10");

        this.roomId = roomId;
        this.page = page;
        if (Validation.isConnected(mContext)) {
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                    .getChatChatRoomData(roomId, page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }
    }


    private void handleResponse(SingleChatResponse singleChatResponse) {

        Log.d("RESPONSE", "handleResponse: " + singleChatResponse.toString());

        List<ChatList> chatLists = singleChatResponse.getChatList();
        Collections.reverse(chatLists);
        chatRoomInterface.getChatRoomData(chatLists);

        if (singleChatResponse.getChatList().size() == 30) {
            page++;
            getChatRoomData(roomId, page);
        } else if (singleChatResponse.getChatList().size() < 30) {
            chatRoomInterface.finishGetDate();
        }
    }
//    private void handleResponse(SingleChatResponse singleChatResponse) {
//        chatRoomInterface.getChatRoomData(singleChatResponse);
//        //getChatRoomData(roomId, 0);
//
////        if (dialogLoader.isAdded()) {
////            dialogLoader.dismiss();
////        }
//        //Log.d("RESPONSE", "handleResponse: " + singleChatResponse.toString());
//        //  if (singleChatResponse.getChatList().size() == 30 && page > 0) {
//        //   Log.d("PAGES_COUNTT", "ChatRoomPresenter: " + String.valueOf(page));
//        //  Toast.makeText(mContext, String.valueOf(page), Toast.LENGTH_SHORT).show();
//        //  --page;
////        } else {
////            chatRoomInterface.finishGetDate();
////          //  Toast.makeText(mContext, "FINISHED", Toast.LENGTH_SHORT).show();
////        }
////           // chatRoomInterface.finishGetDate();
////        } else if (singleChatResponse.getChatList().size() < 30) {
////
////            if (dialogLoader.isAdded()) {
////                dialogLoader.dismiss();
////            }
////            chatRoomInterface.finishGetDate();
////        }
//
//
//    }

    private void handleError(Throwable throwable) {
//        if (dialogLoader.isAdded()) {
//            dialogLoader.dismiss();
//        }

        Log.d("RESPONSE", "handleResponse: " + throwable.getMessage());
        String message = "";
        if (throwable instanceof retrofit2.HttpException) {
            try {
                retrofit2.HttpException error = (retrofit2.HttpException) throwable;
                JSONObject jsonObject = new JSONObject(((HttpException) throwable).response().errorBody().string());
                message = jsonObject.getString("Message");
            } catch (Exception e) {
                message = throwable.getMessage();
            }
            // Constant.getErrorDependingOnResponse(mContext, message);

        }
    }


}
