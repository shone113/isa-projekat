package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="administrator")
public class Administrator extends Person {

    public Administrator() {}
}
