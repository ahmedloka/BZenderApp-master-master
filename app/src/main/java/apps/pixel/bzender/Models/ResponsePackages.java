package apps.pixel.bzender.Models;

public class ResponsePackages {
    private String Points;

    private String Price;

    private String Id;

    public String getPoints ()
    {
        return Points;
    }

    public void setPoints (String Points)
    {
        this.Points = Points;
    }

    public String getPrice ()
    {
        return Price;
    }

    public void setPrice (String Price)
    {
        this.Price = Price;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Points = "+Points+", Price = "+Price+", Id = "+Id+"]";
    }
}

