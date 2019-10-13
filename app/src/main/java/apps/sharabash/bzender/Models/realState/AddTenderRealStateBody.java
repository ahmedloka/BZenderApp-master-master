package apps.sharabash.bzender.Models.realState;

import java.util.List;

public class AddTenderRealStateBody {
    private String Description;

    private String PeriodOfRenting;

    private String BedroomsCount;

    private String Tender_Id;

    private String MaxSize;

    private String MinPrice;

    private String TypeOfProperty_Id;

    private String Location_Id;

    private String MinSize;

    private String SpecificArea;

    private String ActivityFor_Id;

    private String LevelInBuilding;

    private String NeedTo_Id;

    private String Licence;

    private String MaxPrice;

    private List<Amenities> Amenities;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getPeriodOfRenting ()
    {
        return PeriodOfRenting;
    }

    public void setPeriodOfRenting (String PeriodOfRenting)
    {
        this.PeriodOfRenting = PeriodOfRenting;
    }

    public String getBedroomsCount ()
    {
        return BedroomsCount;
    }

    public void setBedroomsCount (String BedroomsCount)
    {
        this.BedroomsCount = BedroomsCount;
    }

    public String getTender_Id ()
    {
        return Tender_Id;
    }

    public void setTender_Id (String Tender_Id)
    {
        this.Tender_Id = Tender_Id;
    }

    public String getMaxSize ()
    {
        return MaxSize;
    }

    public void setMaxSize (String MaxSize)
    {
        this.MaxSize = MaxSize;
    }

    public String getMinPrice ()
    {
        return MinPrice;
    }

    public void setMinPrice (String MinPrice)
    {
        this.MinPrice = MinPrice;
    }

    public String getTypeOfProperty_Id ()
    {
        return TypeOfProperty_Id;
    }

    public void setTypeOfProperty_Id (String TypeOfProperty_Id)
    {
        this.TypeOfProperty_Id = TypeOfProperty_Id;
    }

    public String getLocation_Id ()
    {
        return Location_Id;
    }

    public void setLocation_Id (String Location_Id)
    {
        this.Location_Id = Location_Id;
    }

    public String getMinSize ()
    {
        return MinSize;
    }

    public void setMinSize (String MinSize)
    {
        this.MinSize = MinSize;
    }

    public String getSpecificArea ()
    {
        return SpecificArea;
    }

    public void setSpecificArea (String SpecificArea)
    {
        this.SpecificArea = SpecificArea;
    }

    public String getActivityFor_Id ()
    {
        return ActivityFor_Id;
    }

    public void setActivityFor_Id (String ActivityFor_Id)
    {
        this.ActivityFor_Id = ActivityFor_Id;
    }

    public String getLevelInBuilding ()
    {
        return LevelInBuilding;
    }

    public void setLevelInBuilding (String LevelInBuilding)
    {
        this.LevelInBuilding = LevelInBuilding;
    }

    public String getNeedTo_Id ()
    {
        return NeedTo_Id;
    }

    public void setNeedTo_Id (String NeedTo_Id)
    {
        this.NeedTo_Id = NeedTo_Id;
    }

    public String getLicence ()
    {
        return Licence;
    }

    public void setLicence (String Licence)
    {
        this.Licence = Licence;
    }

    public String getMaxPrice ()
    {
        return MaxPrice;
    }

    public void setMaxPrice (String MaxPrice)
    {
        this.MaxPrice = MaxPrice;
    }

    public List<Amenities> getAmenities ()
    {
        return Amenities;
    }

    public void setAmenities (List<Amenities> Amenities)
    {
        this.Amenities = Amenities;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Description = "+Description+", PeriodOfRenting = "+PeriodOfRenting+", BedroomsCount = "+BedroomsCount+", Tender_Id = "+Tender_Id+", MaxSize = "+MaxSize+", MinPrice = "+MinPrice+", TypeOfProperty_Id = "+TypeOfProperty_Id+", Location_Id = "+Location_Id+", MinSize = "+MinSize+", SpecificArea = "+SpecificArea+", ActivityFor_Id = "+ActivityFor_Id+", LevelInBuilding = "+LevelInBuilding+", NeedTo_Id = "+NeedTo_Id+", Licence = "+Licence+", MaxPrice = "+MaxPrice+", Amenities = "+Amenities+"]";
    }
}

