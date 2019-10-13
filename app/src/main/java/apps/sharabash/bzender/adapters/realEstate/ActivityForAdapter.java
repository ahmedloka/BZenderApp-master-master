package apps.sharabash.bzender.adapters.realEstate;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.MyTextViewBold;

public class ActivityForAdapter extends RecyclerView.Adapter<ActivityForAdapter.ViewHolder> {

    private final List<String> dataList;
    private final OnClickHandler onClickHandler;
    private Context context;
    private SharedPreferences sharedPreferences;
    private List<String> ids;

    public ActivityForAdapter(Context context, List<String> dataList, OnClickHandler onCheckChange) {
        this.context = context;
        this.onClickHandler = onCheckChange;
        this.dataList = dataList;
        ids = new ArrayList<>();

        sharedPreferences = getContext().getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @NonNull
    @Override
    public ActivityForAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_for, parent, false);
        return new ActivityForAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityForAdapter.ViewHolder viewHolder, int listPosition) {
        viewHolder.myTextViewBold.setText(dataList.get(listPosition));

    }

    private void removeAt(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface OnClickHandler {
        void onClick(String position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyTextViewBold myTextViewBold;

        ViewHolder(View itemView) {
            super(itemView);

            myTextViewBold = itemView.findViewById(R.id.txt_for_level);
            myTextViewBold.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onClickHandler.onClick(String.valueOf(position));
        }
    }
}


