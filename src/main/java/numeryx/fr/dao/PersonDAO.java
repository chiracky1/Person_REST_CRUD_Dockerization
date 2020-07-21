package numeryx.fr.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import numeryx.fr.model.Person;

@Repository
public interface PersonDAO extends JpaRepository<Person, Long> {
	
	Optional<Person> findByTel(String tel);

}
