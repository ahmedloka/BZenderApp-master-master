package apps.pixel.bzender.activities.Tenders;

import android.content.Intent;
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

import apps.pixel.bzender.Models.AddTenders.TendersModelResponse;
import apps.pixel.bzender.Models.allTinders.AllTenderRecyclerItem;
import apps.pixel.bzender.Models.allTinders.car.AllTender;
import apps.pixel.bzender.Models.allTinders.electrical.AllTenderElectrical;
import apps.pixel.bzender.Models.getAllTendersRealEstate.AllTenderRealEstateResponse;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.MyTextViewBold;
import apps.pixel.bzender.activities.TenderDetails.TinderDetails;
import apps.pixel.bzender.adapters.AllTenderRecyclerAdapter;

public class AllTenderActivity extends AppCompatActivity implements TendersInterface, AllTenderRecyclerAdapter.OnClickHandler {


    private final List<String> tenderElectricalId = new ArrayList<>();
    private final List<String> tenderCarId = new ArrayList<>();
    private final List<String> tenderRealEstateId = new ArrayList<>();
    private final List<AllTenderRecyclerItem> allTenderRecyclerItem = new ArrayList<>();
    private String dateTime;
    private RecyclerView mRecyclerViewAllTender;
    public static LinearLayoutCompat mTxtEmpty;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateFade(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constant.ENTERED_HERE) {
            finish();
            Intent starterIntent = new Intent(this, AllTenderActivity.class);
            startActivity(starterIntent);

            Constant.ENTERED_HERE = false;
        }
        // recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_list_items);

        mTxtEmpty = findViewById(R.id.txt_empty);


