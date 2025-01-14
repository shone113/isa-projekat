package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "organizations")
public class Organization implements Serializable {
    @Id
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "longitude")
    double longitude;
    @Column(name = "latitude")
    double latitude;

    public Organization() {}

    public Organization(Integer id, String name, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
