package apps.pixel.bzender.Models.bookElectrical;

import org.jetbrains.annotations.NotNull;

public class BookElectricalBody {

    private String Status;

    private String TenderId;

    private String OriginManufacturer;

    private String Price;

    private String UnitType;

    private String Note;

    private String Model;

    private String NumberOfUnit;

    private String YearOfManufacture;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public String getTenderId ()
    {
        return TenderId;
    }

    public void setTenderId (String TenderId)
    {
        this.TenderId = TenderId;
    }

    public String getOriginManufacturer ()
    {
        return OriginManufacturer;
    }

    public void setOriginManufacturer (String OriginManufacturer)
    {
        this.OriginManufacturer = OriginManufacturer;
    }

    public String getPrice ()
    {
        return Price;
    }

    public void setPrice (String Price)
    {
        this.Price = Price;
    }

    public String getUnitType ()
    {
        return UnitType;
    }

    public void setUnitType (String UnitType)
    {
        this.UnitType = UnitType;
    }

    public String getNote ()
    {
        return Note;
    }

    public void setNote (String Note)
    {
        this.Note = Note;
    }

    public String getModel ()
    {
        return Model;
    }

    public void setModel (String Model)
    {
        this.Model = Model;
    }

    public String getNumberOfUnit ()
    {
        return NumberOfUnit;
    }

    public void setNumberOfUnit (String NumberOfUnit)
    {
        this.NumberOfUnit = NumberOfUnit;
    }

    public String getYearOfManufacture ()
    {
        return YearOfManufacture;
    }

    public void setYearOfManufacture (String YearOfManufacture)
    {
        this.YearOfManufacture = YearOfManufacture;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", TenderId = "+TenderId+", OriginManufacturer = "+OriginManufacturer+", Price = "+Price+", UnitType = "+UnitType+", Note = "+Note+", Model = "+Model+", NumberOfUnit = "+NumberOfUnit+", YearOfManufacture = "+YearOfManufacture+"]";
    }
}

