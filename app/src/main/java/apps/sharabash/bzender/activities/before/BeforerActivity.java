package apps.sharabash.bzender.activities.before;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import apps.sharabash.bzender.Models.verify.bussniess.VerifyBussniessResponse;
import apps.sharabash.bzender.Models.verify.invidual.VerifyInvidualResponse;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.ButtonBook;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyEditText;
import apps.sharabash.bzender.Utills.MyTextView;
import apps.sharabash.bzender.activities.AddTender.AddTender;
import apps.sharabash.bzender.activities.Tenders.AllTenderActivity;
import io.ghyeok.stickyswitch.widget.StickySwitch;

public class BeforerActivity extends AppCompatActivity implements BeforeInterface {


    public static ProgressBar mProgressBar;
    private MyEditText etOne;
    private MyEditText etTwo;
    private MyEditText etThree;

    private String numOne, numTwo, numThree;

    private MyTextView mTxtIndividual, mTxtBussiness;
    private MyTextView txtRecord;
    private MyEditText etRecord;
    private MyTextView txtCard;
    private MyTextView txtId;
    private MyEditText etId;
    private StickySwitch stickySwitch;
    private MyTextView txtBussiness;
    private MyTextView txtIndividual;
    private AppCompatImageView mBack;
    private SharedPreferences sharedPreferences;

    private String invidual, bussines;

    private BeforePresenter beforePresenter;

    private ButtonBook mBtnNext;

    private int bussiness = 1;

    private String fromWhere;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateSlideDown(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before);
        mProgressBar = findViewById(R.id.progress_bar);
        sharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        invidual = sharedPreferences.getString(Constant.INVIDUAL_PERSON, "");
        bussines = sharedPreferences.getString(Constant.BUSSINESS_PERSON, "");
        fromWhere = sharedPreferences.getString(Constant.FROM, "");


        beforePresenter = new BeforePresenter(this, this);

        Log.d("TYPE  ", "onCreate: " + "invidual" + invidual);
        Log.d("TYPE  ", "onCreate: " + "bussines" + bussines);

        if (invidual.equals("true".trim()) && bussines.equals("true".trim())) {
            Intent intent;
            if (fromWhere.equals("add".trim())) {
                intent = new Intent(this, AddTender.class);
            } else {
                intent = new Intent(this, AllTenderActivity.class);
            }
            startActivity(intent);
            Animatoo.animateSlideRight(this);
            finish();
        }

        initViews();
    }

    private void initViews() {
        mBack = findViewById(R.id.imageNavigationIcon);
        mBack.setOnClickListener(v -> {
            onBackPressed();
        });

        etOne = findViewById(R.id.et_card_one);
        etTwo = findViewById(R.id.et_two);
        etThree = findViewById(R.id.et_three);

        etOne.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etOne.length() == 3) {

                    etOne.clearFocus();
                    etTwo.requestFocus();
                    etTwo.setCursorVisible(true);

                    numOne = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        etTwo.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etTwo.length() == 3) {

                    etTwo.clearFocus();
                    etThree.requestFocus();
                    etThree.setCursorVisible(true);

                    numTwo = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        etThree.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etThree.length() == 3) {

                    etThree.clearFocus();

                    numThree = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });


        txtRecord = findViewById(R.id.txt_record);
        etRecord = findViewById(R.id.et_record);
        txtCard = findViewById(R.id.txt_card);
        txtId = findViewById(R.id.txt_id);
        etId = findViewById(R.id.et_id);
        stickySwitch = findViewById(R.id.sticky_switch);
        txtBussiness = findViewById(R.id.txt_bussiness);
        txtIndividual = findViewById(R.id.txt_individual);

        mBtnNext = findViewById(R.id.next);
        StickySwitch mSwitch = findViewById(R.id.sticky_switch);

        mTxtBussiness = findViewById(R.id.txt_bussiness);
        mTxtIndividual = findViewById(R.id.txt_individual);


        mTxtBussiness.setTextColor(getResources().getColor(android.R.color.white));
        mTxtBussiness.setTextSize(14);

        stickySwitch.setDirection(StickySwitch.Direction.LEFT);
        handleLeftSwitch();


        mSwitch.setOnSelectedChangeListener((direction, s) -> {


            if (direction == StickySwitch.Direction.LEFT) {
                bussiness = 1;

                Log.d("TEST", "onSelectedChange: " + s + direction);

                txtId.setVisibility(View.GONE);
                etId.setVisibility(View.GONE);

                handleLeftSwitch();


                mTxtBussiness.setTextColor(getResources().getColor(android.R.color.white));
                mTxtIndividual.setTextColor(getResources().getColor(R.color.graycolor));

                mTxtBussiness.setTextSize(14);
                mTxtIndividual.setTextSize(12);
            } else {

                bussiness = 0;

                handleRightSwitch();

                txtCard.setVisibility(View.GONE);
                etOne.setVisibility(View.GONE);
                etTwo.setVisibility(View.GONE);
                etThree.setVisibility(View.GONE);
                txtRecord.setVisibility(View.GONE);
                etRecord.setVisibility(View.GONE);


                Log.d("TEST", "onSelectedChange: " + s + direction);
                mTxtIndividual.setTextColor(getResources().getColor(android.R.color.white));
                mTxtBussiness.setTextColor(getResources().getColor(R.color.graycolor));

                mTxtIndividual.setTextSize(14);
                mTxtBussiness.setTextSize(12);
            }
        });

        mBtnNext.setOnClickListener(v -> {


            if (bussiness == 1) {
                if (numOne.length() != 3 && numTwo.length() != 3 && numThree.length() != 3 && etId.getVisibility() == View.VISIBLE) {
                    Constant.showErrorDialog(this, getString(R.string.pls_check_Tax));
                } else {
                    beforePresenter.validateBussiness(numOne + " - " + numTwo + " - " + numThree, etRecord.getText().toString());
                }
            } else {
                if (etId.getText().toString().length() != 14 && etId.getVisibility() == View.VISIBLE) {
                    Constant.showErrorDialog(this, getString(R.string.check_id));
                } else {
                    beforePresenter.validateInvidual(etId.getText().toString());
                }
            }

        });

    }

    private void handleRightSwitch() {
        if (invidual.equals("false")) {
            txtId.setVisibility(View.VISIBLE);
            etId.setVisibility(View.VISIBLE);
        } else {
            txtId.setVisibility(View.GONE);
            etId.setVisibility(View.GONE);
        }
    }

    private void handleLeftSwitch() {
        if (bussines.equals("false")) {
            txtCard.setVisibility(View.VISIBLE);
            etOne.setVisibility(View.VISIBLE);
            etTwo.setVisibility(View.VISIBLE);
            etThree.setVisibility(View.VISIBLE);
            txtRecord.setVisibility(View.VISIBLE);
            etRecord.setVisibility(View.VISIBLE);
        } else {
            txtCard.setVisibility(View.GONE);
            etOne.setVisibility(View.GONE);
            etTwo.setVisibility(View.GONE);
            etThree.setVisibility(View.GONE);
            txtRecord.setVisibility(View.GONE);
            etRecord.setVisibility(View.GONE);
        }
    }


    @Override
    public void verfiedInvidualSuccessful(VerifyInvidualResponse verifyInvidualResponse) {
        Log.d("RESPONSE_", "verfiedInvidualSuccessful: " + verifyInvidualResponse.getMessage());
    }

    @Override
    public void verfiedBussniessSuccessful(VerifyBussniessResponse verifyBussniessResponse) {
        Log.d("RESPONSE_", "verfiedInvidualSuccessful: " + verifyBussniessResponse.getMessage());
    }
}
