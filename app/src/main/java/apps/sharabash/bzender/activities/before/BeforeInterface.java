package apps.sharabash.bzender.activities.before;

import apps.sharabash.bzender.Models.verify.bussniess.VerifyBussniessResponse;
import apps.sharabash.bzender.Models.verify.invidual.VerifyInvidualResponse;

public interface BeforeInterface {
    void verfiedInvidualSuccessful(VerifyInvidualResponse verifyInvidualResponse);

    void verfiedBussniessSuccessful(VerifyBussniessResponse verifyInvidualResponse);
}