        AppCompatImageView mImgBack = findViewById(R.id.imageNavigationIcon);
        mImgBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateFade(this);
        });

        mRecyclerViewAllTender = findViewById(R.id.recycler);

        TendersPresenter tendersPresenter = new TendersPresenter(this, this);


        if (Constant.categoryId.equals(String.valueOf(10021))) {
            tendersPresenter.getAllTenderItems(String.valueOf(10021));
        } else if (Constant.categoryId.equals(String.valueOf(10022)))
            tendersPresenter.getAllTenderItemsElectrical(String.valueOf(10022));
        else
            tendersPresenter.getAllTenderItemsRealEstate(String.valueOf(10023));


        MyTextViewBold mTxtTitle = findViewById(R.id.title_appbar);
        mTxtTitle.setText(getString(R.string.item_details));

        Log.d("TEST", "onCreate: " + Constant.categoryId);
    }

    @Override
    public void handleCategoryList(List<TendersModelResponse> tendersModelResponses) {

    }

    @Override
    public void getAllTenderList(List<AllTender> allTenders) {

        Log.d("RESPONSE", "START___ : " + allTenders.get(0).getStartDateTender() + " END__" + allTenders.get(0).getEndDateTender());

        try {
            allTenders.get(0);
        } catch (NullPointerException ignored) {
            mTxtEmpty.setVisibility(View.VISIBLE);
            return;
        }
        if (allTenders.isEmpty()) {
            //Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            //Log.d("TESTTESTTEST", "getMyTender: " + "nullllll");

            mTxtEmpty.setVisibility(View.VISIBLE);
            return;
        }

        mTxtEmpty.setVisibility(View.GONE);

        for (int i = 0; i < allTenders.size(); i++) {
            allTenderRecyclerItem.add(new AllTenderRecyclerItem(allTenders.get(i).getStartDateTender()
                    , allTenders.get(i).getEndDateTender()
                    , allTenders.get(i).getCategoryID()
                    , allTenders.get(i).getTenderName()
                    , allTenders.get(i).getBookingCount()));

            tenderCarId.add(allTenders.get(i).getId());

        }


        mRecyclerViewAllTender.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAllTender.setHasFixedSize(true);
        AllTenderRecyclerAdapter recyclerAdapter = new AllTenderRecyclerAdapter(this, allTenderRecyclerItem, this);
        mRecyclerViewAllTender.setAdapter(recyclerAdapter);

    }

    @Override
    public void getAllTenderElectrical(List<AllTenderElectrical> allTenderElectricals) {


        Log.d("TESTTESTTEST", "getAllTenderElectrical: " + allTenderElectricals.toString());
        try {
            allTenderElectricals.get(0);
        } catch (NullPointerException e) {
            mTxtEmpty.setVisibility(View.VISIBLE);
            return;
        }


        mTxtEmpty.setVisibility(View.GONE);
        Log.d("RESPONSE", "START___ : " + allTenderElectricals.get(0).getStartDateTender() + " END__" + allTenderElectricals.get(0).getEndDateTender());


        for (int i = 0; i < allTenderElectricals.size(); i++) {
            allTenderRecyclerItem.add(new AllTenderRecyclerItem(allTenderElectricals.get(i).getStartDateTender()
                    , allTenderElectricals.get(i).getEndDateTender()
                    , allTenderElectricals.get(i).getCategoryID()
                    , allTenderElectricals.get(i).getTenderName()
                    , allTenderElectricals.get(i).getBookingCount()));

            tenderElectricalId.add(allTenderElectricals.get(i).getId());
        }


        mRecyclerViewAllTender.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAllTender.setHasFixedSize(true);
        AllTenderRecyclerAdapter recyclerAdapter = new AllTenderRecyclerAdapter(this, allTenderRecyclerItem, this);
        mRecyclerViewAllTender.setAdapter(recyclerAdapter);
        Constant.runLayoutAnimation(mRecyclerViewAllTender);


    }

    @Override
    public void getAllTenderRealEstate(List<AllTenderRealEstateResponse> allTenderRealEstateResponses) {
        Log.d("TESTTESTTEST", "getAllTenderElectrical: " + allTenderRealEstateResponses.toString());
        if (allTenderRealEstateResponses.isEmpty()) {
            //   Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            //  Log.d("TESTTESTTEST", "getMyTender: " + "nullllll");

            mTxtEmpty.setVisibility(View.VISIBLE);

            return;
        }


        mTxtEmpty.setVisibility(View.GONE);
        Log.d("RESPONSE", "START___ : " + allTenderRealEstateResponses.get(0).getStartDateTender() + " END__" + allTenderRealEstateResponses.get(0).getEndDateTender());


        for (int i = 0; i < allTenderRealEstateResponses.size(); i++) {
            allTenderRecyclerItem.add(new AllTenderRecyclerItem(allTenderRealEstateResponses.get(i).getStartDateTender()
                    , allTenderRealEstateResponses.get(i).getEndDateTender()
                    , allTenderRealEstateResponses.get(i).getCategoryID()
                    , allTenderRealEstateResponses.get(i).getTenderName()
                    , allTenderRealEstateResponses.get(i).getBookingCount()));

            tenderRealEstateId.add(allTenderRealEstateResponses.get(i).getId());
        }


        mRecyclerViewAllTender.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAllTender.setHasFixedSize(true);
        AllTenderRecyclerAdapter recyclerAdapter = new AllTenderRecyclerAdapter(this, allTenderRecyclerItem, this);
        mRecyclerViewAllTender.setAdapter(recyclerAdapter);
        Constant.runLayoutAnimation(mRecyclerViewAllTender);

    }


    @Override
    public void onClick(int position) {


        Intent intent = new Intent(this, TinderDetails.class);

        if (!tenderCarId.isEmpty()) {
            Log.d("TESST", "onClick: " + tenderCarId.size());
            intent.putExtra(Constant.TENDER_ID, tenderCarId.get(position));
        }
        if (!tenderElectricalId.isEmpty()) {
            Log.d("TESST", "onClick: " + tenderElectricalId.size());
            Log.d("TESST", "onClick: " + tenderElectricalId.get(position));
            intent.putExtra(Constant.TENDER_ID, tenderElectricalId.get(position));
        }

        if (!tenderRealEstateId.isEmpty()) {
            intent.putExtra(Constant.TENDER_ID, tenderRealEstateId.get(position));
        }

        startActivity(intent);
        Animatoo.animateSlideRight(this);


//        //intent.putExtra(Constant.TENDER_ID)
//        if (tenderCarId.size() > 1) {
//           // Constant.TENDER_ID = String.valueOf(tenderCarId.get(position));
//            Log.d("IDDD", "onClick: " + tenderCarId.get(position));
//        }
//
//        if (tenderElectricalId.size() > 1) {
//            //Constant.TENDER_ID = String.valueOf(tenderCarId.get(position));
//            Log.d("IDDD", "onClick: " + tenderCarId.get(position));
//        }
//

    }

}

