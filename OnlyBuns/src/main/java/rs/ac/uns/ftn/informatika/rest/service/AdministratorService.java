package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Administrator;
import rs.ac.uns.ftn.informatika.rest.repository.IAdministratorRepository;

import java.util.Collection;

@Service
public class AdministratorService implements IAdministratorService {

    @Autowired
    private IAdministratorRepository administratorRepository;

    @Override
    public Collection<Administrator> findAll(){
        Collection<Administrator> administrators = administratorRepository.findAll();
        return administrators;
    }
}
