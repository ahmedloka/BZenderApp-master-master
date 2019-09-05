package apps.sharabash.bzender.Models.metadataCar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MetaDataCar {
    private List<ElectricalTypes> ElectricalTypes;

    private List<CarTypes> CarTypes;

    private List<TermsAndCondition> TermsAndCondition;

    private List<ContactUs>ContactUs;

    private List<CarModels> CarModels;

    private List<ElectricalModels >ElectricalModels;

    public List<ElectricalTypes> getElectricalTypes ()
    {
        return ElectricalTypes;
    }

    public void setElectricalTypes (List<ElectricalTypes> ElectricalTypes)
    {
        this.ElectricalTypes = ElectricalTypes;
    }

    public List<CarTypes> getCarTypes ()
    {
        return CarTypes;
    }

    public void setCarTypes (List<CarTypes> CarTypes)
    {
        this.CarTypes = CarTypes;
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
        return "ClassPojo [ElectricalTypes = "+ElectricalTypes+", CarTypes = "+CarTypes+", TermsAndCondition = "+TermsAndCondition+", ContactUs = "+ContactUs+", CarModels = "+CarModels+", ElectricalModels = "+ElectricalModels+"]";
    }
}

