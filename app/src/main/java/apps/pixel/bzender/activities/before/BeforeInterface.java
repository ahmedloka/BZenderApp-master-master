package apps.pixel.bzender.activities.before;

import apps.pixel.bzender.Models.verify.bussniess.VerifyBussniessResponse;
import apps.pixel.bzender.Models.verify.invidual.VerifyInvidualResponse;

public interface BeforeInterface {
    void verfiedInvidualSuccessful(VerifyInvidualResponse verifyInvidualResponse);

    void verfiedBussniessSuccessful(VerifyBussniessResponse verifyInvidualResponse);
}
