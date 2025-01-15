package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Organization;
import rs.ac.uns.ftn.informatika.rest.dto.OrganizationDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IRabbitCareOrganizationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitCareOrganizationService {
    @Autowired
    private IRabbitCareOrganizationRepository repository;

    public void save(Organization organization) {
        repository.save(organization);
    }

    public List<OrganizationDTO> findAll() {
        List<Organization> organizations = repository.findAll();
        List<OrganizationDTO> dtos = new ArrayList<>();
        for (Organization organization : organizations) {
            dtos.add(new OrganizationDTO(organization));
        }
        return dtos;
    }
}
