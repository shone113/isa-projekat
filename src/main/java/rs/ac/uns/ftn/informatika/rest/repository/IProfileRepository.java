package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;

import java.util.List;

public interface IProfileRepository extends JpaRepository<Profile, Integer> {

    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    Profile findProfilesByUserId(@Param("userId") Integer userId);

    @Query("SELECT p.followingProfiles FROM Profile p WHERE p.id = :profileId")
    List<Profile> findFollowingProfiles(@Param("profileId") Integer profileId);
}
