package apps.sharabash.bzender.Models.verify.invidual;

public class VerifyInvidualResponse {
    private String messgage;

    public String getMessage() {
        return messgage;
    }

    public void setMessage(String message) {
        messgage = message;
    }

    @Override
    public String toString() {
        return "VerifyBussniessResponse{" +
                "Message='" + messgage + '\'' +
                '}';
    }
}
