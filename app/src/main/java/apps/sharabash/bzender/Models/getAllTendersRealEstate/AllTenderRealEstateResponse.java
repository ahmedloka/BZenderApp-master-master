package apps.sharabash.bzender.Models.getAllTendersRealEstate;

public class AllTenderRealEstateResponse {
    private String CategoryID;


    private String StartDateTender;


    private String TenderDescrioption;

    private String BookingCount;

    private String TenderName;

    private String OwnerUserId;

    private String CityId;


    private String StatusId;


    private String Id;

    private String EndDateTender;



    public String getCategoryID() {
        return CategoryID;
    }


    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }


    public String getStartDateTender() {
        return StartDateTender;
    }

    public void setStartDateTender(String StartDateTender) {
        this.StartDateTender = StartDateTender;
    }


    public String getTenderDescrioption() {
        return TenderDescrioption;
    }

    public void setTenderDescrioption(String TenderDescrioption) {
        this.TenderDescrioption = TenderDescrioption;
    }

    public String getBookingCount() {
        return BookingCount;
    }

    public void setBookingCount(String BookingCount) {
        this.BookingCount = BookingCount;
    }

    public String getTenderName() {
        return TenderName;
    }

    public void setTenderName(String TenderName) {
        this.TenderName = TenderName;
    }


    public String getOwnerUserId() {
        return OwnerUserId;
    }

    public void setOwnerUserId(String OwnerUserId) {
        this.OwnerUserId = OwnerUserId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String CityId) {
        this.CityId = CityId;
    }
    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String StatusId) {
        this.StatusId = StatusId;
    }


    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }


    public String getEndDateTender() {
        return EndDateTender;
    }

    public void setEndDateTender(String EndDateTender) {
        this.EndDateTender = EndDateTender;
    }

    @Override
    public String toString() {
        return "AllTenderRealEstateResponse{" +
                "CategoryID='" + CategoryID + '\'' +
                ", StartDateTender='" + StartDateTender + '\'' +
                ", TenderDescrioption='" + TenderDescrioption + '\'' +
                ", BookingCount='" + BookingCount + '\'' +
                ", TenderName='" + TenderName + '\'' +
                ", OwnerUserId='" + OwnerUserId + '\'' +
                ", CityId='" + CityId + '\'' +
                ", StatusId='" + StatusId + '\'' +
                ", Id='" + Id + '\'' +
                ", EndDateTender='" + EndDateTender + '\'' +
                '}';
    }
}

