package apps.sharabash.bzender.activities.chatRoom;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import apps.sharabash.bzender.Models.message.Message;
import apps.sharabash.bzender.Models.singleChat.ChatList;
import apps.sharabash.bzender.Models.singleChat.SingleChatResponse;
import apps.sharabash.bzender.Network.RetrofitClient;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyTextViewBold;
import apps.sharabash.bzender.Utills.Validation;
import apps.sharabash.bzender.activities.chatAllUsers.ChatListActivity;
import apps.sharabash.bzender.adapters.RecyclerMessagesOneToOneAdapter;
import apps.sharabash.bzender.adapters.chatRoom.PaginateChatRoom;
import apps.sharabash.bzender.dialog.DialogLoader;
import apps.sharabash.bzender.paging.ItemViewModel;
import apps.sharabash.bzender.services.SignalRService;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoom extends AppCompatActivity implements RecyclerMessagesOneToOneAdapter.OnClickHandler, View.OnClickListener, Paginate.Callbacks { //ChatRoomInterface

    public static final List<Message> messageList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    public static RecyclerView mRecyclerViewOneToOne;
    // public static RecyclerMessagesOneToOneAdapter messagesOneToOneAdapter;
    //public static ChatAdapter adapter;
    public static LifecycleOwner lifecycleOwner;
    public static ItemViewModel itemViewModel;
    //  private ChatRoomPresenter chatRoomPresenter;
    public static ProgressBar progressBar;
    public static PaginateChatRoom paginateChatRoom;
    private static Message message;
    private final Context mContext = this;
    ListView messagesView;
    ClientTransport transport;
    HubConnection connection;
    HubProxy hub;
    Boolean isScrolling = false;
    private String catId;
    private int roomId;
    private int mCurrentPage = 0;
    private int TOTAL_PAGES_SIZE;
    private boolean mLoading = false;
    private Handler handler;
    private Paginate paginate;
    private MyTextViewBold mTxtTitle;
    private Handler mHandler; // to display Toast message
    // private String senderId, roomId,
    private EditText mEdTMsg;
    private String statusId, userChatStatus;
    private int pagesCount;
    private boolean toUp;
    private boolean isLoading, isLastPage;
    private SignalRService mService;
    private boolean mBound = false;
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SignalRService, cast the IBinder and get SignalRService instance
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            mService = binder.getService();
            //  Toast.makeText(mContext, "onServiceConnected", Toast.LENGTH_SHORT).show();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // Toast.makeText(mContext, "Disconnected", Toast.LENGTH_SHORT).show();
            mBound = false;
        }
    };
    private List<String> sendMsgs, receivedMsgs;
    private DialogLoader dialogLoader, dialogLoaderTwo;
    private int page = 0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_chat_room);


        progressBar = findViewById(R.id.progressBar);
        lifecycleOwner = ChatRoom.this;

        mTxtTitle = findViewById(R.id.txt_name);
        mTxtTitle.setText(getIntent().getStringExtra(Constant.TENDER_NAME));


        dialogLoader = new DialogLoader();
        dialogLoaderTwo = new DialogLoader();

        sendMsgs = new ArrayList<>();
        receivedMsgs = new ArrayList<>();

        statusId = getIntent().getStringExtra(Constant.STATUS_ID_CHAT);
        userChatStatus = getIntent().getStringExtra(Constant.USER_CHAT_STATUS);

        sharedPreferences = getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);

        TOTAL_PAGES_SIZE = sharedPreferences.getInt(Constant.PAGES_COUNT, 0);
        roomId = sharedPreferences.getInt(Constant.ROOM_ID, 1);

        Log.d("TOTAL_PAGES_SIZE", "onCreate: " + TOTAL_PAGES_SIZE);
        Log.d("roomId_", "onCreate: " + roomId);


        //chatRoomPresenter = new ChatRoomPresenter(this, this);

        ImageView mIVSendMsg = findViewById(R.id.btn_send_message);
        mIVSendMsg.setOnClickListener(this);

