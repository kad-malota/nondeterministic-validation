package nl.malotaux.validation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;

public class Person {

    @NotBlank(groups = Mob.Basic.class)
    public String basicField;

    @NotBlank(groups = Mob.Extended.class)
    public String extendedField;

    @NotBlank(groups = Mob.Full.class)
    public String full;

    @Valid
    @ConvertGroup(from = Mob.Extended.class, to = Mob.Basic.class)
    @ConvertGroup(from = Mob.Full.class, to = Mob.Basic.class)
    public Person partner;

    public Person(String basicField) {
        this.basicField = basicField;
    }
}
