package pk.habsoft.demo.estore.model;

import org.junit.Test;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import pk.habsoft.demo.estore.testutils.FilterTestClasses;

public class BulkDTOTest {

    // The package to be tested
    private static final String packageName = "pk.habsoft.demo.estore.dto";

    private PojoClassFilter filterTestClasses = new FilterTestClasses();

    @Test
    public void validate() {
        Validator validator = ValidatorBuilder.create().with(new SetterMustExistRule(), new GetterMustExistRule())
                .with(new SetterTester(), new GetterTester()).build();
        validator.validate(packageName, filterTestClasses);
    }

}
