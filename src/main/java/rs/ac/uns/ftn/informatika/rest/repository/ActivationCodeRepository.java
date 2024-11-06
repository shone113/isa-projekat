package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.ActivationCode;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Integer> {
    public ActivationCode getActivationCodesByEmail(String email);

}
