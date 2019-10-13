package apps.sharabash.bzender.Models.getTenderRealEstate;


import java.util.Arrays;
import java.util.List;

import apps.sharabash.bzender.Models.metadataCar.ActivityFor;
import apps.sharabash.bzender.Models.metadataCar.TypesOfUses;

public class TenderRealstate
{
    private String Description;

    private String PeriodOfRenting;

    private apps.sharabash.bzender.Models.metadataCar.ActivityFor ActivityFor;

    private String BedroomsCount;

    private TypeOfUse TypeOfUse;


    private String MaxSize;

    private String MinPrice;

    private String MinSize;

    private String SpecificArea;

    private City City;


    private String LevelInBuilding;

    private String Licence;

    private String Id;

    private TypeOfProperty TypeOfProperty;

    private String MaxPrice;

    public apps.sharabash.bzender.Models.getTenderRealEstate.City getCity() {
        return City;
    }

    public void setCity(apps.sharabash.bzender.Models.getTenderRealEstate.City city) {
        City = city;
    }

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

    public ActivityFor getActivityFor ()
{
    return ActivityFor;
}

    public void setActivityFor (ActivityFor ActivityFor)
    {
        this.ActivityFor = ActivityFor;
    }

    public String getBedroomsCount ()
    {
        return BedroomsCount;
    }

    public void setBedroomsCount (String BedroomsCount)
    {
        this.BedroomsCount = BedroomsCount;
    }

    public TypeOfUse getTypeOfUse ()
    {
        return TypeOfUse;
    }

    public void setTypeOfUse (TypeOfUse TypeOfUse)
    {
        this.TypeOfUse = TypeOfUse;
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

    public String getLevelInBuilding ()
    {
        return LevelInBuilding;
    }

    public void setLevelInBuilding (String LevelInBuilding)
    {
        this.LevelInBuilding = LevelInBuilding;
    }

    public String getLicence ()
{
    return Licence;
}

    public void setLicence (String Licence)
    {
        this.Licence = Licence;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public TypeOfProperty getTypeOfProperty ()
    {
        return TypeOfProperty;
    }

    public void setTypeOfProperty (TypeOfProperty TypeOfProperty)
    {
        this.TypeOfProperty = TypeOfProperty;
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
    public String toString() {
        return "TenderRealstate{" +
                "Description='" + Description + '\'' +
                ", PeriodOfRenting='" + PeriodOfRenting + '\'' +
                ", ActivityFor=" + ActivityFor +
                ", BedroomsCount='" + BedroomsCount + '\'' +
                ", TypeOfUse=" + TypeOfUse +
                ", MaxSize='" + MaxSize + '\'' +
                ", MinPrice='" + MinPrice + '\'' +
                ", MinSize='" + MinSize + '\'' +
                ", SpecificArea='" + SpecificArea + '\'' +
                ", LevelInBuilding='" + LevelInBuilding + '\'' +
                ", Licence='" + Licence + '\'' +
                ", Id='" + Id + '\'' +
                ", TypeOfProperty=" + TypeOfProperty +
                ", MaxPrice='" + MaxPrice + '\'' +
                ", Amenities=" + Amenities +
                '}';
    }
}

