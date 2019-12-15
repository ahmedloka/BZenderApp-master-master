package apps.pixel.bzender.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import apps.pixel.bzender.Models.booking.MyTender;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.CountTinerDownTimer;
import apps.pixel.bzender.Utills.MyTextView;
import apps.pixel.bzender.Utills.MyTextViewBold;

import static android.content.Context.MODE_PRIVATE;

public class MyTenderAdapter extends RecyclerView.Adapter<MyTenderAdapter.ViewHolder> {

    private static final String TAG = "MY_TENDER_ADAPTER";
    private final List<MyTender> itemList;
    private Context context;
    private String lang;


    public MyTenderAdapter(Context context, List<MyTender> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @NonNull
    @Override
    public MyTenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_tinder, parent, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        lang = sharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        return new MyTenderAdapter.ViewHolder(view);
    }

    private void removeAt(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyTenderAdapter.ViewHolder holder, final int listPosition) {


        Log.d(TAG, "onBindViewHolder: " + itemList.get(listPosition).getStatusId());

        switch (itemList.get(listPosition).getStatusId()) {
            case 4: //accepted
                if (lang.equals("ar"))
                    holder.imgTypeBg.setImageResource(R.drawable.accepted_img_ar);
                else
                    holder.imgTypeBg.setImageResource(R.drawable.accepted_img_en);
                break;
            case 6: // pending
                if (lang.equals("ar"))
                    holder.imgTypeBg.setImageResource(R.drawable.pending_img_ar);
                else
                    holder.imgTypeBg.setImageResource(R.drawable.pending_img_en);
                break;
            case 5: // rejected
                if (lang.equals("ar"))
                    holder.imgTypeBg.setImageResource(R.drawable.rejected_img_ar);
                else
                    holder.imgTypeBg.setImageResource(R.drawable.rejected_img_en);
                break;
            case 7:// finished
                if (lang.equals("ar"))
                    holder.imgTypeBg.setImageResource(R.drawable.finished_ar);
                else
                    holder.imgTypeBg.setImageResource(R.drawable.finished_en);
                break;
            default:
                holder.imgTypeBg.setVisibility(View.GONE);
                break;
        }

        if (itemList.get(listPosition).getCatName().equals(context.getString(R.string.cat_cars))) {
            holder.imgCat.setImageDrawable(context.getResources().getDrawable(R.drawable.cars));
        } else if (itemList.get(listPosition).getCatName().equals(context.getString(R.string.cat_electronincs))) {
            holder.imgCat.setImageDrawable(context.getResources().getDrawable(R.drawable.electrical));
        } else {
            holder.imgCat.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_real_estate));
        }

        holder.endDate.setText(itemList.get(listPosition).getEndTime().replace("T00:00:00".trim(), ""));
        holder.startDate.setText(itemList.get(listPosition).getStartTime().replace("T00:00:00".trim(), ""));
        holder.txtCatName.setText(itemList.get(listPosition).getCatName());
        holder.txtTenderName.setText(itemList.get(listPosition).getTenderName());

        String strDate = itemList.get(listPosition).getStartTime();

        String endDate = itemList.get(listPosition).getEndTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date dateOne = dateFormat.parse(strDate);
            Date dateTwo = dateFormat.parse(endDate);

            Calendar calendar = Calendar.getInstance();
            long currentDate = calendar.getTime().getTime();
            long difference;
            if (dateOne.getTime() >= currentDate)
                difference = dateTwo.getTime() - dateOne.getTime();
            else if (currentDate >= dateTwo.getTime())
                difference = 0;
            else
                difference = dateTwo.getTime() - currentDate;

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

            CountDownTimer countDownTimer = new CountTinerDownTimer(difference, 1000, holder.txtDays, holder.txtHrs, holder.txtMins, holder.txtSecs);

            countDownTimer.start();


        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("startDate", "getAllTenderList: " + e.getMessage());
        }


//        if (holder.txtHrs.getText().equals("00") && holder.txtDays.getText().equals("00") && holder.txtMins.getText().equals("00") && holder.txtSecs.getText().equals("00")) {
//            holder.itemView.setVisibility(View.GONE);
//        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatImageView imgTypeBg;
        private AppCompatImageView imgCat;
        private MyTextViewBold txtCatName;
        private MyTextViewBold txtTenderName;
        private MyTextViewBold txtDays;
        private MyTextViewBold txtHrs;
        private MyTextViewBold txtMins;
        private MyTextViewBold txtSecs;
        private MyTextView startDate;
        private LinearLayoutCompat linearLayoutCompat;
        private MyTextView endDate;
        private MyTextViewBold txtSoon;
        private LinearLayout mLinearTimer;


        ViewHolder(View itemView) {
            super(itemView);

            initViews(itemView);
        }

        private void initViews(View itemView) {
            imgTypeBg = itemView.findViewById(R.id.img_type_bg);
            imgCat = itemView.findViewById(R.id.img_cat);
            txtCatName = itemView.findViewById(R.id.txt_cat_name);
            txtTenderName = itemView.findViewById(R.id.txt_tender_name);
            txtDays = itemView.findViewById(R.id.txt_days);
            txtHrs = itemView.findViewById(R.id.txt_hrs);
            txtMins = itemView.findViewById(R.id.txt_mins);
            txtSecs = itemView.findViewById(R.id.txt_secs);
            startDate = itemView.findViewById(R.id.startDate);
            linearLayoutCompat = itemView.findViewById(R.id.linearLayoutCompat);
            endDate = itemView.findViewById(R.id.endDate);

            txtSoon = itemView.findViewById(R.id.txt_soon);
            mLinearTimer = itemView.findViewById(R.id.linear_timer);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
