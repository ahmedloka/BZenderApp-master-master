package apps.pixel.bzender.activities.TenderDetails;

import apps.pixel.bzender.Models.TendersDetails.TenderDetails;
import apps.pixel.bzender.Models.TendersDetails.electrical.TenderDetailsElectrical;
import apps.pixel.bzender.Models.bookCar.BookCarResponse;
import apps.pixel.bzender.Models.bookElectrical.BookElectricalResponse;
import apps.pixel.bzender.Models.getTenderRealEstate.GetTenderRealEstateResponse;

public interface TenderDetailsInterface {
    void handleSuccess(TenderDetails tenderDetails);

    void getDetailsElectrical(TenderDetailsElectrical tenderDetailsElectrical);

    void getBookCarId(BookCarResponse bookCarResponse);

    void getElectricalId(BookElectricalResponse bookElectricalResponse);


    void getRealEstateDataTender(GetTenderRealEstateResponse getTenderRealEstateResponse);

}
