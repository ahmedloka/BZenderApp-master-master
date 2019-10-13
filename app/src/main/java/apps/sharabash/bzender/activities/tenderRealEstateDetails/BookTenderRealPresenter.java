package apps.sharabash.bzender.activities.tenderRealEstateDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import apps.sharabash.bzender.Models.bookTenderRealEsate.BookTenderRealEstateBody;
import apps.sharabash.bzender.Models.bookTenderRealEsate.BookTenderRealEstateResponse;
import apps.sharabash.bzender.Network.NetworkUtil;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.ButtonBook;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyTextView;
import apps.sharabash.bzender.Utills.MyTextViewBold;
import apps.sharabash.bzender.Utills.Validation;
import apps.sharabash.bzender.activities.Home.Home;
import apps.sharabash.bzender.dialog.DialogLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static apps.sharabash.bzender.Utills.Constant.buildDialog;

public class BookTenderRealPresenter {
    private final DialogLoader dialogLoader;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;

    public BookTenderRealPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    public void validateBookTender(String price, String desc, String size, String activityFor, String bedroomsCount,
                                   String tenderId, String needTo, String specificArea, String levelInBuilding,
                                   String licence, String typeOfProperty, String location, String amenities, String periodOfRenting,
                                   String priceRange) {
        if (Validation.validateFields(price)) {
            Constant.showErrorDialog(context, context.getString(R.string.price_is_required));
        } else {
            BookTenderRealEstateBody bookTenderRealEstateBody = new BookTenderRealEstateBody();
            bookTenderRealEstateBody.setPriceRange(priceRange);
            bookTenderRealEstateBody.setPrice(price);
            bookTenderRealEstateBody.setNote(desc);
            bookTenderRealEstateBody.setSize(size);
            bookTenderRealEstateBody.setActivityFor(activityFor);
            bookTenderRealEstateBody.setBedroomsCount(bedroomsCount);
            bookTenderRealEstateBody.setTender_Id(tenderId);
            bookTenderRealEstateBody.setNeedTo(needTo);
            bookTenderRealEstateBody.setSpecificArea(specificArea);
            bookTenderRealEstateBody.setLevelInBuilding(levelInBuilding);
            bookTenderRealEstateBody.setLicence(licence);
            bookTenderRealEstateBody.setTypeOfProperty(typeOfProperty);
            bookTenderRealEstateBody.setLocation(location);
            bookTenderRealEstateBody.setAmenities(amenities);
            bookTenderRealEstateBody.setPeriodOfRenting(periodOfRenting);


            if (Validation.isConnected(context)) {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.UserID, ""))
                        .bookTenderRealEstate(bookTenderRealEstateBody)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseBooRealEstate, this::handleError));
            } else {
                buildDialog((Activity) context).show().setCanceledOnTouchOutside(false);
            }

        }
    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);


    }

    private void handleResponseBooRealEstate(BookTenderRealEstateResponse bookTenderRealEstateResponse) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        Constant.CAR_BOOKING_ID = bookTenderRealEstateResponse.getTenderRealstateBookingId();

        Constant.showSuccessTenderBookDialog(context, context.getString(R.string.success_message),
                bookTenderRealEstateResponse.getTenderRealstateBookingId()
                , Constant.REAL_ESTATE);
    }

    private void showDialogCreate() {
        MyTextViewBold txtCongrats;
        MyTextView txtDesc;
        ButtonBook mBtnOk;

        AlertDialog.Builder builder1;
        final AlertDialog dialog1;
        builder1 = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.dialog_add_tender_seccessfully, null);

        mBtnOk = mView.findViewById(R.id.btn_ok);
        txtCongrats = mView.findViewById(R.id.txt_congrats);
        txtDesc = mView.findViewById(R.id.txt_desc);

        txtCongrats.setText(context.getText(R.string.congratulations));
        txtDesc.setText(context.getText(R.string.complete_add_tinder_sec));


        builder1.setView(mView);
        builder1.setCancelable(true);

        dialog1 = builder1.create();

        Window window = dialog1.getWindow();

        window.setGravity(Gravity.CENTER);

        mBtnOk.setOnClickListener(v -> {
            dialog1.dismiss();
            Intent intent = new Intent(context, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            Animatoo.animateFade(context);
        });


        dialog1.show();


    }
}