//        roomId = (getIntent().getStringExtra(Constant.ROOM_ID));
//        pagesCount = getIntent().getIntExtra(Constant.PAGES_COUNT, 0);
        Log.d("PAGES_COUNT", "onCreate: " + String.valueOf(pagesCount));
        // Log.d("ROOM_ID", "onCreate: " + roomId);
        // senderId = getIntent().getStringExtra(Constant.SENDER_ID);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.putString(Constant.ROOM_ID_FOR_SIGNAL_R, roomId);
        editor.putString(Constant.STATUS_ID_CHAT, statusId);
        //editor.putInt(Constant.PAGES_COUNT, pagesCount);

        editor.apply();

        // chatRoomPresenter.getChatRoomData(roomId, page);


        // Log.d("IDRECEIVER", "onCreate: " + roomId);
        // Log.d("IDSENDER", "onCreate: " + senderId);


        // FOR SIGNALR
        Intent intentSignalR = new Intent();
        intentSignalR.setClass(mContext, SignalRService.class);
        bindService(intentSignalR, mConnection, Context.BIND_AUTO_CREATE);

        // ________________________

        mHandler = new Handler(Looper.getMainLooper());


        AppCompatImageView mImgBack = findViewById(R.id.imageNavigationIcon);
        mImgBack.setOnClickListener(v -> {

            Intent intent = new Intent(this, ChatListActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(this);
            finish();
        });


        mEdTMsg = findViewById(R.id.et_message);
        mRecyclerViewOneToOne = findViewById(R.id.recycler_view_messages_one_to_one);
        ((SimpleItemAnimator) Objects.requireNonNull(mRecyclerViewOneToOne.getItemAnimator())).setSupportsChangeAnimations(true);
        mRecyclerViewOneToOne.setHasFixedSize(false);
        // mRecyclerViewOneToOne.setItemAnimator(new SlideInDownAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        //layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        mRecyclerViewOneToOne.setLayoutManager(layoutManager);
        // mRecyclerViewOneToOne.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> mRecyclerViewOneToOne.scrollToPosition(0));
        handler = new Handler();
        setupPagination();


        //getting our ItemViewModel
        //  itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        //creating the Adapter
        // adapter = new ChatAdapter(MyApp.getAppContext(), messageList);

        //observing the itemPagedList from view model
//        itemViewModel.itemPagedList.observe(this, items -> {
//
//            //in case of any changes
//            //submitting the items to adapter
//
//            //adapter.submitList(items);
//
//
//            adapter = new ChatAdapter(MyApp.getAppContext(), messageList);
//            adapter.notifyDataSetChanged();
//            adapter.submitList(items);
//
//            // Toast.makeText(mContext, String.valueOf(items.size()), Toast.LENGTH_SHORT).show();
////                    Log.d("MESSAGESE", "run: " + items.get(0).getSenderId());
////                    Log.d("MESSAGESE", "run: " + sharedPreferences.getString(Constant.USER_ID_CHAT, ""));
//
////                    List<String> msgs = new ArrayList<>();
////                    for (int i = 0; i < items.size(); i++) {
////                        msgs.add(items.get(i).getBody());
////                    }
////                    Collections.reverse(msgs);
////
////                    for (int i = 0; i < items.size(); i++) {
////                        Log.d("MESSAGESE", "run: " + msgs.get(i));
////                        if (!items.get(i).getSenderId().equals(sharedPreferences.getString(Constant.USER_ID_CHAT, ""))) {
////                            adapter.addItem(new Message(Message.MSG_TYPE_RECEIVED, msgs.get(i)));
////                        } else {
////                            adapter.addItem(new Message(Message.MSG_TYPE_SENT, msgs.get(i)));
////                        }
////
////                    }
//            mRecyclerViewOneToOne.setAdapter(adapter);
//        });
//        mRecyclerViewOneToOne.scrollToPosition(0);


//        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//                {
//                    isScrolling = true;
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
////
////                if (isScrolling) {
////                    isScrolling = false;
////                    if (isFirstItemDisplaying(recyclerView)) {
////                        //so i would call the get data method here
////                        // show loading progress
////                        // progressBar.setVisibility(View.VISIBLE);
////                        getDataIncrea();
////                        Toast.makeText(mContext, "UP++", Toast.LENGTH_SHORT).show();
////                    }
////                }
//
//                currentItems = layoutManager.getChildCount();
//                totalItems = layoutManager.getItemCount();
//                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    Toast.makeText(mContext, "DOWN--", Toast.LENGTH_SHORT).show();
//                    getDataDecrea();
//                    isScrolling = false;
//                }
////                if (islastItemDisplaying(recyclerView)) {
////                    getDataDecrea();
////                    Toast.makeText(mContext, "LOAD MORE--", Toast.LENGTH_SHORT).show();
////                }
//            }
//        };

        //mRecyclerViewOneToOne.addOnScrollListener(recyclerViewOnScrollListener);


        // chatRoomPresenter.getChatRoomData(senderId, page);


    }

    private void setupPagination() {
//        // If RecyclerView was recently bound, unbind
//        if (paginate != null) {
//            paginate.unbind();
//        }
        paginateChatRoom = new PaginateChatRoom(this, messageList);
        mRecyclerViewOneToOne.setItemAnimator(new SlideInUpAnimator());
        mRecyclerViewOneToOne.setAdapter(paginateChatRoom);


        loadData();

        paginate = Paginate.with(mRecyclerViewOneToOne, this)
                .setLoadingTriggerThreshold(30)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(null)
                .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                    @Override
                    public int getSpanSize() {
                        return 3;
                    }
                })
                .build();


    }

    private void getDataDecrea() {
        if (page > 0) {
            page--;
            // chatRoomPresenter.getChatRoomData(roomId, page);
            toUp = false;

        }
    }

    private void getDataIncrea() {
        // progressBar.setVisibility(View.GONE);

        if (page < pagesCount) {
            page++;
            // chatRoomPresenter.getChatRoomData(roomId, page);
            toUp = true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBound = false;
        Intent intent = new Intent();
        intent.setClass(mContext, SignalRService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);


        try {
            itemViewModel.invalidateDataSource();

        } catch (NullPointerException ignored) {

        }


    }

    private boolean isFirstItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            //get the last visible item on screen using the layoutmanager
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            //apply some logic here.
            if (firstVisibleItemPosition == 1)
                return true;
        }

        return false;
    }

