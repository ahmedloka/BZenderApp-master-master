package apps.pixel.bzender.activities.AddTender;

import java.util.List;

import apps.pixel.bzender.Models.AddTenders.AllCitiesModel;

public interface AddTinderInterface {

    void getAllCities(List<AllCitiesModel> getAllCities);

    void tenderAddedSuccessfully(int tenderId);
}
