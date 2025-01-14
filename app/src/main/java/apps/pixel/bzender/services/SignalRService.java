package apps.pixel.bzender.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import apps.pixel.bzender.Models.message.Message;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.adapters.RecyclerMessagesOneToOneAdapter;
import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

import static apps.pixel.bzender.activities.chatRoom.ChatRoom.mRecyclerViewOneToOne;
import static apps.pixel.bzender.activities.chatRoom.ChatRoom.paginateChatRoom;
//import static apps.sharabash.bzender.activities.chatRoom.ChatRoom.messagesOneToOneAdapter;

public class SignalRService extends Service implements RecyclerMessagesOneToOneAdapter.OnClickHandler {
    private final String CLIENT_METHOD_BROADAST_MESSAGE = "receiveMessage";
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
    private SharedPreferences sharedPreferences;

    private HubConnection mHubConnection;
    private HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message

    private String USER_ID, statusId;


    private int ROOM_ID;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
        sharedPreferences = getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        USER_ID = sharedPreferences.getString(Constant.USER_ID_CHAT, "");
        statusId = sharedPreferences.getString(Constant.STATUS_ID_CHAT, "");
        Log.d("USER_ID", "onCreate: " + USER_ID);

        ROOM_ID = sharedPreferences.getInt(Constant.ROOM_ID, 1);

        Log.d("ROOM_ID_", "onCreate: " + ROOM_ID);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {
        mHubConnection.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }

    /**
     * method for clients (activities)
     */

    public void sendMessage(int RoomId, String message) {
        String SERVER_METHOD_SEND = "Send";
        mHubProxy.invoke(SERVER_METHOD_SEND, RoomId, message)
                .done(aVoid -> {
                            //Toast.makeText(getApplicationContext(), "Join", Toast.LENGTH_SHORT).show();
                            Log.d("<Debug", "Join"); // work!
//                            paginateChatRoom.add(new Message(Message.MSG_TYPE_SENT, message));
//                            mRecyclerViewOneToOne.scrollToPosition(0);
                            Log.d("TEEEST_SEND", "sendMessage: " + message + Message.MSG_TYPE_SENT);
//                            messagesOneToOneAdapter = new RecyclerMessagesOneToOneAdapter(messageList, this);
//                            messagesOneToOneAdapter.addItem(new Message(Message.MSG_TYPE_SENT, message.trim()));
//                            mRecyclerViewOneToOne.setAdapter(messagesOneToOneAdapter);
//                            mRecyclerViewOneToOne.smoothScrollToPosition(messagesOneToOneAdapter.getItemCount() - 1);
                        }
                ).onError(throwable -> {
            Log.d("<Debug", throwable.getMessage()); // won't work!

            //  Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }).onCancelled(() -> {
            Log.d("<Debug", "Join"); // won't work!
            // Toast.makeText(getApplicationContext(), "onCancelled", Toast.LENGTH_SHORT).show();


        });
    }

    private void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        Credentials credentials = new Credentials() {
            @Override
            public void prepareRequest(Request request) {
                request.addHeader("Authorization", "Bearer " + sharedPreferences.getString(Constant.UserID, ""));
            }
        };

        String serverUrl = Constant.BASE_URL_HTTP;
        mHubConnection = new HubConnection(serverUrl);
        mHubConnection.setCredentials(credentials);


