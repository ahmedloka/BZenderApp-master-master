package apps.sharabash.bzender.Models.verify.bussniess;

public class VerifyBussniess {
    private String CommRegNum ;
    private String TaxNum ;

    public String getCommRegNum() {
        return CommRegNum;
    }

    public void setCommRegNum(String commRegNum) {
        CommRegNum = commRegNum;
    }

    public String getTaxNum() {
        return TaxNum;
    }

    public void setTaxNum(String taxNum) {
        TaxNum = taxNum;
    }

    @Override
    public String toString() {
        return "VerifyBussniess{" +
                "CommRegNum='" + CommRegNum + '\'' +
                ", TaxNum='" + TaxNum + '\'' +
                '}';
    }
}
