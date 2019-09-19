package apps.sharabash.bzender.paging;

import android.annotation.SuppressLint;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import apps.sharabash.bzender.Models.message.Message;
import apps.sharabash.bzender.Models.singleChat.ChatList;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyTextViewBold;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends PagedListAdapter<ChatList, ChatAdapter.ItemViewHolder> {

    private static DiffUtil.ItemCallback<ChatList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChatList>() {
                @Override
                public boolean areItemsTheSame(ChatList oldItem, ChatList newItem) {
                    return oldItem.getSenderId().equals(newItem.getSenderId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(ChatList oldItem, ChatList newItem) {
                    return oldItem.getMessageDate().equals(newItem.getMessageDate());
                }
            };
    private Context mCtx;
    private SharedPreferences sharedPreferences;
    // private PagedList<ChatList> pagedList ;
    private List<Message> messageList;

    public ChatAdapter(Context mCtx, List<Message> messageList) { //PagedList<ChatList> pagedList
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        //  this.pagedList = pagedList ;
        this.messageList = messageList;
        sharedPreferences = mCtx.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }
//

    public void addItem(Message message) {
        messageList.add(0, message);
        notifyItemInserted(messageList.size());
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.chat_recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {


//        Message message = this.messageList.get(position);
//
//        if (Message.MSG_TYPE_RECEIVED.equals(message.getMsgType())) {
//            // Show received message in left linearlayout.
//            holder.mLinearLayoutLeft.setVisibility(LinearLayout.VISIBLE);
//            holder.mTxtViewLeftMessage.setText(message.getMsgContent());
//            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//            // Otherwise each iteview's distance is too big.
//            holder.mLinearLayoutRight.setVisibility(LinearLayout.GONE);
//
//        }
//        // If the message is a sent message.
//        else if (Message.MSG_TYPE_SENT.equals(message.getMsgType())) {
//            // Show sent message in right linearlayout.
//            holder.mLinearLayoutRight.setVisibility(LinearLayout.VISIBLE);
//            holder.mTxtViewRightMessage.setText(message.getMsgContent());
//            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//            // Otherwise each iteview's distance is too big.
//            holder.mLinearLayoutLeft.setVisibility(LinearLayout.GONE);
//        }
//
//

        //  try {
        ChatList item = getItem(position);
        Message message;
        assert item != null;
        if (!item.getSenderId().equals(sharedPreferences.getString(Constant.USER_ID_CHAT, ""))) {
            message = new Message(Message.MSG_TYPE_RECEIVED, item.getBody());
            holder.mLinearLayoutLeft.setVisibility(LinearLayout.GONE);
            holder.mTxtViewLeftMessage.setText(message.getMsgContent());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.mLinearLayoutRight.setVisibility(LinearLayout.GONE);
            holder.mLinearLayoutLeft.setVisibility(LinearLayout.GONE);
        } else {
            message = new Message(Message.MSG_TYPE_SENT, item.getBody());
            holder.mLinearLayoutRight.setVisibility(LinearLayout.GONE);
            holder.mTxtViewRightMessage.setText(message.getMsgContent());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.mLinearLayoutLeft.setVisibility(LinearLayout.GONE);
            holder.mLinearLayoutRight.setVisibility(LinearLayout.GONE);
        }
//
//        try {
//            Message messagee = this.messageList.get(position);
//            if (Message.MSG_TYPE_RECEIVED.equals(messagee.getMsgType())) {
//                // Show received message in left linearlayout.
//                holder.mLinearLayoutLeft.setVisibility(LinearLayout.VISIBLE);
//                holder.mTxtViewLeftMessage.setText(messagee.getMsgContent());
//                // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//                // Otherwise each iteview's distance is too big.
//                holder.mLinearLayoutRight.setVisibility(LinearLayout.GONE);
//            } else if (Message.MSG_TYPE_SENT.equals(messagee.getMsgType())) {
//                holder.mLinearLayoutRight.setVisibility(LinearLayout.VISIBLE);
//                holder.mTxtViewRightMessage.setText(messagee.getMsgContent());
//                // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//                // Otherwise each iteview's distance is too big.
//                holder.mLinearLayoutLeft.setVisibility(LinearLayout.GONE);
//            }
//        } catch (IndexOutOfBoundsException e) {
//            Toast.makeText(mCtx, "ERROR", Toast.LENGTH_SHORT).show();
//        }
        //  try {
//            if (messageList.get(position) != null) {
//                if (messageList.get(position).getMsgContent().equals(Message.MSG_TYPE_RECEIVED)) {
//                    // Show received message in left linearlayout.
//                    holder.mLinearLayoutLeft.setVisibility(LinearLayout.VISIBLE);
//                    holder.mTxtViewLeftMessage.setText(messageList.get(position).getMsgContent());
//                    // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//                    // Otherwise each iteview's distance is too big.
//                    holder.mLinearLayoutRight.setVisibility(LinearLayout.GONE);
//
//                } else {
//                    holder.mLinearLayoutRight.setVisibility(LinearLayout.VISIBLE);
//                    holder.mTxtViewRightMessage.setText(messageList.get(position).getMsgContent());
//                    // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//                    // Otherwise each iteview's distance is too big.
//                    holder.mLinearLayoutLeft.setVisibility(LinearLayout.GONE);
//
//                }
//            }
//        }catch (IndexOutOfBoundsException ignored){
//
//        }

        holder.linearFake.setVisibility(View.GONE);
//        if (item != null) {
//            if (!item.getSenderId().equals(sharedPreferences.getString(Constant.USER_ID_CHAT, ""))) {
//                // Show received message in left linearlayout.
//                holder.mLinearLayoutLeftFake.setVisibility(LinearLayout.GONE);
//                holder.mTxtViewLeftMessageFake.setText(item.getBody());
//                holder.mTxtViewLeftMessageFake.setVisibility(View.GONE);
//                // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//                // Otherwise each iteview's distance is too big.
//                holder.mLinearLayoutRightFake.setVisibility(LinearLayout.GONE);
//                holder.mLinearLayoutLeftFake.setVisibility(LinearLayout.GONE);
//            } else if (item.getSenderId().equals(sharedPreferences.getString(Constant.USER_ID_CHAT, ""))) {
//                holder.mLinearLayoutRightFake.setVisibility(LinearLayout.GONE);
//                holder.mTxtViewRightMessageFake.setText(item.getBody());
//                holder.mTxtViewRightMessageFake.setVisibility(View.GONE);
//                // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
//                // Otherwise each iteview's distance is too big.
//                holder.mLinearLayoutLeftFake.setVisibility(LinearLayout.GONE);
//                holder.mLinearLayoutRightFake.setVisibility(View.GONE);
//            }
//        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {


        private final MyTextViewBold mTxtViewRightMessage;
        private final MyTextViewBold mTxtViewLeftMessage;
        private final LinearLayout mLinearLayoutLeft;
        private final LinearLayout mLinearLayoutRight;

        private LinearLayout linearFake;
        private CircleImageView mCircleImgLeft, mCircleImgRight;


        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            //linearFake = itemView.findViewById(R.id.linear_fake);

            mTxtViewLeftMessage = itemView.findViewById(R.id.chat_left_msg_text_view);
            mTxtViewRightMessage = itemView.findViewById(R.id.chat_right_msg_text_view);


            mLinearLayoutLeft = itemView.findViewById(R.id.chat_left_msg_layout);
            mLinearLayoutRight = itemView.findViewById(R.id.chat_right_msg_layout);


//            mTxtViewLeftMessageFake = itemView.findViewById(R.id.chat_left_msg_text_fake);
//            mTxtViewRightMessageFake = itemView.findViewById(R.id.chat_right_msg_text_view_fake);
//
//
//            mLinearLayoutLeftFake = itemView.findViewById(R.id.chat_left_msg_layout_fake);
//            mLinearLayoutRightFake = itemView.findViewById(R.id.chat_right_msg_layout_fake);


        }
    }

}
