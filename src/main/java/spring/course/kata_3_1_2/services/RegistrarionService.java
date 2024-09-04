package spring.course.kata_3_1_2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.kata_3_1_2.models.Person;
import spring.course.kata_3_1_2.repositories.PeopleRepository;

@Service
public class RegistrarionService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrarionService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    @Transactional
    public void register(Person person) {
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
