package apps.pixel.bzender.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import apps.pixel.bzender.Models.RadioButtonModel;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.MyTextView;
import apps.pixel.bzender.activities.packages.SelectedInterface;

public class AdapterPackes extends RecyclerView.Adapter<AdapterPackes.ViewHolder> {
    private List<String> mListPoints;
    private List<String> mListMoney;
    private List<String> idList;
    private Context mContext;
    private boolean like = false;

    private RadioButton selectedRadioButton;
    private List<RadioButtonModel> radioButtonModel = new ArrayList<>();
    private int selectedPosition;

    private SelectedInterface selectedInterface;


    public AdapterPackes(Context context, List<String> mListMoney, List<String> mListPoints, List<String> idList, SelectedInterface selectedInterface) {
        this.mContext = context;
        this.mListMoney = mListMoney;
        this.mListPoints = mListPoints;
        this.idList = idList;
        this.selectedInterface = selectedInterface;
    }

    @NonNull
    @Override
    public AdapterPackes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutItemGridView = R.layout.item_package_rv;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutItemGridView, parent, false);

        return new AdapterPackes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPackes.ViewHolder holder, int position) {

        holder.mTxtPoint.setText(mListPoints.get(position) + "  Point");
        holder.mTxtMoney.setText(mListMoney.get(position) + "    L.E");

        RadioButton radioButton = holder.mBtn;

        radioButton.setChecked(false);

        radioButton.setOnClickListener(v -> {
            handleingOfRadioButtons(position, v);

            Log.d("SELECTED", "onBindViewHolder: " + selectedPosition);
            selectedInterface.getSelectedRadioBtn(String.valueOf(idList.get(selectedPosition)));
        });

    }

    private void handleingOfRadioButtons(int position, View v) {
        // Set unchecked all other elements in the list, so to display only one selected radio button at a time
        for (RadioButtonModel model : radioButtonModel)
            model.setChecked(false);
        // Set "checked" the model associated to the clicked radio button
        radioButtonModel.add(new RadioButtonModel(true, position));
        selectedPosition = position;
        Log.d("POSITION", "onBindViewHolder: " + selectedPosition + "  __" + position);
        // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
        if (null != selectedRadioButton && !v.equals(selectedRadioButton))
            selectedRadioButton.setChecked(false);
        // Replace the previous selected radio button with the current (clicked) one, and "check" it
        selectedRadioButton = (RadioButton) v;
        selectedRadioButton.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return mListPoints.size();
    }

    public interface ItemOnClickHandler {
        void onClick(int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener

        private MyTextView mTxtPoint, mTxtMoney;
        private RadioButton mBtn;

        ViewHolder(View itemView) {
            super(itemView);

            // itemView.setOnClickListener(this);

            mTxtMoney = itemView.findViewById(R.id.txt_money);
            mTxtPoint = itemView.findViewById(R.id.txt_points);
            mBtn = itemView.findViewById(R.id.radio_btn);


        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            mClickHandler.onClick(position);
//        }
    }
}

