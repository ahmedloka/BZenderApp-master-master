package apps.sharabash.bzender.activities.tenderRealEstateDetails;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import apps.sharabash.bzender.Models.TendersDetails.TenderDetails;
import apps.sharabash.bzender.Models.TendersDetails.electrical.TenderDetailsElectrical;
import apps.sharabash.bzender.Models.bookCar.BookCarResponse;
import apps.sharabash.bzender.Models.bookElectrical.BookElectricalResponse;
import apps.sharabash.bzender.Models.getTenderRealEstate.GetTenderRealEstateResponse;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.ButtonBook;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyEditText;
import apps.sharabash.bzender.Utills.MyTextView;
import apps.sharabash.bzender.activities.TenderDetails.TenderDetailsInterface;
import apps.sharabash.bzender.activities.TenderDetails.TenderDetailsPresenter;

public class TenderRealEstateDetails extends AppCompatActivity implements View.OnClickListener, TenderDetailsInterface {


    private String language;

    private String bedroomsCount;
    private String needTo;
    private String specificArea;
    private String levelInBuilding;
    private String licence;
    private String typeOfProperty;
    private String location;
    private String amenities;
    private String size;
    private String activityFor;
    private String priceRange;
    private String periodOfRenting;
    private BookTenderRealPresenter bookTenderRealPresenter;
    private String tenderId;
    private MyTextView txtNeedTo;
    private MyTextView txtTypeOfProperties;
    private MyTextView txtActiviyFor;
    private MyTextView txtLocation;
    private MyTextView txtSpeseficArea;
    private MyTextView txtAminities;
    private MyTextView txtBedRooms;
    private MyTextView txtLevel;
    private MyTextView txtAreaRange;
    private MyTextView txtPriceRange;
    private MyTextView txtPeriodOfRenting;
    private MyTextView txtLicience;
    private AppCompatCheckBox checkboxNeedTo;
    private AppCompatCheckBox checkboxTypeOfProperties;
    private AppCompatCheckBox checkboxActivityFor;
    private AppCompatCheckBox checkboxLocation;
    private AppCompatCheckBox checkboxSpecficArea;
    private AppCompatCheckBox checkboxAmenities;
    private AppCompatCheckBox checkboxBedrooms;
    private AppCompatCheckBox checkboxLevel;
    private AppCompatCheckBox checkboxAreaRange;
    private AppCompatCheckBox checkboxPriceRange;
    private AppCompatCheckBox checkboxPeriodOfRenting;
    private AppCompatCheckBox checkboxLicence;

    private MyEditText etPrice;
    private MyEditText etDesc;
    private ButtonBook btnBook;
    private AppCompatImageView imageNavigationIcon;

    private TenderDetailsPresenter tenderDetailsPresenter;

