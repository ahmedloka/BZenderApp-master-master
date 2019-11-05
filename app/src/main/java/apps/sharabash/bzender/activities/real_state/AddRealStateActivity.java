package apps.sharabash.bzender.activities.real_state;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apps.sharabash.bzender.Models.AddTenders.AllCitiesModel;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.ButtonBook;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyEditText;
import apps.sharabash.bzender.Utills.MyTextView;
import apps.sharabash.bzender.activities.AddTender.AddTinderInterface;
import apps.sharabash.bzender.activities.AddTender.AddTinderPresenter;
import apps.sharabash.bzender.adapters.filterAreaAdapter;
import apps.sharabash.bzender.adapters.filterAreaModelRecycler;
import apps.sharabash.bzender.dialog.realEstate.DialogActivityForRealEstate;
import apps.sharabash.bzender.dialog.realEstate.DialogAmenities;
import apps.sharabash.bzender.dialog.realEstate.DiloagBedRoomslRealEstate;
import apps.sharabash.bzender.dialog.realEstate.DiloagLevelRealEstate;

import static apps.sharabash.bzender.Utills.Constant.StartDate;
import static apps.sharabash.bzender.dialog.realEstate.DialogAmenities.currentSelectedItems;

public class AddRealStateActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AddTinderInterface {

    public static MyTextView txtBedrooms;
    public static MyTextView txtLevel;
    public static MyTextView txtAmenities;
    public static MyTextView txtActivityFor;
    public static ButtonBook btnNextRealState;
    public static ProgressBar mProgressBar;
    public static ProgressBar mProgressBarAddTender;
    private final List<String> wheelDataNeedTo = new ArrayList<>();
    private final List<String> wheelDataType = new ArrayList<>();
    private final ArrayList<filterAreaModelRecycler> dialogList = new ArrayList<>();
    private RecyclerView dialogRecyclerView;
    private filterAreaAdapter filterAreaAdapter1;
    private List<AllCitiesModel> getAllCitiesForDialog;
    private String CityId = "-1";
    private AddTinderPresenter addTinderPresenter;
    private DialogAmenities dialogAmenities;
    private DiloagLevelRealEstate diloagLevelRealEstate;
    private DiloagBedRoomslRealEstate diloagBedRoomslRealEstate;
    private DialogActivityForRealEstate dialogActivityForRealEstate;
    private AppCompatImageView imageNavigationIcon;
    private LinearLayoutCompat linearWheel;
    private LinearLayoutCompat linearActivityFor;
    private LinearLayoutCompat linearLocation;
    private MyTextView txtLocation;
    private LinearLayoutCompat linearAmentions;
    private LinearLayoutCompat linearBedrooms;
    private LinearLayoutCompat linearLevel;
    private LinearLayoutCompat linearArea;
    private MyEditText txtSpeceficArea;
    private LinearLayoutCompat linearPeriod;
    private MyEditText txtPeriod;
    private LinearLayoutCompat linearAreaRange;
    private MyEditText etRangeOne;
    private MyEditText etRangeTwo;
    private LinearLayoutCompat linearPriceRange;
    private MyEditText areaPriceOne;
    private MyEditText areaPriceTwo;
    private LinearLayoutCompat linearRadioBtns;
    private RadioGroup radioGroupManuAuto;
    private AppCompatRadioButton radioBtnYes;
    private AppCompatRadioButton radioBtnNo;
    private MyEditText etDesciption;
    private String language;
    private String typeOfProperties = String.valueOf(Constant.TYPE_OF_PROPERTIES.get(0).getId());
    private String needTo = "1";
    private String needToForRequest;
    private String typeOfForRequest;
    private String licience;
    private RealEstatePresenter realEstatePresenter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    private void setWheelStyle(WheelView.WheelViewStyle styleOne) {
        styleOne.selectedTextColor = Color.parseColor("#00609A");
        styleOne.textColor = Color.parseColor("#BABABA");
        styleOne.backgroundColor = Color.parseColor("#FFFFFF");
        styleOne.holoBorderColor = Color.parseColor("#00609A");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_state_layout);

        mProgressBarAddTender = findViewById(R.id.progressBar);

        mProgressBar = findViewById(R.id.progressBar);

        realEstatePresenter = new RealEstatePresenter(this);

