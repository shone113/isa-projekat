package rs.ac.uns.ftn.informatika.rest.dto;

public class LoginDetailsDto {
    private String email;
    private String password;
    public LoginDetailsDto() {}
    public LoginDetailsDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
