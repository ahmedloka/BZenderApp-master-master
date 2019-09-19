package apps.sharabash.bzender.Models.verify.invidual;

public class VerifyInvidual {

    private String NationalID ;

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    @Override
    public String toString() {
        return "VerifyInvidual{" +
                "NationalID='" + NationalID + '\'' +
                '}';
    }
}