//    private boolean islastItemDisplaying(RecyclerView recyclerView) {
//        //check if the adapter item count is greater than 0
//        if (recyclerView.getAdapter().getItemCount() != 0) {
//            //get the last visible item on screen using the layoutmanager
//            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            //apply some logic here.
//            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
//                Toast.makeText(mContext, String.valueOf(recyclerView.getAdapter().getItemCount() - 1), Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return false;
//    }

    @Override
    protected void onStop() {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        super.onStop();
    }

    private void sendMessage() {
        if (Validation.isConnected(this)) {
            if (statusId.equals("4".trim())) {
                if (userChatStatus.equals("true".trim())) {
                    if (mBound) {
                        // Call a method from the SignalRService.
                        // However, if this call were something that might hang, then this request should
                        // occur in a separate thread to avoid slowing down the activity performance.
                        if (!mEdTMsg.getText().toString().isEmpty()) {
                            String message = mEdTMsg.getText().toString();
                            int roomId = sharedPreferences.getInt(Constant.ROOM_ID, 1);

                            //String reciveridentityId, String myID, String message
                            mService.sendMessage(roomId, message);
                            mEdTMsg.getText().clear();

//                            paginateChatRoom.add(new Message(Message.MSG_TYPE_SENT, message));
//                            mRecyclerViewOneToOne.scrollToPosition(0);

                            //                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new AsyncTask<Void, Void, Void>() {
//                                        @Override
//                                        protected Void doInBackground(Void... voids) {
//                                           // itemViewModel.invalidateDataSource();
//                                            return null;
//                                        }
//
//                                        @Override
//                                        protected void onPostExecute(Void aVoid) {
//                                            super.onPostExecute(aVoid);
//                                            mRecyclerViewOneToOne.scrollToPosition(0);
//
//                                        }
//                                    }.execute();

//                                }
//                            }, 500);

//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    itemViewModel.invalidateDataSource();
//
//                                    itemViewModel.itemPagedList.observe(ChatRoom.this, new Observer<PagedList<ChatList>>() {
//                                        @Override
//                                        public void onChanged(@Nullable PagedList<ChatList> chatLists) {
//                                            assert chatLists != null;
//                                            mRecyclerViewOneToOne.smoothScrollToPosition(0);
//                                        }
//                                    });
//                                }
//                            }, 500);

                            //onResume();

                            //setting the adapter
                            //mRecyclerViewOneToOne.setAdapter(adapter);
//
//                    items.add(new ChatList("", "", sharedPreferences.getString(Constant.USER_ID_CHAT, ""), message.trim()));
//                    adapter.notifyDataSetChanged();

//
//                    Intent again = new Intent(this, ChatRoom.class);
//                    again.putExtra(Constant.TENDER_NAME, mTxtTitle.getText().toString());
//                    again.putExtra(Constant.STATUS_ID_CHAT, statusId);
//
//                    startActivity(again);
//                    ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
//                    itemViewModel.itemPagedList.observe(this, new Observer<PagedList<ChatList>>() {
//                        @Override
//                        public void onChanged(@Nullable PagedList<ChatList> items) {
//
//                            //in case of any changes
//                            //submitting the items to adapter
//
//                            chatAdapter.submitList(items);
//                            //mRecyclerViewOneToOne.smoothScrollToPosition(items.size() -1);
//                        }
//                    });
//
//                    //setting the adapter
//                    mRecyclerViewOneToOne.setAdapter(chatAdapter);


                        }

                    }
                } else {
                    Constant.showErrorDialog(this, getString(R.string.chat_nott));
                }
            } else {
                Constant.showErrorDialog(this, getString(R.string.chat_not));
            }
        } else {
            Constant.showErrorDialog(this, getString(R.string.check_network_and));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_message) {
            sendMessage();
        }
    }