        String SERVER_HUB_CHAT = "ChatHub";
        mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT);
        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);


        try {
            signalRFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            //   Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }


        String METHOD_JOIN = "Join";
        Log.d("<Debug", "onCreate: " + USER_ID + "___" + sharedPreferences.getString(Constant.ROOM_ID_FOR_SIGNAL_R, ""));
        mHubProxy.invoke(METHOD_JOIN, ROOM_ID)
                .done(
                        aVoid -> {
                            //Toast.makeText(getApplicationContext(), "Join", Toast.LENGTH_SHORT).show();
                            Log.d("<Debug", "Join"); // won't work!
                        }
                ).onError(throwable -> {
            Log.d("<Debug", throwable.getMessage()); // won't work!

            //Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }).onCancelled(() -> {
            Log.d("<Debug", "Join"); // won't work!
        });


//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
//                                new SubscriptionHandler2<String, String>() {
//                                    @Override
//                                    public void run(final String message, final String senderIdentityId) {
//                                        final String finalMsg = message + " " + senderIdentityId;
//                                        // display Toast message
//                                        mHandler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                }
//                                , String.class, String.class);
//
//
//                    }
//                }, 3000);




        /* ****new codes here**** */
        /* ****seems useless but should be here!**** */
        mHubProxy.subscribe(new Object() {
            @SuppressWarnings("unused")
            public void newMessage(final String message, String fullName, final String senderIdentityId) {

                //  Toast.makeText(getApplicationContext(), message.toString() + " " + senderIdentityId, Toast.LENGTH_SHORT).show();
                Log.d("<Debug>", "newMessage: " + message.toString() + fullName.toString() + " " + senderIdentityId);
            }
        });


        mHubConnection.received(new MessageReceivedHandler() {

            @Override
            public void onMessageReceived(final JsonElement json) {
                Log.d("<De", "onMessageReceived: " + json.toString());
                Log.e("<De ", json.toString());

                try {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(json.toString()).getAsJsonObject();
                    Log.d("<De", "run: " + jsonObject.toString());
                    JsonArray jsonArray = jsonObject.getAsJsonArray("A");
                    Log.d("<De", "run: " + jsonArray.toString());


                } catch (Exception e) {
                    Log.d("<De", "onMessageReceived: " + e.getMessage() + "____" + e.getCause());
                }
                mHandler.post(() -> {
                    //     Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(json.toString()).getAsJsonObject();


                    try {
                        JsonArray jsonArray = jsonObject.getAsJsonArray("A");
                        String msg = jsonArray.get(0).toString().replace("\"", "").trim();
                        String partnerID = jsonArray.get(1).toString().replace("\"", "").trim();
                        String name = jsonArray.get(3).toString().replace("\"", "").trim();

                        // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("<De+MESSAGE", "run: " + msg);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!Objects.equals(sharedPreferences.getString(Constant.USER_ID_CHAT, ""), partnerID)) {
                                    Message message = new Message(Message.MSG_TYPE_RECEIVED, msg, name);
                                    message.setName(name);
                                    paginateChatRoom.add(message);
                                    mRecyclerViewOneToOne.smoothScrollToPosition(0);
                                } else {
                                    Message message = new Message(Message.MSG_TYPE_SENT, msg, name);
                                    message.setName(name);
                                    paginateChatRoom.add(message);
                                    mRecyclerViewOneToOne.smoothScrollToPosition(0);
                                }

                            }
                        }, 1000);
//                            new Handler().postDelayed(new Runnable() {
//                                @SuppressLint("StaticFieldLeak")
//                                @Override
//                                public void run() {
//                                    new AsyncTask<Void, Void, Void>() {
//                                        @Override
//                                        protected Void doInBackground(Void... voids) {
//                                            itemViewModel.invalidateDataSource();
//                                            return null;
//                                        }
//
//                                        @Override
//                                        protected void onPostExecute(Void aVoid) {
//                                            super.onPostExecute(aVoid);
//
//                                            itemViewModel.itemPagedList.observe(ChatRoom.lifecycleOwner, new Observer<PagedList<ChatList>>() {
//                                                @Override
//                                                public void onChanged(@Nullable PagedList<ChatList> chatLists) {
//                                                    assert chatLists != null;
//                                                    mRecyclerViewOneToOne.scrollToPosition(0);
//                                                }
//                                            });
//                                        }
//                                    }.execute();
//
//                                }
//                            }, 1000);

//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemViewModel.invalidateDataSource();
//                                    itemViewModel.invalidateDataSource();
//                                }
//                            }, 500);

//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mRecyclerViewOneToOne.smoothScrollToPosition(0);
//                                }
//                            }, 1000);


                    } catch (NullPointerException ignored) {

                    }


                });

            }
        });

    }

    @Override
    public void onClick(int position) {

    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */

    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }
}


