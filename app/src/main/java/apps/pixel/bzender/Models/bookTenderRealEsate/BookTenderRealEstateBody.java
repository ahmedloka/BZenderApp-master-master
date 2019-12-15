package apps.pixel.bzender.Models.bookTenderRealEsate;

public class BookTenderRealEstateBody {
    private String PeriodOfRenting;

    private String Size;

    private String ActivityFor;

    private String BedroomsCount;

    private String Tender_Id;

    private String NeedTo;

    private String PriceRange ;

    private String SpecificArea;

    private String Price;

    private String LevelInBuilding;

    private String Note;

    private String Licence;

    private String TypeOfProperty;

    private String Location;

    private String Amenities;

    public String getPriceRange() {
        return PriceRange;
    }

    public void setPriceRange(String priceRange) {
        PriceRange = priceRange;
    }

    public String getPeriodOfRenting() {
        return PeriodOfRenting;
    }

    public void setPeriodOfRenting(String PeriodOfRenting) {
        this.PeriodOfRenting = PeriodOfRenting;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getActivityFor() {
        return ActivityFor;
    }

    public void setActivityFor(String ActivityFor) {
        this.ActivityFor = ActivityFor;
    }

    public String getBedroomsCount() {
        return BedroomsCount;
    }

    public void setBedroomsCount(String BedroomsCount) {
        this.BedroomsCount = BedroomsCount;
    }

    public String getTender_Id() {
        return Tender_Id;
    }

    public void setTender_Id(String Tender_Id) {
        this.Tender_Id = Tender_Id;
    }

    public String getNeedTo() {
        return NeedTo;
    }

    public void setNeedTo(String NeedTo) {
        this.NeedTo = NeedTo;
    }

    public String getSpecificArea() {
        return SpecificArea;
    }

    public void setSpecificArea(String SpecificArea) {
        this.SpecificArea = SpecificArea;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getLevelInBuilding() {
        return LevelInBuilding;
    }

    public void setLevelInBuilding(String LevelInBuilding) {
        this.LevelInBuilding = LevelInBuilding;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getLicence() {
        return Licence;
    }

    public void setLicence(String Licence) {
        this.Licence = Licence;
    }

    public String getTypeOfProperty() {
        return TypeOfProperty;
    }

    public void setTypeOfProperty(String TypeOfProperty) {
        this.TypeOfProperty = TypeOfProperty;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getAmenities() {
        return Amenities;
    }

    public void setAmenities(String Amenities) {
        this.Amenities = Amenities;
    }

    @Override
    public String toString() {
        return "ClassPojo [PeriodOfRenting = " + PeriodOfRenting + ", Size = " + Size + ", ActivityFor = " + ActivityFor + ", BedroomsCount = " + BedroomsCount + ", Tender_Id = " + Tender_Id + ", NeedTo = " + NeedTo + ", SpecificArea = " + SpecificArea + ", Price = " + Price + ", LevelInBuilding = " + LevelInBuilding + ", Note = " + Note + ", Licence = " + Licence + ", TypeOfProperty = " + TypeOfProperty + ", Location = " + Location + ", Amenities = " + Amenities + "]";
    }
}

