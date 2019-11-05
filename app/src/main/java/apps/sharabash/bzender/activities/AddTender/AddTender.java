package apps.sharabash.bzender.activities.AddTender;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import apps.sharabash.bzender.Models.AddTenderType;
import apps.sharabash.bzender.Models.AddTenders.AllCitiesModel;
import apps.sharabash.bzender.Models.CustomerAddress;
import apps.sharabash.bzender.Models.GetOffers;
import apps.sharabash.bzender.Models.home.getCategoryResponse;
import apps.sharabash.bzender.Models.metadataCar.CarModels;
import apps.sharabash.bzender.Models.metadataCar.CarTypes;
import apps.sharabash.bzender.Models.metadataCar.MetaDataCar;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.ButtonBook;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.DatePickerFragment;
import apps.sharabash.bzender.Utills.MyEditText;
import apps.sharabash.bzender.Utills.MyTextView;
import apps.sharabash.bzender.Utills.Validation;
import apps.sharabash.bzender.activities.Home.HomePresenter;
import apps.sharabash.bzender.activities.Home.homeInterface;
import apps.sharabash.bzender.activities.fillDataCar.FillDataCarActivity;
import apps.sharabash.bzender.activities.fillDataCar.FillDataCarInterface;
import apps.sharabash.bzender.activities.fillDataCar.FillDataCarPresenter;
import apps.sharabash.bzender.activities.fillDataElectrical.FillDataElectricalActivity;
import apps.sharabash.bzender.activities.real_state.AddRealStateActivity;
import apps.sharabash.bzender.adapters.filterAreaAdapter;
import apps.sharabash.bzender.adapters.filterAreaModelRecycler;
import apps.sharabash.bzender.dialog.CustomCallenderDialog;
import rx.subscriptions.CompositeSubscription;

import static apps.sharabash.bzender.Utills.Constant.SELECTED_TENDER_TYPE;

public class AddTender extends AppCompatActivity implements homeInterface, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, View.OnClickListener, AddTinderInterface, FillDataCarInterface { //DatePickerDialog.OnDateSetListener

    private static final String TAG = "tag";
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog customCallenderDialog;
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog customCallenderDialogTwo;
    Button btnCreate;
    CustomerAddress customerAddress;
    List<CarModels> carModelsList = new ArrayList<>();
    List<CarTypes> carTypesList = new ArrayList<>();
    int days, dayCurrent;
    //private CustomCallenderDialog customCallenderDialog, customCallenderDialogTwo;
    private Date dateStartt, dateEnddd;
    private SharedPreferences mSharedPreferences;
    private RecyclerView DialogRecyclerView;
    private filterAreaAdapter filterAreaAdapter1;
    private String language;
    private String categoryId = "-1";
    private String CityId = "-1";
    private TextView mTxtTinderType;
    private List<getCategoryResponse> getCategoryResponses1;
    private List<AllCitiesModel> getAllCitiesForDialog;
    private String selectedDateStart;
    private String getSelectedDateEnd;
    private int dateId;
    private AddTinderPresenter addTinderPresenter;
    private MyEditText mTinderTitle;
    private MyEditText mLocation;
    private MyTextView mMTxtStartTime;
    private MyTextView mMTxtEndTime;
    // FOR FILL DATA
    private MyEditText mDesc;
    private DialogFragment dialogFragment, dialogFragmentTwo;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideLeft(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_add_tinder);


//        dailogHorizantalCallender = new DailogHorizantalCallender();
//        dailogHorizantalCallender.show(getSupportFragmentManager(), "");


        final ScrollView mImgBg;
        mImgBg = findViewById(R.id.img_bg);
        mImgBg.setOnTouchListener((v, event) -> {
            Constant.hideKeyboardFrom(this, v);
            return true;
        });

        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        initView();

        CompositeSubscription mSubscriptions = new CompositeSubscription();


        MyEditText mEditTExtLocation = findViewById(R.id.location);
        Typeface font = Typeface.createFromAsset(getAssets(), "Aileron-Light.otf");
        mEditTExtLocation.setTypeface(font);

