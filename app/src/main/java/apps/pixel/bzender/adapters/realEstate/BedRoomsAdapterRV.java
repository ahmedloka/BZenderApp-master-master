package apps.pixel.bzender.adapters.realEstate;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.MyTextViewBold;

public class BedRoomsAdapterRV extends RecyclerView.Adapter<BedRoomsAdapterRV.ViewHolder> {


    private final List<String> dataList;
    private final BedRoomsAdapterRV.OnCheckChange onClickHandler;
    private Context context;
    private SharedPreferences sharedPreferences;
    private List<String> ids;

    public BedRoomsAdapterRV(Context context, List<String> dataList, BedRoomsAdapterRV.OnCheckChange onCheckChange) {
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
    public BedRoomsAdapterRV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bed_rooms, parent, false);
        return new BedRoomsAdapterRV.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int listPosition) {
        viewHolder.myTextViewBold.setText(dataList.get(listPosition) + " :  " + context.getString(R.string.room));

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

    public interface OnCheckChange {
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
            onClickHandler.onClick(dataList.get(position));
        }
    }
}

