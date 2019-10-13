package apps.sharabash.bzender.Models;

import org.jetbrains.annotations.NotNull;

public class Offers
{
    private String Note;

    private String Id;

    private String Image;

    private String URL;

    public String getNote ()
    {
        return Note;
    }

    public void setNote (String Note)
    {
        this.Note = Note;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getImage ()
    {
        return Image;
    }

    public void setImage (String Image)
    {
        this.Image = Image;
    }

    public String getURL ()
    {
        return URL;
    }

    public void setURL (String URL)
    {
        this.URL = URL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Note = "+Note+", Id = "+Id+", Image = "+Image+", URL = "+URL+"]";
    }
}
