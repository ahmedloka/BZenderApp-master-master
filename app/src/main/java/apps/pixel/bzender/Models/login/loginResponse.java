package apps.pixel.bzender.Models.login;

public class loginResponse {
    private String BusinessPerson;

    private String VerifiedPerson;

    private String Points;

    private String Email;


    private String IsImgProfileUploaded;

    private String UserId;

    private String FullName;

    private String ImageUrl;

    private String Token;

    public String getBusinessPerson() {
        return BusinessPerson;
    }

    public void setBusinessPerson(String BusinessPerson) {
        this.BusinessPerson = BusinessPerson;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String Points) {
        this.Points = Points;
    }

    public String getVerifiedPerson() {
        return VerifiedPerson;
    }

    public void setVerifiedPerson(String VerifiedPerson) {
        this.VerifiedPerson = VerifiedPerson;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getIsImgProfileUploaded() {
        return IsImgProfileUploaded;
    }

    public void setIsImgProfileUploaded(String IsImgProfileUploaded) {
        this.IsImgProfileUploaded = IsImgProfileUploaded;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    @Override
    public String toString() {
        return "ClassPojo [BusinessPerson = " + BusinessPerson + ", Points = " + Points + ", VerifiedPerson = " + VerifiedPerson + ", Email = " + Email + ", IsImgProfileUploaded = " + IsImgProfileUploaded + ", UserId = " + UserId + ", FullName = " + FullName + ", ImageUrl = " + ImageUrl + ", Token = " + Token + "]";
    }
}

