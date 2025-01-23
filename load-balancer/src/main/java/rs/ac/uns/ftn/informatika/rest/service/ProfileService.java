package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.LinkedCaseInsensitiveMap;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.ICommentRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private IProfileRepository profileRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ICommentRepository commentRepository;

    @Transactional
    public Profile create(User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = new Profile();
        profile.setUser(managedUser);
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile getProfileByUserId(Integer userId) {
        return profileRepository.findProfilesByUserId(userId);
    }

}
