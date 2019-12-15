package apps.pixel.bzender.activities.Tenders;

import java.util.List;

import apps.pixel.bzender.Models.AddTenders.TendersModelResponse;
import apps.pixel.bzender.Models.allTinders.car.AllTender;
import apps.pixel.bzender.Models.allTinders.electrical.AllTenderElectrical;
import apps.pixel.bzender.Models.getAllTendersRealEstate.AllTenderRealEstateResponse;

interface TendersInterface {
    void handleCategoryList(List<TendersModelResponse> tendersModelResponses);

    void getAllTenderList(List<AllTender> allTenders);

    void getAllTenderElectrical(List<AllTenderElectrical> allTenderElectricals);

    void getAllTenderRealEstate(List<AllTenderRealEstateResponse> allTenderRealEstateResponses);


}
