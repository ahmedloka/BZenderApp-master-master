package apps.sharabash.bzender.Models.TendersDetails;

import org.jetbrains.annotations.NotNull;

public class CarType
{
    private String NameLT;

    private String Id;

    private String Name;

    public String getNameLT ()
    {
        return NameLT;
    }

    public void setNameLT (String NameLT)
    {
        this.NameLT = NameLT;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [NameLT = "+NameLT+", Id = "+Id+", Name = "+Name+"]";
    }
}

