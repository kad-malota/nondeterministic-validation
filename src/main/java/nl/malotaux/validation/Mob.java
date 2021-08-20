package nl.malotaux.validation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;
import java.util.ArrayList;
import java.util.List;

class Mob {

    @NotBlank
    public String name;

    @Valid
    @ConvertGroup(to = Full.class)
    public List<Person> people = new ArrayList<>();

    public Mob(String name) {
        this.name = name;
    }

    interface Basic {}

    interface Extended extends Basic {}

    interface Full extends Extended {}
}
