package rs.ac.uns.ftn.informatika.rest.domain;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import javax.persistence.*;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Person {

    public enum Type {
        UNAUTHENTICATED_USER, AUTHENTICATED_USER, ADMINISTRATOR
    }

    @Id
    @SequenceGenerator(name = "personSeqGen", sequenceName = "personSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @Column(name="name", unique=false, nullable=false)
    private String name;

    @Column(name="surname", unique=false, nullable=false)
    private String surname;

    @Column(name="username", unique=false, nullable=false)
    private String username;

    @Column(name="password", unique=false, nullable=false)
    private String password;

    @Column(name="locationId", unique=false, nullable=false)
    private int locationId;

    @Column(name="email", unique=false, nullable=false)
    private String email;

    @Column(name = "type", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Type getTip() {
        return type;
    }

    public void setTip(Type tip) {
        this.type = tip;
    }

}
