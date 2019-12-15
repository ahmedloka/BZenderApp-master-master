package apps.pixel.bzender.Models;

import org.jetbrains.annotations.NotNull;

public class FillDataCar {

    private String FromKMToKM;

    private String ViolationDocument;

    private String RegistrationFees;

    private String EngineCapacity;

    private String NumberOfCar;

    private String YearOfCar;

    private String TenderId;

    private String TransmissionType;

    private String CarModelId;

    private String Note;

    private String LicenseStatus;

    private String ToKM;

    private String PossibilityOfExamination;

    private String CarTypeId;

    public String getFromKM ()
    {
        return FromKMToKM;
    }

    public void setFromKM (String FromKM)
    {
        this.FromKMToKM = FromKM;
    }

    public String getViolationDocument ()
    {
        return ViolationDocument;
    }

    public void setViolationDocument (String ViolationDocument)
    {
        this.ViolationDocument = ViolationDocument;
    }

    public String getRegistrationFees ()
    {
        return RegistrationFees;
    }

    public void setRegistrationFees (String RegistrationFees)
    {
        this.RegistrationFees = RegistrationFees;
    }

    public String getEngineCapacity ()
    {
        return EngineCapacity;
    }

    public void setEngineCapacity (String EngineCapacity)
    {
        this.EngineCapacity = EngineCapacity;
    }

    public String getNumberOfCar ()
    {
        return NumberOfCar;
    }

    public void setNumberOfCar (String NumberOfCar)
    {
        this.NumberOfCar = NumberOfCar;
    }

    public String getYearOfCar ()
    {
        return YearOfCar;
    }

    public void setYearOfCar (String YearOfCar)
    {
        this.YearOfCar = YearOfCar;
    }

    public String getTenderId ()
    {
        return TenderId;
    }

    public void setTenderId (String TenderId)
    {
        this.TenderId = TenderId;
    }

    public String getTransmissionType ()
    {
        return TransmissionType;
    }

    public void setTransmissionType (String TransmissionType)
    {
        this.TransmissionType = TransmissionType;
    }

    public String getCarModelId ()
    {
        return CarModelId;
    }

    public void setCarModelId (String CarModelId)
    {
        this.CarModelId = CarModelId;
    }

    public String getNote ()
    {
        return Note;
    }

    public void setNote (String Note)
    {
        this.Note = Note;
    }

    public String getLicenseStatus ()
    {
        return LicenseStatus;
    }

    public void setLicenseStatus (String LicenseStatus)
    {
        this.LicenseStatus = LicenseStatus;
    }

    public String getToKM ()
    {
        return ToKM;
    }

    public void setToKM (String ToKM)
    {
        this.ToKM = ToKM;
    }

    public String getPossibilityOfExamination ()
    {
        return PossibilityOfExamination;
    }

    public void setPossibilityOfExamination (String PossibilityOfExamination)
    {
        this.PossibilityOfExamination = PossibilityOfExamination;
    }

    public String getCarTypeId ()
    {
        return CarTypeId;
    }

    public void setCarTypeId (String CarTypeId)
    {
        this.CarTypeId = CarTypeId;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "ClassPojo [FromKM = "+FromKMToKM+", ViolationDocument = "+ViolationDocument+", RegistrationFees = "+RegistrationFees+", EngineCapacity = "+EngineCapacity+", NumberOfCar = "+NumberOfCar+", YearOfCar = "+YearOfCar+", TenderId = "+TenderId+", TransmissionType = "+TransmissionType+", CarModelId = "+CarModelId+", Note = "+Note+", LicenseStatus = "+LicenseStatus+", ToKM = "+ToKM+", PossibilityOfExamination = "+PossibilityOfExamination+", CarTypeId = "+CarTypeId+"]";
    }
}

