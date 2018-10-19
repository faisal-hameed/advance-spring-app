package pk.habsoft.demo.estore.model;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import pk.habsoft.demo.estore.dto.UserDTO;

public class UserDTOTest {

    @Test
    public void validateGettersAndSetters() {
        PojoClass userPojo = PojoClassFactory.getPojoClass(UserDTO.class);

        ValidatorBuilder builder = ValidatorBuilder.create();
        // Lets make sure that we have a getter and a setter for every field
        // defined.
        builder.with(new SetterMustExistRule());
        builder.with(new GetterMustExistRule());

        // Lets also validate that they are behaving as expected
        builder.with(new SetterTester());
        builder.with(new GetterTester());

        // Start test
        Validator pojoValidator = builder.build();
        pojoValidator.validate(userPojo);

    }

}
