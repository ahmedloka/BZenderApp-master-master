package apps.sharabash.bzender.Models.realState;

public class AddTenderResponse {

    private String Message ;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    @Override
    public String toString() {
        return "AddTenderResponse{" +
                "Message='" + Message + '\'' +
                '}';
    }
}