        //btnCreate = findViewById(R.id.create);
        mTxtTinderType = findViewById(R.id.tender_type);
        getCategoryResponses1 = new ArrayList<>();
        HomePresenter homePresenter = new HomePresenter(this, this);
        homePresenter.getCategory();
        addTinderPresenter.getAllCities();
//        btnCreate.setOnClickListener(v -> {
//
//        });
        mTxtTinderType.setOnClickListener(v -> show_dialig());


        // FOR FILL DATA

        FillDataCarPresenter fillDataCarPresenter = new FillDataCarPresenter(this, this);
        fillDataCarPresenter.getMetaData();

    }

    @Override
    public void handleCategoryList(List<getCategoryResponse> getCategoryResponses) {
        getCategoryResponses1 = getCategoryResponses;
    }

    @Override
    public void getAllImages(GetOffers getOffers) {

    }

    private void show_dialig() {
        AlertDialog.Builder builder1;
        final AlertDialog dialog1;
        builder1 = new AlertDialog.Builder(AddTender.this);
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
        TextView header = mview.findViewById(R.id.DialogHeader);
        TextView All = mview.findViewById(R.id.All);
        All.setVisibility(View.GONE);
        header.setText("Select Tender Type");
        if (language.equals("en")) {
            header.setText("Select Tender Type");
        } else if (language.equals("ar")) {
            header.setText("اختر نوع المناقصة");
        }
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new filterAreaAdapter(DialogList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

        DialogRecyclerView.setLayoutManager(linearLayoutManager);
        DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initionilizationOFFilter();
        DialogRecyclerView.setAdapter(filterAreaAdapter1);
        builder1.setView(mview);
        dialog1 = builder1.create();
        Window window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog1.show();
        filterAreaAdapter1.setOnItemClickListener(v1 -> {
            int position = DialogRecyclerView.getChildAdapterPosition(v1);
            Log.d(TAG, "selectedTenderType: " + position);

            Constant.SELECTED_TENDER_TYPE = position;

            categoryId = String.valueOf(DialogList.get(position).id);
            mTxtTinderType.setText(DialogList.get(position).CityName);

            dialog1.dismiss();
        });

    }


    private void show_dialigForCities() {
        AlertDialog.Builder builder1;
        final AlertDialog dialog1;
        builder1 = new AlertDialog.Builder(AddTender.this);
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
        TextView header = mview.findViewById(R.id.DialogHeader);
        TextView All = mview.findViewById(R.id.All);
        All.setVisibility(View.GONE);
        if (language.equals("ar")) {
            header.setText("اختر المحافظة");
        } else {
            header.setText("Select City");
        }
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new filterAreaAdapter(DialogList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

        DialogRecyclerView.setLayoutManager(linearLayoutManager);
        DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initionilizationOFCities();
        DialogRecyclerView.setAdapter(filterAreaAdapter1);
        builder1.setView(mview);
        dialog1 = builder1.create();
        Window window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog1.show();
        filterAreaAdapter1.setOnItemClickListener(v1 -> {
            int position = DialogRecyclerView.getChildAdapterPosition(v1);
            String position1 = (String.valueOf(position));

            CityId = getAllCitiesForDialog.get(position).getId();
            mLocation.setText(DialogList.get(position).CityName);


            dialog1.dismiss();
        });

    }

    private void initionilizationOFFilter() {
        DialogList.clear();
        List<AddTenderType> addTenderTypeList = new ArrayList<>();
        for (int k = 0; k < 3; k++) {

            if (language.equals("ar")) {
                addTenderTypeList.add(new AddTenderType(10021, "سيارات"));
                addTenderTypeList.add(new AddTenderType(10022, "الكترونيات"));
                addTenderTypeList.add(new AddTenderType(10023, "عقـارات"));


                DialogList.add(new filterAreaModelRecycler(
                        addTenderTypeList.get(k).getName()
                        , ""
                        , -1
                        , addTenderTypeList.get(k).getId()));
            } else {
                addTenderTypeList.add(new AddTenderType(10021, "Cars"));
                addTenderTypeList.add(new AddTenderType(10022, "Electronics"));
                addTenderTypeList.add(new AddTenderType(10023, "Real Estate"));


                DialogList.add(new filterAreaModelRecycler(
                        addTenderTypeList.get(k).getName()
                        , ""
                        , -1
                        , addTenderTypeList.get(k).getId()));
            }
        }
        filterAreaAdapter1.update(DialogList);


    }

    private void initionilizationOFCities() {
        DialogList.clear();
        for (int k = 0; k < getAllCitiesForDialog.size(); k++) {
            if (language.equals("ar")) {
                DialogList.add(new filterAreaModelRecycler(
                        getAllCitiesForDialog.get(k).getArabicName()
                        , ""
                        , -1
                        , Integer.parseInt(getAllCitiesForDialog.get(k).getId())));
            } else {
                DialogList.add(new filterAreaModelRecycler(
                        getAllCitiesForDialog.get(k).getEnglishName()
                        , ""
                        , -1
                        , Integer.parseInt(getAllCitiesForDialog.get(k).getId())));
            }
        }
        filterAreaAdapter1.update(DialogList);


    }

    private void initView() {

        addTinderPresenter = new AddTinderPresenter(AddTender.this, this);

        dialogFragment = new DatePickerFragment();
        dialogFragmentTwo = new DatePickerFragment();


        customCallenderDialog = CustomCallenderDialog.getInstance(this);
        customCallenderDialogTwo = CustomCallenderDialog.getInstance(this);

        customCallenderDialog.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2);
        customCallenderDialog.setThemeDark(true);
        customCallenderDialog.setOnDateSetListener(this);
        customCallenderDialog.setCancelColor(getResources().getColor(R.color.whitecolor));
        // customCallenderDialog.setDisabledDays();
        Calendar minDate = Calendar.getInstance();
        //minDate.set(Calendar.MONTH, 10);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 2;////TODO .... MADE CHANGE HERE

        days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        dayCurrent = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Log.d("TOTAL_DAYS", "initView: " + days + "__" + dayCurrent);

        Log.d(TAG, "initView: " + day);
        //int month = Calendar.getInstance().get(Calendar.YEAR) ;
        minDate.set(Calendar.DAY_OF_MONTH, day);

        // minDate.set(Calendar.MONTH, monthh);
        customCallenderDialog.setMinDate(minDate);

        int monthh = Calendar.getInstance().get(Calendar.MONTH) + 10;

        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.MONTH, monthh);
        customCallenderDialog.setMaxDate(maxDate);

        Calendar[] calendars = new Calendar[1];
        //calendars[0] = Calendar.getInstance().add(Calendar.DAY_OF_MONTH , );

//        Calendar min_date_c = Calendar.getInstance();
//        datePickerDialog.setMinDate(min_date_c);
//
//        // Setting Max Date to next 2 years
//        Calendar max_date_c = Calendar.getInstance();
//        max_date_c.set(Calendar.YEAR, Year+2);
//        datePickerDialog.setMaxDate(max_date_c);
//        //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
//        for (Calendar loopdate = ; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
//            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
//            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
//                Calendar[] disabledDays =  new Calendar[1];
//                disabledDays[0] = loopdate;
//                datePickerDialog.setDisabledDays(disabledDays);
//            }
//        }


        customCallenderDialogTwo.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2);
        customCallenderDialogTwo.setOnDateSetListener(this);
        customCallenderDialogTwo.setThemeDark(true);
        customCallenderDialogTwo.setCancelColor(getResources().getColor(R.color.whitecolor));


        AppCompatImageView mImgBack = findViewById(R.id.imageNavigationIcon);
        mImgBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateSlideLeft(this);
        });

        mTinderTitle = findViewById(R.id.tinder_title);
        MyTextView mTenderType = findViewById(R.id.tender_type);
        mLocation = findViewById(R.id.location);

        mMTxtStartTime = findViewById(R.id.mTxtStartTime);
        mMTxtEndTime = findViewById(R.id.mTxtEndTime);
        mDesc = findViewById(R.id.desc);
        ButtonBook mCreate = findViewById(R.id.create);

        mCreate.setOnClickListener(this);


        mMTxtStartTime.setClickable(true);
        mMTxtStartTime.setFocusable(false);
        mMTxtStartTime.setFocusableInTouchMode(false);

        mMTxtStartTime.setOnClickListener(this);

        mMTxtEndTime.setClickable(true);
        mMTxtEndTime.setFocusable(false);
        mMTxtEndTime.setFocusableInTouchMode(false);

        mMTxtEndTime.setOnClickListener(this);

        mLocation.setClickable(true);
        mLocation.setFocusable(false);
        mLocation.setFocusableInTouchMode(false);

        mLocation.setOnClickListener(this);

        DatePickerDialog.OnDateSetListener mStartDateListener = (view, year, month, dayOfMonth) -> {

        };

        DatePickerDialog.OnDateSetListener mEndDateListener = (view, year, month, dayOfMonth) -> {

        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                Log.d(TAG, "onClick: ");
                Log.d(TAG, "Title: " + mTinderTitle.getText().toString());
                Log.d(TAG, "desc: " + mDesc.getText().toString());
                Log.d(TAG, "startDate: " + selectedDateStart);
                Log.d(TAG, "endTime: " + getSelectedDateEnd);
                Log.d(TAG, "cityId: " + CityId);
                Log.d(TAG, "catyId: " + categoryId);
                Log.d(TAG, "address: " + mLocation.getText().toString());

                if (Validation.validateFields(mTinderTitle.getText().toString())) {
                    Constant.showErrorDialog(this, getString(R.string.title_is_empty));
                } else if (Validation.validateFields(categoryId) || categoryId.equals("-1")) {
                    Constant.showErrorDialog(this, getString(R.string.cat_id_empty));

                } else if (Validation.validateFields(CityId) || CityId.equals("-1")) {
                    Constant.showErrorDialog(this, getString(R.string.city_is_empty));

                } else if (Validation.validateFields(selectedDateStart)) {
                    Constant.showErrorDialog(this, getString(R.string.start_date_error));

                } else if (Validation.validateFields(getSelectedDateEnd)) {
                    Constant.showErrorDialog(this, getString(R.string.end_date_error));

                } else if (Validation.validateFields(mDesc.getText().toString())) {
                    Constant.showErrorDialog(this, getString(R.string.desc_error));


                } else {
                    Intent intent = null;
                    if (SELECTED_TENDER_TYPE == 0) {
                        intent = new Intent(this, FillDataCarActivity.class);
                        intent.putExtra(Constant.TITLE, mTinderTitle.getText().toString());
                        intent.putExtra(Constant.DESC, mDesc.getText().toString());
                        intent.putExtra(Constant.StartDate, selectedDateStart);
                        intent.putExtra(Constant.EndTime, getSelectedDateEnd);
                        intent.putExtra(Constant.CityId, CityId);
                        intent.putExtra(Constant.CatId, categoryId);
                        intent.putExtra(Constant.Address, mLocation.getText().toString());
                        startActivity(intent);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Animatoo.animateSlideRight(this);
                    } else if (SELECTED_TENDER_TYPE == 1) {
                        intent = new Intent(this, FillDataElectricalActivity.class);
                        intent.putExtra(Constant.TITLE, mTinderTitle.getText().toString());
                        intent.putExtra(Constant.DESC, mDesc.getText().toString());
                        intent.putExtra(Constant.StartDate, selectedDateStart);
                        intent.putExtra(Constant.EndTime, getSelectedDateEnd);
                        intent.putExtra(Constant.CityId, CityId);
                        intent.putExtra(Constant.CatId, categoryId);
                        intent.putExtra(Constant.Address, mLocation.getText().toString());
                        startActivity(intent);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Animatoo.animateSlideRight(this);
                    } else {
                        intent = new Intent(this, AddRealStateActivity.class);
                        intent.putExtra(Constant.TITLE, mTinderTitle.getText().toString());
                        intent.putExtra(Constant.DESC, mDesc.getText().toString());
                        intent.putExtra(Constant.StartDate, selectedDateStart);
                        intent.putExtra(Constant.EndTime, getSelectedDateEnd);
                        intent.putExtra(Constant.CityId, CityId);
                        intent.putExtra(Constant.CatId, categoryId);
                        intent.putExtra(Constant.Address, mLocation.getText().toString());
                        startActivity(intent);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Animatoo.animateSlideRight(this);
                    }


                }
                break;
            case R.id.location:
                //addTinderPresenter.getAllCities();
                show_dialigForCities();
                break;
            case R.id.mTxtStartTime:
                dateId = 1;
                if (!dialogFragment.isAdded()) {
                    //  dialogFragment.show(getSupportFragmentManager(), "date picker");
                    customCallenderDialog.show(getFragmentManager(), "date picker");
                } else
                    return;
                break;
            case R.id.mTxtEndTime:
                dateId = 2;
                if (!dialogFragmentTwo.isAdded())
                    if (mMTxtStartTime.getText().length() == 0) {
                        Constant.showErrorDialog(this, getString(R.string.pls_choose_start));
                    } else {
                        //dialogFragmentTwo.show(getSupportFragmentManager(), "date picker");
                        customCallenderDialogTwo.show(getFragmentManager(), "DatePickerDialog");
                    }
                else
                    return;
                break;
        }
    }