//    @Override
//    public void getChatRoomData(List<ChatList> singleChatResponse) {
//
//
//        Log.d("TEEEST", "getChatRoomData: " + singleChatResponse.get(0).getBody());
//
//        for (int i = 0; i < singleChatResponse.size(); i++) {
//            if (singleChatResponse.get(i).getSenderId().equals(senderId)) {
//                messageList.add(new Message(Message.MSG_TYPE_RECEIVED, singleChatResponse.get(i).getBody()));
//            } else {
//                messageList.add(new Message(Message.MSG_TYPE_SENT, singleChatResponse.get(i).getBody()));
//            }
//            //Collections.reverse(messageList);
//        }
//
//
//    }

//    @Override
//    public void finishGetDate() {
//
//
//        messagesOneToOneAdapter = new RecyclerMessagesOneToOneAdapter(messageList, this);
//        for (int i = 0; i < sendMsgs.size(); i++) {
//            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_SENT, sendMsgs.get(i)));
//            Log.d("SIZE_LIST", "finishGetDate:_SEND " + sendMsgs.size());
//        }
//
//        for (int i = 0; i < receivedMsgs.size(); i++) {
//            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_RECEIVED, receivedMsgs.get(i)));
//            Log.d("SIZE_LIST", "finishGetDate:_RECEIVE " + receivedMsgs.size());
//        }
//        mRecyclerViewOneToOne.setAdapter(messagesOneToOneAdapter);
//
//
//        Log.d("count", "initViews: " + messagesOneToOneAdapter.getItemCount());
//        if (messagesOneToOneAdapter.getItemCount() > 1)
//            mRecyclerViewOneToOne.smoothScrollToPosition(messagesOneToOneAdapter.getItemCount() - 1);
//    }
//    @Override
//    public void getChatRoomData(SingleChatResponse singleChatResponse) {
//
//        messageList.clear();
//        sendMsgs.clear();
//        receivedMsgs.clear();
//
//        messagesOneToOneAdapter = new RecyclerMessagesOneToOneAdapter(messageList, this);
//
//        for (int i = 0; i < singleChatResponse.getChatList().size(); i++) {
//            if (!singleChatResponse.getChatList().get(i).getSenderId().equals(senderId)) {
//                receivedMsgs.add(singleChatResponse.getChatList().get(i).getBody());
//                messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_RECEIVED, singleChatResponse.getChatList().get(i).getBody()));
//                Log.d("MSG_RECIEVED", "getChatRoomData: " + singleChatResponse.getChatList().get(i).getBody());
//            } else {
//                sendMsgs.add(singleChatResponse.getChatList().get(i).getBody());
//                messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_SENT, singleChatResponse.getChatList().get(i).getBody()));
//                Log.d("MSG_SEND", "getChatRoomData: " + singleChatResponse.getChatList().get(i).getBody());
//            }
//        }
//        mRecyclerViewOneToOne.setAdapter(messagesOneToOneAdapter);
////
//
//        if (messagesOneToOneAdapter.getItemCount() > 1)
//            mRecyclerViewOneToOne.smoothScrollToPosition(messagesOneToOneAdapter.getItemCount() - 1);
//
//
//    }

