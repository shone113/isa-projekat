package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.User;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
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

    @Query("select u from User as u where" +
            "(:name is null or :name = '' or LOWER(u.name) like LOWER(CONCAT('%', :name, '%')))" +
            "and (:surname is null or :surname = '' or LOWER(u.surname) like LOWER(CONCAT('%', :surname, '%')) )" +
            "and (:email is null or :email = '' or LOWER(u.email) like LOWER(CONCAT('%', :email, '%')) )" +
            "and (:minPostsRange is null or u.postsCount >= :minPostsRange) " +
            "and (:maxPostsRange is null or u.postsCount <= :maxPostsRange)")
    public Page<User> filterUsersPaged(Pageable page,
                                       @Param("name") String name,
                                       @Param("surname") String surname,
                                       @Param("email") String email,
                                       @Param("minPostsRange") Integer minPostsRange,
                                       @Param("maxPostsRange") Integer maxPostsRange);


    @Query("select u from User as u order by u.followingCount asc")
    public List<User> getSortedByFollowingCountAsc();

    @Query("select u from User as u order by u.followingCount asc")
    public Page<User> getSortedByFollowingCountAscPaged(Pageable page);

    @Query("select u from User as u order by u.followingCount desc")
    public List<User> getSortedByFollowingCountDesc();

    @Query("select u from User as u order by u.followingCount desc")
    public Page<User> getSortedByFollowingCountDescPaged(Pageable page);

    @Query("select u from User as u order by u.email asc")
    public List<User> getSortedByEmailAsc();

    @Query("select u from User as u order by u.email asc")
    public Page<User> getSortedByEmailAscPaged(Pageable page);

    @Query("select u from User as u order by u.email desc")
    public List<User> getSortedByEmailDesc();

    @Query("select u from User as u order by u.email desc")
    public Page<User> getSortedByEmailDescPaged(Pageable page);

    public User getUsersByActivationToken(String activationToken);

    @Query("select u from User u")
    public List<User> getAllUsers(Pageable pageable);

    public int countUsersByEmail(String email);

//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    User findUserByID(@Param("userId") Integer userId);

    //    @Lock(LockModeType.PESSIMISTIC_WRITE)
@Transactional
    @Modifying
    @Query("UPDATE User u SET u.followingCount = u.followingCount + 1 WHERE u.id = :userId")
    void incrementFollowingCount(@Param("userId") Integer userId);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
@Transactional
    @Modifying
    @Query("UPDATE User u SET u.followingCount = u.followingCount - 1 WHERE u.id = :userId")
    void decrementFollowingCount(@Param("userId") Integer userId);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.followersCount = u.followersCount + 1 WHERE u.id = :userId")
    void incrementFollowersCount(@Param("userId") Integer userId);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
@Transactional
    @Modifying
    @Query("UPDATE User u SET u.followersCount = u.followersCount - 1 WHERE u.id = :userId")
    void decrementFollowersCount(@Param("userId") Integer userId);

}