//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//        loginCalender(year, month, dayOfMonth);
//
//
//    }

    private void loginCalender(int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date choosenDate = c.getTime();

        String currentDateString = sdf.format(c.getTime());

        int y = Calendar.getInstance().get(Calendar.YEAR);
        int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int m = Calendar.getInstance().get(Calendar.MONTH);

        Date currentDate = Calendar.getInstance().getTime();


        if (currentDate.getTime() >= choosenDate.getTime()) {
            Constant.showErrorDialog(this, getString(R.string.error_date_one));
            return;
        }
        long oneDay = 86400000;
        if (currentDate.getTime() + oneDay >= choosenDate.getTime()) {
            Constant.showErrorDialog(this, getString(R.string.error_date_one));
            return;
        }
//        if (year != y) {
//            Constant.showErrorDialog(this, getString(R.string.error_date_year));
//            return;
//        }
        if (month >= m + 4) {
            Constant.showErrorDialog(this, getString(R.string.error_month));
            return;
        }
        {

            String date = sdf.format(c.getTime());

            try {
                Date endDate = null, startDate = null;
                if (dateId == 1) {
                    startDate = sdf.parse(date);
                    dateStartt = startDate;
                    String dayStr = (String) DateFormat.format("dd", startDate);
                    int day = Integer.valueOf(dayStr);
                    //Toast.makeText(this, String.valueOf(day), Toast.LENGTH_SHORT).show();

                    Calendar maxEnd = Calendar.getInstance();
                    int dayMax = day + 10;
                    maxEnd.set(Calendar.DAY_OF_MONTH, dayMax);

                    if (days == dayCurrent) {
                        int monthh = Calendar.getInstance().get(Calendar.MONTH) + 1;
                        maxEnd.set(Calendar.MONTH, monthh);
                    }

                    customCallenderDialogTwo.setMaxDate(maxEnd);


                    Calendar minEnd = Calendar.getInstance();
                    int daymin = day + 5;
                    minEnd.set(Calendar.DAY_OF_MONTH, daymin);


                    if (days == dayCurrent) {
                        int monthh = Calendar.getInstance().get(Calendar.MONTH) + 1;
                        minEnd.set(Calendar.MONTH, monthh);
                    }

                    customCallenderDialogTwo.setMinDate(minEnd);

                    mMTxtStartTime.setText(currentDateString);

                    selectedDateStart = date;
                } else {
                    endDate = sdf.parse(date);
                    dateEnddd = endDate;
                }

                Log.d(TAG, "onDateSet: " + dateStartt + "///" + dateEnddd);

                if (dateEnddd != null && dateStartt != null) {

                    if (mMTxtEndTime.getText().length() > 0 && mMTxtStartTime.getText().length() == 0) {
                        Constant.showErrorDialog(this, getString(R.string.pls_choose_start));
                        mMTxtEndTime.setText(" ");
                        return;
                    }

                    if (dateEnddd.getTime() <= dateStartt.getTime()) {

                        Constant.showErrorDialog(this, getString(R.string.error_end_date_one));
                        return;
                    }
                    long fiveDays = 432000000;
                    if (dateStartt.getTime() + fiveDays > dateEnddd.getTime()) {
                        Constant.showErrorDialog(this, getString(R.string.error_end_date_two));
                        return;
                    }

                    long tenDays = 864000000;
                    if (dateEnddd.getTime() - tenDays > dateStartt.getTime()) {
                        Constant.showErrorDialog(this, getString(R.string.error_end_date_three));
                        return;
                    }

                }
                if (dateId != 1) {

                    mMTxtEndTime.setText(currentDateString);
                    getSelectedDateEnd = date;
                }


//                } else {
//                    Constant.showErrorDialog(this, getString(R.string.pls_choose_start));
//                }

            } catch (ParseException e) {
                Log.d(TAG, "onDateSet: " + e.getMessage());

                e.printStackTrace();
            }

        }
    }


    @Override
    public void getAllCities(List<AllCitiesModel> getAllCities) {
        Log.d(TAG, "getAllCities: " + getAllCities.get(0).toString());

        getAllCitiesForDialog = getAllCities;
    }

    @Override
    public void tenderAddedSuccessfully() {

    }

    @Override
    public void getMetaCar(MetaDataCar metaDataCar) {

        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);

        Constant.AMENTITIES = metaDataCar.getAmenities();
        Constant.ACTIVITY_FOR = metaDataCar.getActivityFor();
        Constant.TYPE_OF_PROPERTIES = metaDataCar.getTypesOfProperties();
        Constant.TYPE_OF_USES = metaDataCar.getTypesOfUses();


        for (int i = 0; i < metaDataCar.getElectricalTypes().size(); i++) {
            if (language.equals("ar")) {
                Constant.wheelDataElectricalType.add(metaDataCar.getElectricalTypes().get(i));
            } else {
                Constant.wheelDataElectricalType.add(metaDataCar.getElectricalTypes().get(i));
            }

            Log.d(TAG, "TYPE__: " + Constant.wheelDataElectricalType.get(i));
        }

        for (int i = 0; i < metaDataCar.getElectricalModels().size(); i++) {
            if (language.equals("ar")) {
                Constant.wheelDataElectricalModel.add(metaDataCar.getElectricalModels().get(i));
            } else {
                Constant.wheelDataElectricalModel.add(metaDataCar.getElectricalModels().get(i));
            }
            Log.d(TAG, "MODEL__: " + Constant.wheelDataElectricalModel.get(i));
        }

        for (int i = 0; i < metaDataCar.getCarTypes().size(); i++) {
            if (language.equals("ar")) {
                Constant.wheelDataCarType.add(metaDataCar.getCarTypes().get(i));
            } else {
                Constant.wheelDataCarType.add(metaDataCar.getCarTypes().get(i));
            }

            Log.d(TAG, "TYPE__: " + Constant.wheelDataCarType.get(i));
        }

        for (int i = 0; i < metaDataCar.getCarModels().size(); i++) {
            if (language.equals("ar")) {
                Constant.wheelDataCarModel.add(metaDataCar.getCarModels().get(i));
            } else {
                Constant.wheelDataCarModel.add(metaDataCar.getCarModels().get(i));
            }
            Log.d(TAG, "MODEL__: " + Constant.wheelDataCarModel.get(i));
        }


    }


    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        loginCalender(year, monthOfYear, dayOfMonth);

        // Log.d(TAG, "onDateSet: " + "CHANGED");
        // Toast.makeText(this, "CHANGED", Toast.LENGTH_SHORT).show();
    }
}
