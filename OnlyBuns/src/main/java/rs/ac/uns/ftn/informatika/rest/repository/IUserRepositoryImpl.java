package rs.ac.uns.ftn.informatika.rest.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.domain.User;

import javax.persistence.*;

@Repository
public class IUserRepositoryImpl {

    private static final Logger logger = LoggerFactory.getLogger(IUserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User findByIdWithLock(Integer id) {
        logger.info("Pokušaj zaključavanja reda za User sa ID: {}", id);
        logger.info("EntityManager hash code: {}", entityManager.hashCode());

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id");
        query.setParameter("id", id);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        query.setHint("javax.persistence.lock.timeout", 0); // NO WAIT

        try {
            User user = (User) query.getSingleResult();
            logger.info("Uspešno zaključan red za User sa ID: {}", id);
            return user;
        }  catch (LockTimeoutException e) {
            logger.error("Nije uspelo zaključavanje reda za User sa ID: {}", id, e);
            throw new PessimisticLockingFailureException("Could not obtain lock on row", e);
        }catch (NoResultException e) {
            logger.warn("User sa ID: {} nije pronađen", id);
            return null;
        } catch (PersistenceException e) {
            logger.error("Nije uspelo zaključavanje reda za User sa ID: {}", id, e);
            throw e;
        }
    }
}