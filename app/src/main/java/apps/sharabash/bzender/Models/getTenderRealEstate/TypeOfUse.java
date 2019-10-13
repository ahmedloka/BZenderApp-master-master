package apps.sharabash.bzender.Models.getTenderRealEstate;


public class TypeOfUse
{
    private String Id;

    private String EnglishName;

    private String ArabicName;

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getEnglishName ()
    {
        return EnglishName;
    }

    public void setEnglishName (String EnglishName)
    {
        this.EnglishName = EnglishName;
    }

    public String getArabicName ()
    {
        return ArabicName;
    }

    public void setArabicName (String ArabicName)
    {
        this.ArabicName = ArabicName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Id = "+Id+", EnglishName = "+EnglishName+", ArabicName = "+ArabicName+"]";
    }
}

