package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUserRepository  extends JpaRepository<User, Integer> {
    @Query("select '*' from User u where u.name like 'name'")
    public List<User> filterUsers(String name);
    public User getUserByEmail(String email);
//
//    @Query("select c from Course c join fetch c.exams e where c.id =?1")
//    public List<User> sortByFollowingCountAsc(Integer courseId);
//
//    @Query("select c from Course c join fetch c.exams e where c.id =?1")
//    public List<User> sortByFollowingCountDesc(Integer courseId);
//
//    @Query("select c from Course c join fetch c.exams e where c.id =?1")
//    public List<User> sortByEmailAsc(Integer courseId);
//
//    @Query("select c from Course c join fetch c.exams e where c.id =?1")
//    public List<User> sortByEmailDesc(Integer courseId);

}
