package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Organization;
import rs.ac.uns.ftn.informatika.rest.repository.IRabbitCareOrganizationRepository;

@Service
public class RabbitCareOrganizationService {
    @Autowired
    private IRabbitCareOrganizationRepository repository;

    public void save(Organization organization) {
        repository.save(organization);
    }
}
