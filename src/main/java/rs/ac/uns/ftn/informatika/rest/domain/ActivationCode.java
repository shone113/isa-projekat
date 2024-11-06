package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;

@Entity
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public ActivationCode() {
    }

    public ActivationCode(Integer id, String code, String email) {
        this.id = id;
        this.code = code;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }
    public String getCode() {
        return code;
    }

}
