package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.Organization;

public interface IRabbitCareOrganizationRepository extends JpaRepository<Organization, Integer> {

}