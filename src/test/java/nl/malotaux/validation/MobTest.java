package nl.malotaux.validation;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

class MobTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void ok() {
        Mob mob = new Mob("People");
        Person bob = new Person("Bob");
        Person alice = new Person("Alice");
        Person carol = new Person("Carol");
        bob.extendedField = "Extended";
        bob.full = "Full";
        alice.extendedField = "Extended";
        alice.full = "Full";
        bob.partner = carol;
        carol.extendedField = "Extended";
        carol.full = "Full";
        carol.partner = bob;
        mob.people.addAll(Arrays.asList(bob, alice, carol));
        Set<ConstraintViolation<Mob>> constraintViolations = validator.validate(mob);
        assertThat(constraintViolations, empty());
    }

    @Test
    void nonDeterministic() {
        Mob mob = new Mob("");
        Person bob = new Person("");
        Person alice = new Person("");
        Person carol = new Person("");
        bob.partner = carol;
        bob.partner.partner = bob;
        mob.people.addAll(Arrays.asList(bob, alice, bob.partner));
        Set<ConstraintViolation<Mob>> violations = validator.validate(mob);
        assertThat(
            violations.stream().map(ConstraintViolation::getPropertyPath).map(Path::toString).sorted().toArray(),
            arrayContaining(
                "name",
                "people[0].basicField",
                "people[0].extendedField",
                "people[0].full",
                "people[0].partner.basicField",
                "people[1].basicField",
                "people[1].extendedField",
                "people[1].full",
                "people[2].basicField",
                "people[2].extendedField",
                "people[2].full"));
    }
}