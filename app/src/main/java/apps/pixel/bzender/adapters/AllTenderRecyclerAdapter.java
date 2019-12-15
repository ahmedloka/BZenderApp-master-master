package apps.pixel.bzender.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apps.pixel.bzender.Models.allTinders.AllTenderRecyclerItem;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.CountTinerDownTimer;
import apps.pixel.bzender.Utills.MyTextViewBold;


public class AllTenderRecyclerAdapter extends RecyclerView.Adapter<AllTenderRecyclerAdapter.ViewHolder> { //implements AllTendersInterface

    private static final String TAG = "AdapterAllTenders";

    private final OnClickHandler onClickHandler;
    private final List<AllTenderRecyclerItem> allTenderRecyclerItems;
    private Context context;

    public AllTenderRecyclerAdapter(Context context, List<AllTenderRecyclerItem> allTenderRecyclerItems, OnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
        this.context = context;
        this.allTenderRecyclerItems = allTenderRecyclerItems;

    }

    @Override
    public int getItemCount() {
        return allTenderRecyclerItems == null ? 0 : allTenderRecyclerItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
        return new ViewHolder(view);
    }

    private void removeAt(int position) {
        allTenderRecyclerItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, allTenderRecyclerItems.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {


        holder.name.setText(allTenderRecyclerItems.get(listPosition).getName());
        holder.startDate.setText(allTenderRecyclerItems.get(listPosition).getStartDate().replace("T00:00:00".trim(), ""));
        holder.endDate.setText(allTenderRecyclerItems.get(listPosition).getEndDate().replace("T00:00:00".trim(), ""));

        holder.count.setText(allTenderRecyclerItems.get(listPosition).getCount());

        String strDate = allTenderRecyclerItems.get(listPosition).getStartDate();
        String endDate = allTenderRecyclerItems.get(listPosition).getEndDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date dateOne = dateFormat.parse(strDate);
            Date dateTwo = dateFormat.parse(endDate);

            Calendar calendar = Calendar.getInstance();
            long currentDate = calendar.getTime().getTime();
            long difference;
            if (dateOne.getTime() >= currentDate) {
                difference = dateTwo.getTime() - dateOne.getTime();
                holder.mLinearTimer.setVisibility(View.VISIBLE);
            } else if (currentDate >= dateTwo.getTime())
                difference = 0;
            else {
                difference = dateTwo.getTime() - currentDate;
                holder.mLinearTimer.setVisibility(View.VISIBLE);
            }
            if (difference < 0)
                difference = 0;

            if (currentDate < dateOne.getTime()) {
                holder.mLinearTimer.setVisibility(View.GONE);
                holder.txtSoon.setVisibility(View.VISIBLE);
            } else {
                holder.mLinearTimer.setVisibility(View.VISIBLE);
                holder.txtSoon.setVisibility(View.GONE);
            }

            Log.d("difference", "getAllTenderList: " + difference);
            CountTinerDownTimer countDownTimer = new CountTinerDownTimer(difference, 1000, holder.txtDays, holder.txtHrs, holder.txtMins, holder.txtSecs);
            countDownTimer.start();


        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("startDate", "getAllTenderList: " + e.getMessage());
        }

//
//        if (holder.txtHrs.getText().equals("00") && holder.txtDays.getText().equals("00") && holder.txtMins.getText().equals("00") && holder.txtSecs.getText().equals("00")) {
//            //holder.itemView.setVisibility(View.GONE);
//            Log.d(TAG, "onBindViewHolder: " + holder.count);
//        }
////        if (!holder.txtHrs.getText().equals("00") && !holder.txtDays.getText().equals("00") && !holder.txtMins.getText().equals("00") && !holder.txtSecs.getText().equals("00")){
////            holder.itemView.setVisibility(View.VISIBLE);
////
////        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public interface OnClickHandler {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final MyTextViewBold txtDays;
        final MyTextViewBold txtHrs;
        final MyTextViewBold txtMins;
        final MyTextViewBold txtSecs;
        //SimpleRatingBar offer_rate;
        final TextView name;
        final TextView startDate;
        final TextView endDate;
        final TextView count;
        private MyTextViewBold txtSoon;
        private LinearLayout mLinearTimer;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            count = itemView.findViewById(R.id.count);

            txtSoon = itemView.findViewById(R.id.txt_soon);
            mLinearTimer = itemView.findViewById(R.id.linear_timer);

            txtDays = itemView.findViewById(R.id.txt_days);
            txtHrs = itemView.findViewById(R.id.txt_hrs);
            txtMins = itemView.findViewById(R.id.txt_mins);
            txtSecs = itemView.findViewById(R.id.txt_secs);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onClickHandler.onClick(position);
            notifyDataSetChanged();
        }
    }


}
