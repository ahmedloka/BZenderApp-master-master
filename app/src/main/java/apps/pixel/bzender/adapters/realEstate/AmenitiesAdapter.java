package apps.pixel.bzender.adapters.realEstate;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.MyTextViewBold;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.ViewHolder> {


    private final List<String> nameList;
    private final List<String> positionsList;
    private final OnCheckChange onCheckChange;
    private final OnItemCheckListener onItemCheck;
    private Context context;
    private SharedPreferences sharedPreferences;
    private List<String> ids;
    private List<String> dataNames;

    public AmenitiesAdapter(Context context, List<String> nameList, List<String> positionsList, OnCheckChange onCheckChange, OnItemCheckListener onItemCheck) {
        this.context = context;
        this.onItemCheck = onItemCheck;
        this.nameList = nameList;
        this.onCheckChange = onCheckChange;
        this.positionsList = positionsList;
        ids = new ArrayList<>();
        dataNames = new ArrayList<>();

        sharedPreferences = getContext().getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    @Override
    public int getItemCount() {
        return nameList == null ? 0 : nameList.size();
    }

    @NonNull
    @Override
    public AmenitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ameninities, parent, false);
        return new AmenitiesAdapter.ViewHolder(view);
    }

    private void removeAt(int position) {
        nameList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, nameList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final AmenitiesAdapter.ViewHolder holder, final int listPosition) {

        holder.myTextViewBold.setText(nameList.get(listPosition));

        String item = positionsList.get(listPosition);
        String itemName = nameList.get(listPosition);

        ((ViewHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewHolder) holder).checkBox.setChecked(
                        !((ViewHolder) holder).checkBox.isChecked());
                if (((ViewHolder) holder).checkBox.isChecked()) {
                    onItemCheck.onItemCheck(item, itemName);
                } else {
                    onItemCheck.onItemUncheck(item, itemName);
                }
            }
        });

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface OnItemCheckListener {
        void onItemCheck(String item, String name);

        void onItemUncheck(String item, String name);
    }

    public interface OnCheckChange {
        void onCheckChange(String position, List<String> selectedIds, List<String> names);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private MyTextViewBold myTextViewBold;

        ViewHolder(View itemView) {
            super(itemView);


            checkBox = itemView.findViewById(R.id.checkbox);
            checkBox.setClickable(false);

            myTextViewBold = itemView.findViewById(R.id.txt_of_check_box);


        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}

