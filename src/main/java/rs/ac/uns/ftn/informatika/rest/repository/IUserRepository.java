package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
//    @Query("select u from User as u where u.name like %:name%")

//    @Query("select u from User as u where LOWER(u.name) like LOWER(CONCAT('%', :name, '%'))")
//    public List<User> filterUsers(@Param("name") String name);

    @Query("select u from User as u where" +
            "(:name is null or :name = '' or LOWER(u.name) like LOWER(CONCAT('%', :name, '%')))" +
            "and (:surname is null or :surname = '' or LOWER(u.surname) like LOWER(CONCAT('%', :surname, '%')) )" +
            "and (:email is null or :email = '' or LOWER(u.email) like LOWER(CONCAT('%', :email, '%')) )" +
            "and (:minPostsRange is null or u.postsCount >= :minPostsRange) " +
            "and (:maxPostsRange is null or u.postsCount <= :maxPostsRange)")
    public List<User> filterUsers(@Param("name") String name,
                                  @Param("surname") String surname,
                                  @Param("email") String email,
                                  @Param("minPostsRange") Integer minPostsRange,
                                  @Param("maxPostsRange") Integer maxPostsRange);

    @Query("select u from User as u order by u.followingCount asc")
    public List<User> getSortedByFollowingCountAsc();

    @Query("select u from User as u order by u.followingCount desc")
    public List<User> getSortedByFollowingCountDesc();

    @Query("select u from User as u order by u.email asc")
    public List<User> getSortedByEmailAsc();

    @Query("select u from User as u order by u.email desc")
    public List<User> getSortedByEmailDesc();

    public User getUsersByActivationToken(String activationToken);
}
