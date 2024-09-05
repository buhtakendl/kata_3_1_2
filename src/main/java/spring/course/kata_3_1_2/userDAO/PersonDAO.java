package spring.course.kata_3_1_2.userDAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.course.kata_3_1_2.models.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Person> getAllUsers() {
        return entityManager.createQuery("select u from Person u", Person.class)
                .getResultList();
    }

    @Transactional
    public Person getUserById(int id) {
        TypedQuery<Person> query = entityManager.createQuery(
                "select u from Person u where u.id = :id", Person.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Transactional
    public void addUser(Person person) {
        entityManager.persist(person);
    }

    @Transactional
    public void updateUser(int id, Person updatedUser) {
        TypedQuery<Person> query = entityManager.createQuery(
                "select u from Person u where u.id = :id", Person.class
        );
        query.setParameter("id", id);
        Person person = query.getSingleResult();
        person.setUsername(updatedUser.getUsername());
        person.setAge(updatedUser.getAge());
        entityManager.merge(person);
    }

    @Transactional
    public void deleteUser(int id) {
        entityManager.remove(getUserById(id));
    }

    @Transactional
    public void deleteAllUsers() {
        entityManager.createQuery("delete from Person").executeUpdate();
    }
}
