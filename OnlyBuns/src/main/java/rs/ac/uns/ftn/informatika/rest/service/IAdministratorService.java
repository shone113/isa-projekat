package rs.ac.uns.ftn.informatika.rest.service;

import rs.ac.uns.ftn.informatika.rest.domain.Administrator;
import rs.ac.uns.ftn.informatika.rest.dto.AdministratorDTO;

import java.util.Collection;

public interface IAdministratorService {

    Collection<Administrator> findAll();
//    Administrator findOne(int id);
//    Administrator create(AdministratorDTO administrator) throws Exception;
//    Administrator update(AdministratorDTO administrator, int id) throws Exception;
//    Administrator delete(int id);
}
