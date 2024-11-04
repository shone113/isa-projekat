package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public interface IUserRepository  extends JpaRepository<User, Integer> {

}
