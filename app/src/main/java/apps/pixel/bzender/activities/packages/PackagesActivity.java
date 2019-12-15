package apps.pixel.bzender.activities.packages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

import apps.pixel.bzender.Models.ResponsePackages;
import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.ButtonBook;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.Utills.MyTextViewBold;
import apps.pixel.bzender.activities.TermsAndConditionActivity;
import apps.pixel.bzender.activities.webPaymnet.WebPaymentActivity;
import apps.pixel.bzender.adapters.AdapterPackes;

public class PackagesActivity extends AppCompatActivity implements AdapterPackes.ItemOnClickHandler, PackagesInterface, SelectedInterface {

    private RecyclerView mRV;
    private List<String> mListMoney;
    private List<String> mListPoints;
    private List<String> mListId;

    private AppCompatImageView mBack;
    private ButtonBook mBtnPay;
    private AppCompatCheckBox mCheckBox;
    private MyTextViewBold mTxtTerms;


    private PackagesPresenter packagesPresenter;
    // private PaymentPresenter paymentPresenter;
    private int selectedPosition;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateSlideLeft(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_packages);

        mTxtTerms = findViewById(R.id.txt_terms);
        mTxtTerms.setOnClickListener(v -> {
            startActivity(new Intent(this, TermsAndConditionActivity.class));
            Animatoo.animateSlideRight(this);
        });

        mCheckBox = findViewById(R.id.checkbox);

        packagesPresenter = new PackagesPresenter(this, this);
        packagesPresenter.getAllPackages();

        //paymentPresenter = new PaymentPresenter(this);

        mBtnPay = findViewById(R.id.btn_pay);

        mListMoney = new ArrayList<>();
        mListPoints = new ArrayList<>();
        mListId = new ArrayList<>();


        mBack = findViewById(R.id.imageNavigationIcon);
        mBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateSlideLeft(this);
        });

        mRV = findViewById(R.id.rv);
        mRV.setHasFixedSize(false);
        mRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void getAllPackages(List<ResponsePackages> responsePackages) {
        for (int i = 0; i < responsePackages.size(); i++) {
            mListMoney.add(responsePackages.get(i).getPrice());
            mListPoints.add(responsePackages.get(i).getPoints());
            mListId.add(responsePackages.get(i).getId());
        }

        AdapterPackes recyclerHomeAdapter = new AdapterPackes(getApplicationContext(), mListMoney, mListPoints, mListId, this);
        mRV.setAdapter(recyclerHomeAdapter);
        Constant.runLayoutAnimation(mRV);
    }

    @Override
    public void getSelectedRadioBtn(String id) {


        mBtnPay.setOnClickListener(v -> {
            Log.d("THESELECTED", "getSelectedRadioBtn: " + id);

            if (!mCheckBox.isChecked()) {
                Constant.showErrorDialog(this, getString(R.string.have_to_mark));
                return;
            }

            if (String.valueOf(id).length() < 0) {
                Constant.showErrorDialog(this, getString(R.string.please_select_package));
                return;
            }
            //paymentPresenter.pay(String.valueOf(id));
            Intent intent = new Intent(this, WebPaymentActivity.class);
            intent.putExtra(Constant.PATYMENT_URL, "http://pixelserver-001-site29.ctempurl.com/Payment/index?packageId=".trim() + id);
            startActivity(intent);
            Animatoo.animateSlideRight(this);

        });
    }
}
