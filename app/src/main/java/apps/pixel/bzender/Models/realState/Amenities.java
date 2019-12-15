package apps.pixel.bzender.Models.realState;

public class Amenities {
    private String Id;

    public Amenities(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        return "ClassPojo [Id = " + Id + "]";
    }
}