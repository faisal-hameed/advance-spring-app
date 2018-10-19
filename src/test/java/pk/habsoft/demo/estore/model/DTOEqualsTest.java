package pk.habsoft.demo.estore.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import pk.habsoft.demo.estore.dto.BaseDTO;
import pk.habsoft.demo.estore.dto.LoginRequest;
import pk.habsoft.demo.estore.dto.ProductDTO;
import pk.habsoft.demo.estore.dto.UserDTO;

public class DTOEqualsTest {

	Warning[] warnings = new Warning[] { Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS };

	@Test
	public void testProductDTOEquals() {
		ProductDTO p1 = new ProductDTO(1L, "prod-1", 30);
		ProductDTO p2 = new ProductDTO(2L, "prod-2", 40);

		EqualsVerifier.forClass(ProductDTO.class).suppress(warnings).usingGetClass()
				.withPrefabValues(ProductDTO.class, p1, p2).verify();

	}

	@Test
	public void testBulkDTOEquals() {
		Class<?>[] clazzes = new Class[] { BaseDTO.class, LoginRequest.class, UserDTO.class };

		for (Class<?> clazz : clazzes) {
			EqualsVerifier.forClass(clazz).suppress(warnings).usingGetClass().verify();
		}
	}

}
