package apps.sharabash.bzender.Models.metadataCar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MetaDataCar {
    private List<TypesOfUses> TypesOfUses;

    private List<ElectricalTypes> ElectricalTypes;

    private List<ActivityFor> ActivityFor;

    private List<CarTypes> CarTypes;

    private List<TypesOfProperties> TypesOfProperties;

    private List<TermsAndCondition> TermsAndCondition;

    private List<ContactUs> ContactUs;

    private List<CarModels> CarModels;

    private List<Amenities> Amenities;

    private List<ElectricalModels> ElectricalModels;

    public List<TypesOfUses> getTypesOfUses ()
    {
        return TypesOfUses;
    }

    public void setTypesOfUses (List<TypesOfUses> TypesOfUses)
    {
        this.TypesOfUses = TypesOfUses;
    }

    public List<ElectricalTypes> getElectricalTypes ()
    {
        return ElectricalTypes;
    }

    public void setElectricalTypes (List<ElectricalTypes> ElectricalTypes)
    {
        this.ElectricalTypes = ElectricalTypes;
    }

    public List<ActivityFor> getActivityFor ()
    {
        return ActivityFor;
    }

    public void setActivityFor (List<ActivityFor> ActivityFor)
    {
        this.ActivityFor = ActivityFor;
    }

    public List<CarTypes> getCarTypes ()
    {
        return CarTypes;
    }

    public void setCarTypes (List<CarTypes> CarTypes)
    {
        this.CarTypes = CarTypes;
    }

    public List<TypesOfProperties> getTypesOfProperties ()
    {
        return TypesOfProperties;
    }

    public void setTypesOfProperties (List<TypesOfProperties> TypesOfProperties)
    {
        this.TypesOfProperties = TypesOfProperties;
    }

    public List<TermsAndCondition> getTermsAndCondition ()
    {
        return TermsAndCondition;
    }

    public void setTermsAndCondition (List<TermsAndCondition> TermsAndCondition)
    {
        this.TermsAndCondition = TermsAndCondition;
    }

    public List<ContactUs> getContactUs ()
    {
        return ContactUs;
    }

    public void setContactUs (List<ContactUs> ContactUs)
    {
        this.ContactUs = ContactUs;
    }

    public List<CarModels> getCarModels ()
    {
        return CarModels;
    }

    public void setCarModels (List<CarModels> CarModels)
    {
        this.CarModels = CarModels;
    }

    public List<Amenities> getAmenities ()
    {
        return Amenities;
    }

    public void setAmenities (List<Amenities> Amenities)
    {
        this.Amenities = Amenities;
    }

    public List<ElectricalModels> getElectricalModels ()
    {
        return ElectricalModels;
    }

    public void setElectricalModels (List<ElectricalModels> ElectricalModels)
    {
        this.ElectricalModels = ElectricalModels;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TypesOfUses = "+TypesOfUses+", ElectricalTypes = "+ElectricalTypes+", ActivityFor = "+ActivityFor+", CarTypes = "+CarTypes+", TypesOfProperties = "+TypesOfProperties+", TermsAndCondition = "+TermsAndCondition+", ContactUs = "+ContactUs+", CarModels = "+CarModels+", Amenities = "+Amenities+", ElectricalModels = "+ElectricalModels+"]";
    }
}

