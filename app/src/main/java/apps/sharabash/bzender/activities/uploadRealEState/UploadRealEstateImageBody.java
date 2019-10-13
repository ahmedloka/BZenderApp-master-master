package apps.sharabash.bzender.activities.uploadRealEState;

public class UploadRealEstateImageBody {
    private String TenderRealstateBooking_Id;

    private String Image;

    public String getTenderRealstateBooking_Id() {
        return TenderRealstateBooking_Id;
    }

    public void setTenderRealstateBooking_Id(String TenderRealstateBooking_Id) {
        this.TenderRealstateBooking_Id = TenderRealstateBooking_Id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    @Override
    public String toString() {
        return "ClassPojo [TenderRealstateBooking_Id = " + TenderRealstateBooking_Id + ", Image = " + Image + "]";
    }
}

