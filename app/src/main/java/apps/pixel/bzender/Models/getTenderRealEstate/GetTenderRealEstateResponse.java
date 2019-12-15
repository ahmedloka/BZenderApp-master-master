package apps.pixel.bzender.Models.getTenderRealEstate;

public class GetTenderRealEstateResponse {


    private TenderRealstate tenderRealstate;


    public TenderRealstate getTenderRealstate() {
        return tenderRealstate;
    }

    public void setTenderRealstate(TenderRealstate tenderRealstate) {
        this.tenderRealstate = tenderRealstate;
    }

    @Override
    public String toString() {
        return "GetTenderRealEstateResponse{" +
                "tenderRealstate=" + tenderRealstate +
                '}';
    }
}