        addTinderPresenter = new AddTinderPresenter(this, this);
        addTinderPresenter.getAllCities();

        SharedPreferences mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        dialogAmenities = new DialogAmenities();
        diloagLevelRealEstate = new DiloagLevelRealEstate();
        diloagBedRoomslRealEstate = new DiloagBedRoomslRealEstate();
        dialogActivityForRealEstate = new DialogActivityForRealEstate();
        initViews();

    }

    private void initViews() {
        imageNavigationIcon = findViewById(R.id.imageNavigationIcon);
        imageNavigationIcon.setOnClickListener(v -> {
            onBackPressed();
        });

        linearWheel = findViewById(R.id.linear_wheel);
        WheelView<String> wheelType = findViewById(R.id.wheel_type);
        WheelView<String> wheelNeedTo = findViewById(R.id.wheel_need_to);


        //1
        wheelType.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelType.setSkin(WheelView.Skin.Holo);

        WheelView.WheelViewStyle styleOne = new WheelView.WheelViewStyle();
        setWheelStyle(styleOne);

        wheelType.setStyle(styleOne);
        wheelType.setWheelClickable(true);

        for (int i = 0; i < Constant.TYPE_OF_PROPERTIES.size(); i++) {
            if (language.equals("ar")) {
                wheelDataType.add(Constant.TYPE_OF_PROPERTIES.get(i).getArabicName());
                Log.d("TEST_", "initViews: " + Constant.TYPE_OF_PROPERTIES.get(i).getArabicName());
            } else {
                wheelDataType.add(Constant.TYPE_OF_PROPERTIES.get(i).getEnglishName());
                Log.d("TEST_", "initViews: " + Constant.TYPE_OF_PROPERTIES.get(i).getEnglishName());
            }
        }


        wheelType.setWheelData(wheelDataType);

        wheelType.setOnWheelItemSelectedListener((position, s) -> {

            try {
                currentSelectedItems.clear();
            } catch (NullPointerException ignored) {

            }

            typeOfForRequest = Constant.TYPE_OF_PROPERTIES.get(position).getId();
            if (!needTo.equals("1")) {
                Log.d("NEED_TO", "initViews: " + needTo);
                typeOfProperties = String.valueOf(Constant.TYPE_OF_PROPERTIES.get(position).getId());
                switch (typeOfProperties) {
                    case "1":
                        //third case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearActivityFor.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);


                        linearAmentions.setVisibility(View.GONE);
                        linearBedrooms.setVisibility(View.GONE);
                        linearLevel.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);


                        break;
                    case "2":
                        //first case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearAmentions.setVisibility(View.VISIBLE);
                        linearBedrooms.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);


                        linearActivityFor.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);
                        break;
                    case "3":
                        //first case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearAmentions.setVisibility(View.VISIBLE);
                        linearBedrooms.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);

                        linearActivityFor.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);
                        break;
                    case "4":
                        //first case  - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearAmentions.setVisibility(View.VISIBLE);
                        linearBedrooms.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);

                        linearActivityFor.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);
                        break;
                    case "5":
                        //fourth case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearRadioBtns.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);

                        linearActivityFor.setVisibility(View.GONE);
                        linearAmentions.setVisibility(View.GONE);
                        linearBedrooms.setVisibility(View.GONE);
                        linearLevel.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        break;
                    case "6":
                        //second case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);


                        linearActivityFor.setVisibility(View.GONE);
                        linearAmentions.setVisibility(View.GONE);
                        linearBedrooms.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);

                        break;
                }
            }
            if (needTo.equals("1")) {

            }

            Log.d("WHEEL_TYPE", "initViews: " + typeOfProperties);
        });

        //2

        //2
        wheelNeedTo.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelNeedTo.setSkin(WheelView.Skin.Holo);


        wheelNeedTo.setStyle(styleOne);

        wheelNeedTo.setWheelClickable(true);


        for (int i = 0; i < Constant.TYPE_OF_USES.size(); i++) {
            if (language.equals("ar")) {
                wheelDataNeedTo.add(Constant.TYPE_OF_USES.get(i).getArabicName());

            } else {
                wheelDataNeedTo.add(Constant.TYPE_OF_USES.get(i).getEnglishName());

            }
        }

        wheelNeedTo.setWheelData(wheelDataNeedTo);

        wheelNeedTo.setOnWheelItemSelectedListener((position, s) -> {
            needToForRequest = Constant.TYPE_OF_USES.get(position).getId();
            needTo = Constant.TYPE_OF_USES.get(position).getId();

            try {
                currentSelectedItems.clear();
            } catch (NullPointerException ignored) {

            }

            Log.d("NEED_TO", "initViews: " + needTo);
            if (needTo.equals("1")) { //RENT

                linearAreaRange.setVisibility(View.VISIBLE);
                linearPriceRange.setVisibility(View.VISIBLE);
                linearLocation.setVisibility(View.VISIBLE);
                linearArea.setVisibility(View.VISIBLE);
                linearPeriod.setVisibility(View.VISIBLE);
                linearRadioBtns.setVisibility(View.VISIBLE);
                etDesciption.setVisibility(View.VISIBLE);

                linearLevel.setVisibility(View.GONE);
                linearActivityFor.setVisibility(View.GONE);
                linearAmentions.setVisibility(View.GONE);
                linearBedrooms.setVisibility(View.GONE);

                Log.d("NEED_TO", "initViews: " + needTo);

            } else { //BUY

                Log.d("NEED_TO", "initViews: " + needTo);
                switch (typeOfProperties) {
                    case "1":
                        //third case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearActivityFor.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);


                        linearAmentions.setVisibility(View.GONE);
                        linearBedrooms.setVisibility(View.GONE);
                        linearLevel.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);


                        break;
                    case "2":
                        //first case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearAmentions.setVisibility(View.VISIBLE);
                        linearBedrooms.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);


                        linearActivityFor.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);
                        break;
                    case "3":
                        //first case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearAmentions.setVisibility(View.VISIBLE);
                        linearBedrooms.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);

                        linearActivityFor.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);
                        break;
                    case "4":
                        //first case  - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearAmentions.setVisibility(View.VISIBLE);
                        linearBedrooms.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);

                        linearActivityFor.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);
                        break;
                    case "5":
                        //fourth case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearRadioBtns.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);

                        linearActivityFor.setVisibility(View.GONE);
                        linearAmentions.setVisibility(View.GONE);
                        linearBedrooms.setVisibility(View.GONE);
                        linearLevel.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        break;
                    case "6":
                        //second case - buy
                        linearAreaRange.setVisibility(View.VISIBLE);
                        linearLocation.setVisibility(View.VISIBLE);
                        linearArea.setVisibility(View.VISIBLE);
                        linearLevel.setVisibility(View.VISIBLE);
                        etDesciption.setVisibility(View.VISIBLE);


                        linearActivityFor.setVisibility(View.GONE);
                        linearAmentions.setVisibility(View.GONE);
                        linearBedrooms.setVisibility(View.GONE);
                        linearPeriod.setVisibility(View.GONE);
                        linearPriceRange.setVisibility(View.GONE);
                        linearRadioBtns.setVisibility(View.GONE);

                        break;
                }

            }
            Log.d("WHEEL_NEED_TO", "initViews: " + needTo);
        });
        //____________-
        linearActivityFor = findViewById(R.id.linear_activity_for);
        txtActivityFor = findViewById(R.id.txt_activity_for);
        txtActivityFor.setOnClickListener(this);

        linearLocation = findViewById(R.id.linear_location);
        txtLocation = findViewById(R.id.txt_location);
        txtLocation.setOnClickListener(this);

        linearAmentions = findViewById(R.id.linear_amentions);
        txtAmenities = findViewById(R.id.txt_amenities);
        txtAmenities.setOnClickListener(this);

        linearBedrooms = findViewById(R.id.linear_bedrooms);
        txtBedrooms = findViewById(R.id.txt_bedrooms);
        txtBedrooms.setText("1");
        txtBedrooms.setOnClickListener(this);

        linearLevel = findViewById(R.id.linear_level);
        txtLevel = findViewById(R.id.txt_level);
        txtLevel.setText("1");
        txtLevel.setOnClickListener(this);

        linearArea = findViewById(R.id.linear_area);
        txtSpeceficArea = findViewById(R.id.txt_specefic_area);

        linearPeriod = findViewById(R.id.linear_period);
        txtPeriod = findViewById(R.id.txt_period);

        linearAreaRange = findViewById(R.id.linear_area_range);
        etRangeOne = findViewById(R.id.et_range_one);
        etRangeTwo = findViewById(R.id.et_range_two);

        linearPriceRange = findViewById(R.id.linear_price_range);
        areaPriceOne = findViewById(R.id.area_price_one);
        areaPriceTwo = findViewById(R.id.area_price_two);

        linearRadioBtns = findViewById(R.id.linear_radio_btns);
        radioGroupManuAuto = findViewById(R.id.radio_group_manu_auto);
        radioBtnYes = findViewById(R.id.radio_btn_yes);
        radioBtnYes.setOnCheckedChangeListener(this);
        radioBtnYes.setChecked(true);
        radioBtnNo = findViewById(R.id.radio_btn_no);
        radioBtnNo.setChecked(false);

        etDesciption = findViewById(R.id.et_desciption);

        btnNextRealState = findViewById(R.id.btn_next);
        btnNextRealState.setOnClickListener(this);
    }

    private void show_dialigForCities() {
        AlertDialog.Builder builder1;
        final AlertDialog dialog1;
        builder1 = new AlertDialog.Builder(AddRealStateActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
        TextView header = mview.findViewById(R.id.DialogHeader);
        TextView All = mview.findViewById(R.id.All);
        All.setVisibility(View.GONE);
        if (language.equals("ar")) {
            header.setText("اختر المحافظة");
        } else {
            header.setText("Select City");
        }
        dialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new filterAreaAdapter(dialogList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

        dialogRecyclerView.setLayoutManager(linearLayoutManager);
        dialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initionilizationOFCities();
        dialogRecyclerView.setAdapter(filterAreaAdapter1);
        builder1.setView(mview);
        dialog1 = builder1.create();
        Window window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog1.show();
        filterAreaAdapter1.setOnItemClickListener(v1 -> {
            int position = dialogRecyclerView.getChildAdapterPosition(v1);
            String position1 = (String.valueOf(position));

            CityId = getAllCitiesForDialog.get(position).getId();
            txtLocation.setText(dialogList.get(position).CityName);


            dialog1.dismiss();
        });

    }

    private void initionilizationOFCities() {
        dialogList.clear();
        for (int k = 0; k < getAllCitiesForDialog.size(); k++) {
            if (language.equals("ar")) {
                dialogList.add(new filterAreaModelRecycler(
                        getAllCitiesForDialog.get(k).getArabicName()
                        , ""
                        , -1
                        , Integer.parseInt(getAllCitiesForDialog.get(k).getId())));
            } else {
                dialogList.add(new filterAreaModelRecycler(
                        getAllCitiesForDialog.get(k).getEnglishName()
                        , ""
                        , -1
                        , Integer.parseInt(getAllCitiesForDialog.get(k).getId())));
            }
        }
        filterAreaAdapter1.update(dialogList);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                Log.d("YOU_CLICK_ME", "onClick: " + " TITLE " + getIntent().getStringExtra(Constant.TITLE) + " DESC " +
                        getIntent().getStringExtra(Constant.DESC) + " CityId " +
                        getIntent().getStringExtra(Constant.CityId) + " StartDate " + getIntent().getStringExtra(StartDate) + " EndTime "
                        + getIntent().getStringExtra(Constant.EndTime) + " CatId " + getIntent().getStringExtra(Constant.CatId) + " Address " +
                        getIntent().getStringExtra(Constant.Address));

                addTinderPresenter.validationAddTinder(getIntent().getStringExtra(Constant.TITLE), getIntent().getStringExtra(Constant.DESC),
                        getIntent().getStringExtra(Constant.CityId), getIntent().getStringExtra(StartDate)
                        , getIntent().getStringExtra(Constant.EndTime), getIntent().getStringExtra(Constant.CatId), getIntent().getStringExtra(Constant.Address));
                break;
            case R.id.txt_amenities:
                dialogAmenities.show(getSupportFragmentManager(), "");
                break;
            case R.id.txt_level:
                txtLevel.setText("");
                diloagLevelRealEstate.show(getSupportFragmentManager(), "1");
                break;
            case R.id.txt_bedrooms:
                txtBedrooms.setText("");
                diloagBedRoomslRealEstate.show(getSupportFragmentManager(), "2");
                break;
            case R.id.txt_location:
                txtLocation.setText("");
                show_dialigForCities();
                break;
            case R.id.txt_activity_for:
                txtActivityFor.setText("");
                dialogActivityForRealEstate.show(getSupportFragmentManager(), "3");
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            licience = "true";
            radioBtnNo.setChecked(false);
        } else {
            licience = "false";
            radioBtnNo.setChecked(true);
        }

    }

    @Override
    public void getAllCities(List<AllCitiesModel> getAllCities) {

        getAllCitiesForDialog = getAllCities;
    }

    @Override
    public void tenderAddedSuccessfully() {

        String licenceForSend = "";

        if (linearRadioBtns.getVisibility() == View.VISIBLE) {
            try {
                currentSelectedItems.get(currentSelectedItems.size() - 1);
                realEstatePresenter.addTenderWithAmin(etRangeOne.getText().toString(), etRangeTwo.getText().toString(), areaPriceOne.getText().toString(),
                        areaPriceTwo.getText().toString(), txtPeriod.getText().toString(), txtSpeceficArea.getText().toString(), etDesciption.getText().toString(),
                        licience, txtBedrooms.getText().toString(), txtLevel.getText().toString(), CityId, txtActivityFor.getText().toString(),
                        needToForRequest, typeOfForRequest, String.valueOf(Constant.ADD_TENDER_ID), currentSelectedItems);

            } catch (NullPointerException e) {
                realEstatePresenter.addTenderWithoutAmin(etRangeOne.getText().toString(), etRangeTwo.getText().toString(), areaPriceOne.getText().toString(),
                        areaPriceTwo.getText().toString(), txtPeriod.getText().toString(), txtSpeceficArea.getText().toString(), etDesciption.getText().toString(),
                        licience, txtBedrooms.getText().toString(), txtLevel.getText().toString(), CityId, txtActivityFor.getText().toString(),
                        needToForRequest, typeOfForRequest, String.valueOf(Constant.ADD_TENDER_ID));

            }
        } else {
            try {
                currentSelectedItems.get(currentSelectedItems.size() - 1);
                realEstatePresenter.addTenderWithAmin(etRangeOne.getText().toString(), etRangeTwo.getText().toString(), areaPriceOne.getText().toString(),
                        areaPriceTwo.getText().toString(), txtPeriod.getText().toString(), txtSpeceficArea.getText().toString(), etDesciption.getText().toString(),
                        licenceForSend, txtBedrooms.getText().toString(), txtLevel.getText().toString(), CityId, txtActivityFor.getText().toString(),
                        needToForRequest, typeOfForRequest, String.valueOf(Constant.ADD_TENDER_ID), currentSelectedItems);
            } catch (NullPointerException e) {
                realEstatePresenter.addTenderWithoutAmin(etRangeOne.getText().toString(), etRangeTwo.getText().toString(), areaPriceOne.getText().toString(),
                        areaPriceTwo.getText().toString(), txtPeriod.getText().toString(), txtSpeceficArea.getText().toString(), etDesciption.getText().toString(),
                        licenceForSend, txtBedrooms.getText().toString(), txtLevel.getText().toString(), CityId, txtActivityFor.getText().toString(),
                        needToForRequest, typeOfForRequest, String.valueOf(Constant.ADD_TENDER_ID));
            }
            Log.d("DATAA__", ": " + "MIN RANE " + etRangeOne.getText().toString() + "MAX RANE " + etRangeTwo.getText().toString()
                    + "PRICE MIN " + areaPriceOne.getText().toString() +
                    "PRICE MAX " + areaPriceTwo.getText().toString() + "PERIOD  " + txtPeriod.getText().toString() +
                    "AREA : " + txtSpeceficArea.getText().toString() + "DESC " + etDesciption.getText().toString() +
                    "LICE " + licenceForSend + "BED ROOMS " + txtBedrooms.getText().toString() + "LEVEL " + txtLevel.getText().toString()
                    + "CITY ID " + CityId + "ACTIVITY FOR " + txtActivityFor.getText().toString() +
                    "NEED TO " + needToForRequest + "TYPE OF REQ " + typeOfForRequest + "TENDER ID " + (Constant.ADD_TENDER_ID) +
                    "AMENITIES" + currentSelectedItems.toString());


        }

    }
}