//    @Override
//    public void finishGetDate() {
////        progressBar.setVisibility(View.GONE);
//////
//////        sendMsgs.clear();
//////        receivedMsgs.clear();
////
////        Log.d("RESPONSE_DATA", "getChatRoomData: " + receivedMsgs.get(receivedMsgs.size() - 1));
////        Log.d("RESPONSE_DATA", "getChatRoomData: " + sendMsgs.get(sendMsgs.size() - 1));
////
////        //  messageList.clear();
//        messagesOneToOneAdapter = new RecyclerMessagesOneToOneAdapter(messageList, this);
//
//        Log.d("SIZE_LIST", "finishGetDate: " + sendMsgs.size() + "RECEI_SIZE" + receivedMsgs.size());
////
//        for (int i = 0; i < sendMsgs.size(); i++) {
//            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_SENT, sendMsgs.get(i)));
//            Log.d("DATA_SEND", "finishGetDate: " + sendMsgs.get(i));
//        }
////
//        for (int i = 0; i < receivedMsgs.size(); i++) {
//            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_RECEIVED, receivedMsgs.get(i)));
//            Log.d("DATA_RECEI", "finishGetDate: " + receivedMsgs.get(i));
//        }
////
//        mRecyclerViewOneToOne.setAdapter(messagesOneToOneAdapter);
////
////
//        if (messagesOneToOneAdapter.getItemCount() > 1)
//            mRecyclerViewOneToOne.smoothScrollToPosition(messagesOneToOneAdapter.getItemCount() - 1);
//
//////
//////        Log.d("count", "initViews: " + messagesOneToOneAdapter.getItemCount());
//
//    }

    @Override
    public void onClick(int position) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideLeft(this);
    }

    private void loadData() {
        RetrofitClient.getInstance(sharedPreferences.getString(Constant.UserID, ""))
                .getApi()
                .getChatChatRoomData(roomId, mCurrentPage)
                .enqueue(new Callback<SingleChatResponse>() {
                    @Override
                    public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response) {
                        if (response.body() != null) {
                            try {
                                progressBar.setVisibility(View.GONE);
                            } catch (Exception ignored) {

                            }
                            List<ChatList> chatListList = response.body().getChatList();

                            Collections.reverse(chatListList);
                            messageList.clear();
                            for (int i = 0; i < chatListList.size(); i++) {
                                if (chatListList.get(i).getSenderId().equals(sharedPreferences.getString(Constant.USER_ID_CHAT, ""))) {
                                    Log.d("NAMEE_NAME", "onResponse: " + chatListList.get(i).getNickname());
                                    messageList.add(new Message(Message.MSG_TYPE_SENT, chatListList.get(i).getBody(), chatListList.get(i).getNickname()));
                                } else {
                                    messageList.add(new Message(Message.MSG_TYPE_RECEIVED, chatListList.get(i).getBody(), chatListList.get(i).getNickname()));
                                }
                            }
                            paginateChatRoom.notifyDataSetChanged();
                            mLoading = false;
                        }

                    }

                    @Override
                    public void onFailure(Call<SingleChatResponse> call, Throwable t) {
                        Constant.handleError(ChatRoom.this, t);

                        try {
                            ChatRoom.progressBar.setVisibility(View.GONE);
                        } catch (Exception ignored) {
                            Log.d("ERROR", "onFailure: " + t.getCause() + t.getMessage());
                        }


                    }
                });

    }

    @Override
    public void onLoadMore() {
        mLoading = true;

        mCurrentPage++;
        Log.d("mCurrentPage_", "onLoadMore: " + String.valueOf(mCurrentPage));
        loadData();
    }

    @Override
    public boolean isLoading() {
        return mLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        // If all pages are loaded return true
        return TOTAL_PAGES_SIZE == mCurrentPage;
    }
}
