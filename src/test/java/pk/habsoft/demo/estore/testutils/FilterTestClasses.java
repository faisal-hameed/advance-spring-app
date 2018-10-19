package pk.habsoft.demo.estore.testutils;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

public class FilterTestClasses implements PojoClassFilter {
    public boolean include(PojoClass pojoClass) {
        // Path will be *test-classes* for maven
        return !pojoClass.getSourcePath().contains("/test-classes/");
    }
}
