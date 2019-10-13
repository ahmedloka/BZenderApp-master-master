package apps.sharabash.bzender.activities.TenderDetails;

import apps.sharabash.bzender.Models.TendersDetails.TenderDetails;
import apps.sharabash.bzender.Models.TendersDetails.electrical.TenderDetailsElectrical;
import apps.sharabash.bzender.Models.bookCar.BookCarResponse;
import apps.sharabash.bzender.Models.bookElectrical.BookElectricalResponse;
import apps.sharabash.bzender.Models.getTenderRealEstate.GetTenderRealEstateResponse;

public interface TenderDetailsInterface {
    void handleSuccess(TenderDetails tenderDetails);

    void getDetailsElectrical(TenderDetailsElectrical tenderDetailsElectrical);

    void getBookCarId(BookCarResponse bookCarResponse);

    void getElectricalId(BookElectricalResponse bookElectricalResponse);


    void getRealEstateDataTender(GetTenderRealEstateResponse getTenderRealEstateResponse);

}
