package numeryx.fr.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import numeryx.fr.model.Person;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonDaoTuTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDAO dao;

    private List<Person> personDataList = Arrays.asList(
            Person.builder().firstName("Jacques").lastName("Cyprus").profession("Mecanicien").tel("123456789").address("Talence").build(),
            Person.builder().firstName("Mawuli").lastName("Koffi").profession("IT").tel("123456987").address("Paris").build(),
            Person.builder().firstName("Estime").lastName("Biayi").profession("Electrician").tel("123456879").address("Lubumbashi").build(),
            Person.builder().firstName("Norman").lastName("Albert").profession("Millitaire").tel("000111222").address("Paris").build()
    );

    @Test
    public void emptyResultTest() {
        Iterable<Person> persons = dao.findAll();
        assertThat(persons).isEmpty();
    }

    @Test
    public void findAllPersonsTest() {

        Person p1 = personDataList.get(0);
        Person p2 = personDataList.get(1);
        Person p3 = personDataList.get(2);
        Person p4 = personDataList.get(3);

        entityManager.persist(p2);
        entityManager.persist(p1);
        entityManager.persist(p3);
        entityManager.persist(p4);

        entityManager.flush();

        Iterable<Person> persons = dao.findAll();
        assertThat(persons).hasSize(4).contains(p1, p2, p3, p4);
    }

    @Test
    public void findPersonByIdTest() {

        Person p1 = personDataList.get(0);
        Person p2 = personDataList.get(1);

        entityManager.persist(p1);
        entityManager.persist(p2);

        Person foundPerson = dao.findById(p2.getIdPerson()).get();
        assertThat(foundPerson).isEqualTo(p2);
    }

    @Test
    public void findPersonByTelTest() {

        Person p1 = personDataList.get(0);
        Person p2 = personDataList.get(1);
        Person p3 = personDataList.get(2);

        entityManager.persist(p2);
        entityManager.persist(p1);
        entityManager.persist(p3);

        Person person = dao.findByTel("123456789").get();
        assertEquals("Jacques", person.getFirstName());
        assertEquals("Cyprus", person.getLastName());
        assertEquals("Mecanicien", person.getProfession());
        assertEquals("Talence", person.getAddress());
    }

    @Test
    public void createPersonTest() {
        Person p = personDataList.get(3);
        Person result = dao.save(p);
        assertThat(result).hasFieldOrPropertyWithValue("firstName", "Norman");
        assertThat(result).hasFieldOrPropertyWithValue("lastName", "Albert");
        assertThat(result).hasFieldOrPropertyWithValue("profession", "Millitaire");
        assertThat(result).hasFieldOrPropertyWithValue("tel", "000111222");
        assertThat(result).hasFieldOrPropertyWithValue("address", "Paris");
    }

    @Test
    public void deletePersonTest() {

        Person p1 = personDataList.get(0);
        Person p2 = personDataList.get(1);
        Person p3 = personDataList.get(2);

        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);

        dao.delete(p2);

        Iterable<Person> persons = dao.findAll();
        assertThat(persons).hasSize(2).contains(p1, p3);
    }
}
