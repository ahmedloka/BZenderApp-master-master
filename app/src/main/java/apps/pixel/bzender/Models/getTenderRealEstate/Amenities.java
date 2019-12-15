package apps.pixel.bzender.Models.getTenderRealEstate;


public class Amenities {
    private String Id;

    private String[] TenderRealstate;

    private String EnglishName;

    private String ArabicName;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String[] getTenderRealstate() {
        return TenderRealstate;
    }

    public void setTenderRealstate(String[] TenderRealstate) {
        this.TenderRealstate = TenderRealstate;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String EnglishName) {
        this.EnglishName = EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public void setArabicName(String ArabicName) {
        this.ArabicName = ArabicName;
    }

    @Override
    public String toString() {
        return "ClassPojo [Id = " + Id + ", TenderRealstate = " + TenderRealstate + ", EnglishName = " + EnglishName + ", ArabicName = " + ArabicName + "]";
    }
}

