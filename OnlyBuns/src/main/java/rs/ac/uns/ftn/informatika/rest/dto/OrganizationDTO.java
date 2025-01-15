package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Organization;


public class OrganizationDTO {
    private Integer id;
    private String name;
    private double longitude;
    private double latitude;

    public OrganizationDTO() {

    }

    public OrganizationDTO(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.longitude = organization.getLongitude();
        this.latitude = organization.getLatitude();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