    private SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_real_estate_details);

        sharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        language = sharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());

        tenderDetailsPresenter = new TenderDetailsPresenter(this, this);
        tenderId = getIntent().getStringExtra(Constant.TENDER_ID);

        tenderDetailsPresenter.getTenderDetailsRealEstate(tenderId);


        bedroomsCount = "false";
        needTo = "false";
        specificArea = "false";
        levelInBuilding = "false";
        licence = "false";
        typeOfProperty = "false";
        location = "false";
        amenities = "false";
        size = "false";
        activityFor = "false";
        periodOfRenting = "false";
        priceRange = "false";

        bookTenderRealPresenter = new BookTenderRealPresenter(this);
        initViews();

    }

    private void initViews() {
        txtNeedTo = findViewById(R.id.txt_need_to);
        txtTypeOfProperties = findViewById(R.id.txt_type_of_properties);
        txtActiviyFor = findViewById(R.id.txt_activiy_for);
        txtLocation = findViewById(R.id.txt_location);
        txtSpeseficArea = findViewById(R.id.txt_spesefic_area);
        txtAminities = findViewById(R.id.txt_aminities);
        txtBedRooms = findViewById(R.id.txt_bed_rooms);
        txtLevel = findViewById(R.id.txt_level);
        txtAreaRange = findViewById(R.id.txt_area_range);
        txtPriceRange = findViewById(R.id.txt_price_range);
        txtPeriodOfRenting = findViewById(R.id.txt_period_of_renting);
        txtLicience = findViewById(R.id.txt_licience);

        checkboxNeedTo = findViewById(R.id.checkbox_need_to);
        checkboxNeedTo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                needTo = "true";
            } else {
                needTo = "false";
            }
        });

        checkboxTypeOfProperties = findViewById(R.id.checkbox_type_of_properties);
        checkboxTypeOfProperties.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                typeOfProperty = "true";
            } else {
                typeOfProperty = "false";
            }
        });

        checkboxActivityFor = findViewById(R.id.checkbox_activity_for);
        checkboxActivityFor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                activityFor = "true";
            } else {
                activityFor = "false";
            }
        });

        checkboxLocation = findViewById(R.id.checkbox_location);
        checkboxLocation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                location = "true";
            } else {
                location = "false";
            }
        });

        checkboxSpecficArea = findViewById(R.id.checkbox_specfic_area);
        checkboxSpecficArea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                specificArea = "true";
            } else {
                specificArea = "false";
            }
        });

        checkboxAmenities = findViewById(R.id.checkbox_amenities);
        checkboxAmenities.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                amenities = "true";
            } else {
                amenities = "false";
            }
        });

        checkboxBedrooms = findViewById(R.id.checkbox_bedrooms);
        checkboxBedrooms.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bedroomsCount = "true";
            } else {
                bedroomsCount = "false";
            }
        });

        checkboxLevel = findViewById(R.id.checkbox_level);
        checkboxLevel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                levelInBuilding = "true";
            } else {
                levelInBuilding = "false";
            }
        });

        checkboxAreaRange = findViewById(R.id.checkbox_area_range);
        checkboxAreaRange.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                specificArea = "true";
            } else {
                specificArea = "false";
            }
        });

        checkboxPriceRange = findViewById(R.id.checkbox_price_range);
        checkboxPriceRange.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                priceRange = "true";
            } else {
                priceRange = "false";
            }
        });

        checkboxPeriodOfRenting = findViewById(R.id.checkbox_period_of_renting);
        checkboxPeriodOfRenting.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                periodOfRenting = "true";
            } else {
                periodOfRenting = "false";
            }
        });

        checkboxLicence = findViewById(R.id.checkbox_licence);
        checkboxLicence.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                licence = "true";
            } else {
                licence = "false";
            }
        });

        etPrice = findViewById(R.id.et_price);
        etDesc = findViewById(R.id.et_desc);
        btnBook = findViewById(R.id.btn_book);
        btnBook.setOnClickListener(this);
        imageNavigationIcon = findViewById(R.id.imageNavigationIcon);
        imageNavigationIcon.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_book:
                bookTenderRealPresenter.validateBookTender(etPrice.getText().toString(), etDesc.getText().toString(),
                        size, activityFor, bedroomsCount, tenderId, needTo
                        , specificArea, levelInBuilding, licence, typeOfProperty, location, amenities, periodOfRenting, priceRange);
                break;
        }
    }

    @Override
    public void handleSuccess(TenderDetails tenderDetails) {

    }

    @Override
    public void getDetailsElectrical(TenderDetailsElectrical tenderDetailsElectrical) {

    }

    @Override
    public void getBookCarId(BookCarResponse bookCarResponse) {

    }

    @Override
    public void getElectricalId(BookElectricalResponse bookElectricalResponse) {

    }

    @Override
    public void getRealEstateDataTender(GetTenderRealEstateResponse getTenderRealEstateResponse) {

        try {
            if (language.equals("ar")) {
                if (!getTenderRealEstateResponse.getTenderRealstate().getTypeOfUse().getArabicName().equals("null") || !getTenderRealEstateResponse.getTenderRealstate().getTypeOfUse().getArabicName().equals("")) {
                    txtNeedTo.append("  " + getTenderRealEstateResponse.getTenderRealstate().getTypeOfUse().getArabicName() + "  ");
                } else {
                    txtNeedTo.append("  " + getTenderRealEstateResponse.getTenderRealstate().getTypeOfUse().getEnglishName() + "  ");
                }
            } else {
                txtNeedTo.setVisibility(View.GONE);
                checkboxNeedTo.setVisibility(View.GONE);
            }

        } catch (NullPointerException ignored) {
            txtNeedTo.setVisibility(View.GONE);
            checkboxNeedTo.setVisibility(View.GONE);
        }

        try {
            if (!getTenderRealEstateResponse.getTenderRealstate().getTypeOfProperty().getArabicName().equals("null") || !getTenderRealEstateResponse.getTenderRealstate().getTypeOfProperty().getArabicName().equals("")) {
                if (language.equals("ar")) {
                    txtTypeOfProperties.append("  " + getTenderRealEstateResponse.getTenderRealstate().getTypeOfProperty().getArabicName() + "  ");
                } else {
                    txtTypeOfProperties.append("  " + getTenderRealEstateResponse.getTenderRealstate().getTypeOfProperty().getEnglishName() + "  ");
                }
            } else {
                txtTypeOfProperties.setVisibility(View.GONE);
                checkboxTypeOfProperties.setVisibility(View.GONE);
            }

        } catch (NullPointerException ignored) {
            txtTypeOfProperties.setVisibility(View.GONE);
            checkboxTypeOfProperties.setVisibility(View.GONE);
        }

        try {
            if (!getTenderRealEstateResponse.getTenderRealstate().getActivityFor().getArabicName().equals("") || !getTenderRealEstateResponse.getTenderRealstate().getActivityFor().getArabicName().equals("null")) {
                if (language.equals("ar")) {
                    txtActiviyFor.append(" " + getTenderRealEstateResponse.getTenderRealstate().getActivityFor().getArabicName() + "  ");
                } else {
                    txtActiviyFor.append(" " + getTenderRealEstateResponse.getTenderRealstate().getActivityFor().getEnglishName() + "  ");
                }
            } else {
                txtActiviyFor.setVisibility(View.GONE);
                checkboxActivityFor.setVisibility(View.GONE);
            }

        } catch (NullPointerException ignored) {
            txtActiviyFor.setVisibility(View.GONE);
            checkboxActivityFor.setVisibility(View.GONE);
        }


        try {
            if (!getTenderRealEstateResponse.getTenderRealstate().getCity().getArabicName().equals("") || !getTenderRealEstateResponse.getTenderRealstate().getCity().getArabicName().equals("null")) {


                if (language.equals("ar")) {
                    txtLocation.append(" " + getTenderRealEstateResponse.getTenderRealstate().getCity().getArabicName() + "  ");
                } else {
                    txtLocation.append(" " + getTenderRealEstateResponse.getTenderRealstate().getCity().getEnglishName() + "  ");
                }
            } else {
                txtLocation.setVisibility(View.GONE);
                checkboxLicence.setVisibility(View.GONE);
            }

        } catch (NullPointerException ignored) {
            txtLocation.setVisibility(View.GONE);
            checkboxLicence.setVisibility(View.GONE);
        }


        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getSpecificArea().equals("null") || getTenderRealEstateResponse.getTenderRealstate().getSpecificArea().equals("")) {
                txtSpeseficArea.setVisibility(View.GONE);
                checkboxSpecficArea.setVisibility(View.GONE);
            } else
                txtSpeseficArea.append(" " + getTenderRealEstateResponse.getTenderRealstate().getSpecificArea() + "  ");

        } catch (NullPointerException ignored) {
            txtSpeseficArea.setVisibility(View.GONE);
            checkboxSpecficArea.setVisibility(View.GONE);
        }

        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getAmenities().get(0).getArabicName().equals("") || getTenderRealEstateResponse.getTenderRealstate().getAmenities().get(0).getArabicName().equals("null")) {
                txtAminities.setVisibility(View.GONE);
                checkboxAmenities.setVisibility(View.GONE);
            } else {
                for (int i = 0; i < getTenderRealEstateResponse.getTenderRealstate().getAmenities().size(); i++) {
                    if (language.equals("ar")) {
                        txtAminities.append(" " + getTenderRealEstateResponse.getTenderRealstate().getAmenities().get(i).getArabicName() + " , ");
                    } else {
                        txtAminities.append(" " + getTenderRealEstateResponse.getTenderRealstate().getAmenities().get(i).getEnglishName() + " , ");
                    }
                }
            }
        } catch (NullPointerException ignored) {
            txtAminities.setVisibility(View.GONE);
            checkboxAmenities.setVisibility(View.GONE);
        }

        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getBedroomsCount().equals("null") || getTenderRealEstateResponse.getTenderRealstate().getBedroomsCount().equals("")) {
                txtBedRooms.setVisibility(View.GONE);
                checkboxBedrooms.setVisibility(View.GONE);
            } else
                txtBedRooms.append(" " + getTenderRealEstateResponse.getTenderRealstate().getBedroomsCount() + "  ");

        } catch (NullPointerException ignored) {
            txtBedRooms.setVisibility(View.GONE);
            checkboxBedrooms.setVisibility(View.GONE);
        }

        try {

            if (getTenderRealEstateResponse.getTenderRealstate().getLevelInBuilding().equals("") || getTenderRealEstateResponse.getTenderRealstate().getLevelInBuilding().equals("null")) {
                txtLevel.setVisibility(View.GONE);
                checkboxLevel.setVisibility(View.GONE);
            } else
                txtLevel.append(" " + getTenderRealEstateResponse.getTenderRealstate().getLevelInBuilding() + "  ");

        } catch (NullPointerException ignored) {
            txtLevel.setVisibility(View.GONE);
            checkboxLevel.setVisibility(View.GONE);
        }

        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getMinSize().equals("0.0") && getTenderRealEstateResponse.getTenderRealstate().getMaxSize().equals("0.0") || getTenderRealEstateResponse.getTenderRealstate().getMaxSize().equals("null") || getTenderRealEstateResponse.getTenderRealstate().getMinSize().equals("null")) {
                txtAreaRange.setVisibility(View.GONE);
                checkboxAreaRange.setVisibility(View.GONE);
            } else
                txtAreaRange.append(" " + getTenderRealEstateResponse.getTenderRealstate().getMinSize() + "  :  " + getTenderRealEstateResponse.getTenderRealstate().getMaxSize() + " ");
        } catch (NullPointerException ignored) {
            txtAreaRange.setVisibility(View.GONE);
            checkboxAreaRange.setVisibility(View.GONE);
        }

        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getMinPrice().equals("0.0") && getTenderRealEstateResponse.getTenderRealstate().getMaxPrice().equals("0.0") || getTenderRealEstateResponse.getTenderRealstate().getMaxPrice().equals("null") || getTenderRealEstateResponse.getTenderRealstate().getMinPrice().equals("null")) {
                txtPriceRange.setVisibility(View.GONE);
                checkboxPriceRange.setVisibility(View.GONE);
            } else
                txtPriceRange.append(" " + getTenderRealEstateResponse.getTenderRealstate().getMinPrice() + "  :  " + getTenderRealEstateResponse.getTenderRealstate().getMaxPrice() + " ");
        } catch (NullPointerException ignorfed) {
            txtPriceRange.setVisibility(View.GONE);
            checkboxPriceRange.setVisibility(View.GONE);
        }

        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getPeriodOfRenting().equals("") || getTenderRealEstateResponse.getTenderRealstate().getPeriodOfRenting().equals("null")) {
                txtPeriodOfRenting.setVisibility(View.GONE);
                checkboxPeriodOfRenting.setVisibility(View.GONE);
            } else
                txtPeriodOfRenting.append(" " + getTenderRealEstateResponse.getTenderRealstate().getPeriodOfRenting() + "  ");
        } catch (NullPointerException ignored) {
            txtPeriodOfRenting.setVisibility(View.GONE);
            checkboxPeriodOfRenting.setVisibility(View.GONE);
        }


        try {
            if (getTenderRealEstateResponse.getTenderRealstate().getLicence().equals("null") || getTenderRealEstateResponse.getTenderRealstate().getLicence().equals("")) {
                txtLicience.setVisibility(View.GONE);
                checkboxLicence.setVisibility(View.GONE);
            } else {
                txtLicience.append(" " + getTenderRealEstateResponse.getTenderRealstate().getLicence() + "  ");
            }
        } catch (NullPointerException ignored) {
            txtLicience.setVisibility(View.GONE);
            checkboxLicence.setVisibility(View.GONE);
        }

        //licience
        Log.d("SUCCESS_DATA", "getRealEstateDataTender: " + getTenderRealEstateResponse.getTenderRealstate().toString());
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
        Log.d("SUCCESS_DATA", "getRealEstateDataTender: " + "SUCCESS");
    }
}
