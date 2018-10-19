package pk.habsoft.demo.estore.model;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.utils.IdentityHandlerStub;
import com.openpojo.validation.utils.ValidationHelper;

import pk.habsoft.demo.estore.testutils.FilterTestClasses;

public class DTOToStringTest {

	// The package to test
	private static final String POJO_PACKAGE = "pk.habsoft.demo.estore.dto";
	private PojoClassFilter filterTestClasses = new FilterTestClasses();

	private List<PojoClass> pojoClasses;

	@Before()
	public void setup() {
		pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, filterTestClasses);
	}

	@Test
	public void testToString() {
		for (PojoClass pojoClass : pojoClasses) {
			final Object classInstance = ValidationHelper.getBasicInstance(pojoClass);
			for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
				if (fieldEntry.hasGetter()) {
					Object value = fieldEntry.get(classInstance);

					if (!fieldEntry.isFinal()) {
						value = RandomFactory.getRandomValue(fieldEntry);
						fieldEntry.set(classInstance, value);
					}

					IdentityHandlerStub.registerIdentityHandlerStubForValue(value);

					LoggerFactory.getLogger(this.getClass())
							.debug("Testing that ToString() prints Field [{0}] with value [{1}]", fieldEntry, value);

					String toStringContent = classInstance.toString();
					Affirm.affirmTrue(pojoClass.getName() + ".toString() did not print Field[{" + fieldEntry
							+ "}] with value [{" + value + "}]", toStringContent.contains(value.toString()));
					IdentityHandlerStub.unregisterIdentityHandlerStubForValue(value);
				} else {
					LoggerFactory.getLogger(this.getClass()).debug("Field [{0}] has no getter skipping", fieldEntry);
				}
			}
		}

	}
}
