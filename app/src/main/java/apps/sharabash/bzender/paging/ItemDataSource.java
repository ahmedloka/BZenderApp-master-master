package apps.sharabash.bzender.paging;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import apps.sharabash.bzender.Models.singleChat.ChatList;
import apps.sharabash.bzender.Models.singleChat.SingleChatResponse;
import apps.sharabash.bzender.Network.NetworkUtil;
import apps.sharabash.bzender.Network.RetrofitClient;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemDataSource extends PageKeyedDataSource<Integer, ChatList> {


    private SharedPreferences sharedPreferences;

    private Context mContext;


    //the size of a page that we want
    public int PAGE_SIZE;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 0;

    private String roomId;


    public ItemDataSource() {
        sharedPreferences = MyApp.getAppContext().getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

        roomId = sharedPreferences.getString(Constant.ROOM_ID, "");
        PAGE_SIZE = sharedPreferences.getInt(Constant.PAGES_COUNT, 0);

        Log.d("DATA_SIZE_PAGE", "ItemDataSource: " + PAGE_SIZE);
        Log.d("DATA_ROOM_ID", "ItemDataSource: " + roomId);
    }

//    @Override
//    public void invalidate() {
//        super.invalidate();
//    }

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ChatList> callback) {
        RetrofitClient.getInstance(sharedPreferences.getString(Constant.UserID, ""))
                .getApi()
                .getChatChatRoomData(roomId, FIRST_PAGE)
                .enqueue(new Callback<SingleChatResponse>() {
                    @Override
                    public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response) {
                        if (response.body() != null) {
                            List<ChatList> chatListList = response.body().getChatList();
                            Collections.reverse(chatListList);
                            callback.onResult(chatListList, null, FIRST_PAGE + 1);
                        }

                    }

                    @Override
                    public void onFailure(Call<SingleChatResponse> call, Throwable t) {

                    }
                });

        Log.d("LOAD", "loadInitial: ");
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ChatList> callback) {
        RetrofitClient.getInstance(sharedPreferences.getString(Constant.UserID, ""))
                .getApi()
                .getChatChatRoomData(roomId, params.key)
                .enqueue(new Callback<SingleChatResponse>() {
                    @Override
                    public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            //passing the loaded data
                            //and the previous page key
                            List<ChatList> chatListList = response.body().getChatList();
                            Collections.reverse(chatListList);
                            callback.onResult(chatListList, adjacentKey);
                        }

                    }

                    @Override
                    public void onFailure(Call<SingleChatResponse> call, Throwable t) {

                    }
                });

        Log.d("LOAD", "loadBefore: ");
    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ChatList> callback) {
        RetrofitClient.getInstance(sharedPreferences.getString(Constant.UserID, ""))
                .getApi()
                .getChatChatRoomData(roomId, params.key)
                .enqueue(new Callback<SingleChatResponse>() {
                    @Override
                    public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response) {
                        if (response.body() != null) {
                            //if the response has next page
                            //incrementing the next page number
                            Integer key = PAGE_SIZE > params.key ? params.key + 1 : null;
                            //passing the loaded data and next page value
                            List<ChatList> chatListList = response.body().getChatList();
                            Collections.reverse(chatListList);
                            callback.onResult(chatListList, params.key + 1);
                        }

                    }

                    @Override
                    public void onFailure(Call<SingleChatResponse> call, Throwable t) {

                    }
                });
        Log.d("LOAD", "loadAfter: ");
    }
}
