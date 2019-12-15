package apps.pixel.bzender.Models;

import org.jetbrains.annotations.NotNull;

public class ForgetPasswordBody {
    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @NotNull
    @Override
    public String toString() {
        return "ForgetPasswordBody{" +
                "Email='" + Email + '\'' +
                '}';
    }
}
