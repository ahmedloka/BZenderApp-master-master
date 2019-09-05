package apps.sharabash.bzender.activities.chatRoom;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

import apps.sharabash.bzender.Models.message.Message;
import apps.sharabash.bzender.Models.singleChat.ChatList;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyTextViewBold;
import apps.sharabash.bzender.activities.chatAllUsers.ChatListActivity;
import apps.sharabash.bzender.adapters.RecyclerMessagesOneToOneAdapter;
import apps.sharabash.bzender.dialog.DialogLoader;
import apps.sharabash.bzender.services.SignalRService;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ClientTransport;

public class ChatRoom extends AppCompatActivity implements RecyclerMessagesOneToOneAdapter.OnClickHandler, View.OnClickListener, ChatRoomInterface {

    public static final List<Message> messageList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    public static RecyclerView mRecyclerViewOneToOne;
    public static RecyclerMessagesOneToOneAdapter messagesOneToOneAdapter;
    private static Message message;
    private final Context mContext = this;
    ListView messagesView;
    ClientTransport transport;
    HubConnection connection;
    HubProxy hub;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    private MyTextViewBold mTxtTitle;
    private Handler mHandler; // to display Toast message
    private EditText mEdTMsg;
    private String senderId, roomId, statusId;

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
    private ChatRoomPresenter chatRoomPresenter;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_chat_room);

        mTxtTitle = findViewById(R.id.txt_name);
        mTxtTitle.setText(getIntent().getStringExtra(Constant.TENDER_NAME));


        dialogLoader = new DialogLoader();
        dialogLoaderTwo = new DialogLoader();

        sendMsgs = new ArrayList<>();
        receivedMsgs = new ArrayList<>();

        statusId = getIntent().getStringExtra(Constant.STATUS_ID_CHAT);

        sharedPreferences = getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        chatRoomPresenter = new ChatRoomPresenter(this, this);

        ImageView mIVSendMsg = findViewById(R.id.btn_send_message);
        mIVSendMsg.setOnClickListener(this);

        roomId = (getIntent().getStringExtra(Constant.ROOM_ID));
        pagesCount = getIntent().getIntExtra(Constant.PAGES_COUNT, 0);
        Log.d("PAGES_COUNT", "onCreate: " + String.valueOf(pagesCount));
        Log.d("ROOM_ID", "onCreate: " + roomId);
        senderId = getIntent().getStringExtra(Constant.SENDER_ID);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.ROOM_ID_FOR_SIGNAL_R, roomId);
        editor.putString(Constant.STATUS_ID_CHAT, statusId);
        //editor.putInt(Constant.PAGES_COUNT, pagesCount);

        editor.apply();

        chatRoomPresenter.getChatRoomData(roomId, page);


        Log.d("IDRECEIVER", "onCreate: " + roomId);
        Log.d("IDSENDER", "onCreate: " + senderId);


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


        progressBar = findViewById(R.id.progressBar);

        mEdTMsg = findViewById(R.id.et_message);
        mRecyclerViewOneToOne = findViewById(R.id.recycler_view_messages_one_to_one);
        mRecyclerViewOneToOne.setHasFixedSize(true);
        mRecyclerViewOneToOne.setItemAnimator(new SlideInUpAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        mRecyclerViewOneToOne.setLayoutManager(layoutManager);

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


        chatRoomPresenter.getChatRoomData(senderId, page);


    }

    private void getDataDecrea() {
        if (page > 0) {
            page--;

            chatRoomPresenter.getChatRoomData(roomId, page);

            toUp = false;

        }
    }

    private void getDataIncrea() {
        // progressBar.setVisibility(View.GONE);

        if (page < pagesCount) {
            page++;
            chatRoomPresenter.getChatRoomData(roomId, page);
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

        messageList.clear();
        sendMsgs.clear();
        receivedMsgs.clear();

        chatRoomPresenter.getChatRoomData(roomId, page);


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
        if (!statusId.equals("7".trim())) {
            if (mBound) {
                // Call a method from the SignalRService.
                // However, if this call were something that might hang, then this request should
                // occur in a separate thread to avoid slowing down the activity performance.
                if (!mEdTMsg.getText().toString().isEmpty()) {
                    String message = mEdTMsg.getText().toString();
                    //String reciveridentityId, String myID, String message
                    mService.sendMessage(roomId, message);
                    mEdTMsg.getText().clear();
                    messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_SENT, message));
                    mRecyclerViewOneToOne.smoothScrollToPosition(1);


                }

            }
        } else {
            Constant.showErrorDialog(this, getString(R.string.chat_not));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_message) {
            sendMessage();
        }
    }


    @Override
    public void getChatRoomData(List<ChatList> singleChatResponse) {


        Log.d("TEEEST", "getChatRoomData: " + singleChatResponse.get(0).getBody());

        for (int i = 0; i < singleChatResponse.size(); i++) {
            if (singleChatResponse.get(i).getSenderId().equals(senderId)) {
                messageList.add(new Message(Message.MSG_TYPE_RECEIVED, singleChatResponse.get(i).getBody()));
            } else {
                messageList.add(new Message(Message.MSG_TYPE_SENT, singleChatResponse.get(i).getBody()));
            }
            //Collections.reverse(messageList);
        }


    }

    @Override
    public void finishGetDate() {


        messagesOneToOneAdapter = new RecyclerMessagesOneToOneAdapter(messageList, this);
        for (int i = 0; i < sendMsgs.size(); i++) {
            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_SENT, sendMsgs.get(i)));
            Log.d("SIZE_LIST", "finishGetDate:_SEND " + sendMsgs.size());
        }

        for (int i = 0; i < receivedMsgs.size(); i++) {
            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_RECEIVED, receivedMsgs.get(i)));
            Log.d("SIZE_LIST", "finishGetDate:_RECEIVE " + receivedMsgs.size());
        }
        mRecyclerViewOneToOne.setAdapter(messagesOneToOneAdapter);


        Log.d("count", "initViews: " + messagesOneToOneAdapter.getItemCount());
        if (messagesOneToOneAdapter.getItemCount() > 1)
            mRecyclerViewOneToOne.smoothScrollToPosition(1);

    }
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
}
