package apps.sharabash.bzender.activities.before;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

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

    private MyTextView mTxtIndividual, mTxtBussiness;
    private MyTextView txtRecord;
    private MyEditText etRecord;
    private MyTextView txtCard;
    private MyEditText etCard;
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
        txtRecord = (MyTextView) findViewById(R.id.txt_record);
        etRecord = (MyEditText) findViewById(R.id.et_record);
        txtCard = (MyTextView) findViewById(R.id.txt_card);
        etCard = (MyEditText) findViewById(R.id.et_card);
        txtId = (MyTextView) findViewById(R.id.txt_id);
        etId = (MyEditText) findViewById(R.id.et_id);
        stickySwitch = (StickySwitch) findViewById(R.id.sticky_switch);
        txtBussiness = (MyTextView) findViewById(R.id.txt_bussiness);
        txtIndividual = (MyTextView) findViewById(R.id.txt_individual);

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
                etCard.setVisibility(View.GONE);
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
                beforePresenter.validateBussiness(etCard.getText().toString(), etRecord.getText().toString());
            } else {
                beforePresenter.validateInvidual(etId.getText().toString());
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
            etCard.setVisibility(View.VISIBLE);
            txtRecord.setVisibility(View.VISIBLE);
            etRecord.setVisibility(View.VISIBLE);
        } else {
            txtCard.setVisibility(View.GONE);
            etCard.setVisibility(View.GONE);
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
