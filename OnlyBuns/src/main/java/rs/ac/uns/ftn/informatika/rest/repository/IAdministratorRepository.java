package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.Administrator;

public interface IAdministratorRepository extends JpaRepository<Administrator, Long> {
}
