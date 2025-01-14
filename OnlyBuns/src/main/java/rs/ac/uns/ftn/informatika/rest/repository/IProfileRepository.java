package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;

import java.util.List;
import java.util.Set;

public interface IProfileRepository extends JpaRepository<Profile, Integer> {

    @Query("SELECT p FROM Profile p LEFT JOIN FETCH p.posts WHERE p.user.id = :userId")
    Profile findProfilesByUserId(@Param("userId") Integer userId);

    @Query("SELECT p.followingProfiles FROM Profile p WHERE p.id = :profileId")
    List<Profile> findFollowingProfiles(@Param("profileId") Integer profileId);

    @Query("SELECT p FROM Profile p LEFT JOIN FETCH p.posts WHERE p.id = :profileId")
    Profile findProfileById(@Param("profileId")Integer profileId);

    @Query("SELECT p.followerProfiles FROM Profile p WHERE p.id = :profileId")
    List<Profile> findFollowerProfiles(@Param("profileId") Integer profileId);

    @Query("SELECT p.user.id FROM Profile p WHERE p.id = :profileId")
    Profile findUserIdByProfileId(@Param("profileId") Integer profileId);


}
