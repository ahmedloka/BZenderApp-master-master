package apps.sharabash.bzender.activities.myBooking;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

import apps.sharabash.bzender.Models.my_tenders.MyBookingBody;
import apps.sharabash.bzender.Models.my_tenders.MyTendersBody;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyTextViewBold;
import apps.sharabash.bzender.adapters.ItemBookingModel;
import apps.sharabash.bzender.adapters.MyBookingAdapter;

public class MyBooking extends AppCompatActivity implements MyTenderInterface {

    private static final String TAG = "MyTender";
    private RecyclerView nearByRecyclerView;
    private LinearLayoutCompat mTxtEmpty;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateInAndOut(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_my_booking);


        initViews();

    }

    private void initViews() {
        mTxtEmpty = findViewById(R.id.txt_empty);

        AppCompatImageView mImgBack = findViewById(R.id.imageNavigationIcon);
        mImgBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateInAndOut(this);
        });


        MyTenderPresenter myTenderPresenter = new MyTenderPresenter(MyBooking.this, this);
        myTenderPresenter.getMyooking();
        nearByRecyclerView = findViewById(R.id.recycler);


    }

    @Override
    public void getMyooking(MyBookingBody myTenderModelsList) {
        if (myTenderModelsList.getBookingList().size() == 0) {
            //Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            Log.d("CHECK", "getMyTender: " + "nullllll");

            mTxtEmpty.setVisibility(View.VISIBLE);
            return;
        }

        mTxtEmpty.setVisibility(View.GONE);

        List<ItemBookingModel> itemModels = new ArrayList<>();

        String cat = null;

        for (int i = 0; i < myTenderModelsList.getBookingList().size(); i++) {
            if (myTenderModelsList.getBookingList().get(i).getCategoryId().equals("10021")) {
                cat = getString(R.string.cat_cars);
            } else if (myTenderModelsList.getBookingList().get(i).getCategoryId().equals("10022")) {
                cat = getString(R.string.cat_electronincs);
            }

            itemModels.add(new ItemBookingModel(myTenderModelsList.getBookingList().get(i).getId()
                    , myTenderModelsList.getBookingList().get(i).getTenderName(),
                    myTenderModelsList.getBookingList().get(i).getStartDateTender(),
                    myTenderModelsList.getBookingList().get(i).getEndDateTender()
                    , cat,
                    myTenderModelsList.getBookingList().get(i).getStatusId()));

            Log.d("CHECK", "getMyooking: " + myTenderModelsList.getBookingList().get(i).toString());

        }


        Log.d(TAG, "DATE_start: " + itemModels.get(0).getStartDate());
        Log.d(TAG, "DATE_end: " + itemModels.get(0).getEndDate());


        nearByRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MyBookingAdapter nearByRecyclerAdapter = new MyBookingAdapter(this, itemModels);

        Log.d(TAG, "getMyooking: " + itemModels.get(0).toString());

        nearByRecyclerView.setAdapter(nearByRecyclerAdapter);
        Constant.runLayoutAnimation(nearByRecyclerView);


    }

    @Override
    public void getMyTender(List<MyTendersBody> myTendersBodyList) {

    }


}
