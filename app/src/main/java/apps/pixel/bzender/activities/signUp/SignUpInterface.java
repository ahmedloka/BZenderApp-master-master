package apps.pixel.bzender.activities.signUp;

import java.util.List;

import apps.pixel.bzender.Models.signUp.CountryCodeResponse;

public interface SignUpInterface {
    void getAllCodes(List<CountryCodeResponse> countryCodeResponseList);
}
