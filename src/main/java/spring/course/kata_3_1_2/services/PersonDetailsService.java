package spring.course.kata_3_1_2.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.course.kata_3_1_2.models.Person;
import spring.course.kata_3_1_2.repositories.PeopleRepository;
import spring.course.kata_3_1_2.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Person> person = peopleRepository.findByUsername(username);
       if (person.isEmpty()) {
           throw new UsernameNotFoundException("User not found");
       }
       return new PersonDetails(person.get());
    }
}
