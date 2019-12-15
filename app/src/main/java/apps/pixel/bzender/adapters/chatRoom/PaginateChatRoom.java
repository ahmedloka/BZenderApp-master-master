package apps.pixel.bzender.adapters.chatRoom;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import apps.pixel.bzender.Models.message.Message;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.MyTextView;
import apps.pixel.bzender.Utills.MyTextViewBold;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

public class PaginateChatRoom extends RecyclerView.Adapter<PaginateChatRoom.ItemViewHolder> {

    private Context mCtx;
    private SharedPreferences sharedPreferences;
    // private PagedList<ChatList> pagedList ;
    private List<Message> messageList;
    private String catId;

    public PaginateChatRoom(Context mCtx, List<Message> messageList) {
        this.mCtx = mCtx;
        this.messageList = messageList;
        sharedPreferences = mCtx.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        catId = sharedPreferences.getString(Constant.CAT_ID, "");
    }


    public void add(Message message) {
        this.messageList.add(0, message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.chat_recycler_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        ItemViewHolder vh = itemViewHolder;

        Log.d("CAT_ID_", "onBindViewHolder: " + catId);

        switch (catId) {
            case "10021":
                vh.mCircleImgLeft.setImageResource(R.drawable.cars);
                break;
            case "10022":
                vh.mCircleImgLeft.setImageResource(R.drawable.electrical);
                break;
            case "10023":
                vh.mCircleImgLeft.setImageResource(R.drawable.ic_real_estate);
                break;
            default:
                vh.mCircleImgLeft.setImageResource(R.drawable.bzender);
                break;
        }
        Message messagee = this.messageList.get(position);
        if (Message.MSG_TYPE_RECEIVED.equals(messagee.getMsgType())) {
            // Show received message in left linearlayout.
            vh.mLinearLayoutLeft.setVisibility(LinearLayout.VISIBLE);
            vh.linearNameLeft.setVisibility(View.VISIBLE);
            vh.linearNameRight.setVisibility(GONE);
            vh.mTxtNameLeft.setVisibility(View.VISIBLE);
            vh.mTxtNameLeft.setText("~ " + messagee.getName().trim());
            Log.d("NAME_N", "onBindViewHolder: " + messagee.getName());
            vh.mTxtViewLeftMessage.setText(messagee.getMsgContent());
            vh.mLinearLayoutRight.setVisibility(GONE);
//
//            try {
//                if (messagee.getName().equals(null) || messagee.getName().equals(" ") || messagee.getName().equals("")) {
//                    vh.linearNameLeft.setVisibility(GONE);
//                }
//            } catch (NullPointerException e) {
//                vh.linearNameLeft.setPadding(0,10,0,0);
//                vh.linearNameLeft.setVisibility(GONE);
//
//            }


            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
        } else if (Message.MSG_TYPE_SENT.equals(messagee.getMsgType())) {
            vh.mLinearLayoutRight.setVisibility(LinearLayout.VISIBLE);
            vh.mTxtViewRightMessage.setText(messagee.getMsgContent());
            vh.linearNameRight.setVisibility(View.VISIBLE);
            vh.mTxtNameRight.setVisibility(View.VISIBLE);
            vh.linearNameLeft.setVisibility(GONE);
            vh.mTxtNameRight.setText("~ " + messagee.getName().trim());
            Log.d("NAME_N", "onBindViewHolder: " + messagee.getName());
            vh.mLinearLayoutLeft.setVisibility(GONE);
//            try {
//                if (messagee.getName().equals(null) || messagee.getName().equals(" ") || messagee.getName().equals("")) {
//                    vh.linearNameRight.setVisibility(GONE);
//                }
//            } catch (NullPointerException e) {
//                vh.linearNameRight.setPadding(0,10,0,0);
//                vh.linearNameRight.setVisibility(GONE);
//
//            }


            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        final MyTextViewBold mTxtViewRightMessage;
        private final MyTextViewBold mTxtViewLeftMessage;
        private final LinearLayout mLinearLayoutLeft;
        private final LinearLayout mLinearLayoutRight;
        private final MyTextView mTxtNameLeft, mTxtNameRight;
        private LinearLayout linearNameLeft, linearNameRight;
        private CircleImageView mCircleImgLeft;


        ItemViewHolder(@NonNull View itemView) {
            super(itemView);


            mCircleImgLeft = itemView.findViewById(R.id.img_left);

            linearNameLeft = itemView.findViewById(R.id.linear_name_left);
            linearNameRight = itemView.findViewById(R.id.linear_name_right);


            mTxtViewLeftMessage = itemView.findViewById(R.id.chat_left_msg_text_view);
            mTxtViewRightMessage = itemView.findViewById(R.id.chat_right_msg_text_view);

            mLinearLayoutLeft = itemView.findViewById(R.id.chat_left_msg_layout);
            mLinearLayoutRight = itemView.findViewById(R.id.chat_right_msg_layout);

            mTxtNameLeft = itemView.findViewById(R.id.text_name_left);
            mTxtNameRight = itemView.findViewById(R.id.text_name_right);


        }
    }
}
