package apps.pixel.bzender.activities.Home;

import java.util.List;

import apps.pixel.bzender.Models.GetOffers;
import apps.pixel.bzender.Models.home.getCategoryResponse;

public interface homeInterface {
    void handleCategoryList(List<getCategoryResponse> getCategoryResponses);

    void getAllImages(GetOffers getOffers);

}
