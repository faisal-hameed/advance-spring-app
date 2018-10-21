package pk.habsoft.demo.estore.endpoint.it;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pk.habsoft.demo.estore.core.Endpoints.ProductEndpoint.BASE_URI;
import static pk.habsoft.demo.estore.core.Endpoints.ProductEndpoint.GET_ALL_PRODUCTS;
import static pk.habsoft.demo.estore.core.Endpoints.ProductEndpoint.GET_BY_ID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pk.habsoft.demo.estore.MainApplication;
import pk.habsoft.demo.estore.service.AppUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@WebAppConfiguration
public class ProductEndpointIntegrationTestIT {

    // https://github.com/rwinch/spring-security-test-blog/blob/master/src/test/java/org/springframework/security/test/web/servlet/showcase/secured/SecurityRequestsTests.java

    private static final String RANDOM_URL = "/random-url";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                // Initiate spring security filter chain
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    public void test_get_all_products_unauthrized() throws Exception {
        mvc.perform(get(BASE_URI + GET_ALL_PRODUCTS))
                // Status should be 401
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_get_all_products() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        mvc.perform(get(BASE_URI + GET_ALL_PRODUCTS)
                // Add user details
                .with(user(userDetails)))
                // Status should be 200
                .andExpect(status().isOk())
                // User should match
                .andExpect(authenticated().withUsername("user"))
                // Role should match
                .andExpect(authenticated().withRoles("USER"));
    }

    @Test
    public void test_get_product_by_id_unauthrized() throws Exception {
        mvc.perform(get(BASE_URI + GET_BY_ID, 3))
                // Status should be 401
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_get_product_by_id() throws Exception {
        // UserDetails userDetails =
        // userDetailsService.loadUserByUsername("user");
        mvc.perform(get(BASE_URI + GET_BY_ID, 3)
                // Specify user
                .with(user("user")))
                // Status should be 200
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    public void test_get_product_by_invalid_id() throws Exception {
        // UserDetails userDetails =
        // userDetailsService.loadUserByUsername("user");
        MvcResult result = mvc.perform(get(BASE_URI + GET_BY_ID, -1)
                // Specify user
                .with(user("user")))
                // Status should be 200
                .andExpect(status().isOk())
                // Empty string
                .andExpect(content().string(""))
                // Return result
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("", content);
    }

    @Test
    public void requestProtectedUrlWithUser() throws Exception {
        mvc.perform(get(RANDOM_URL).with(user("user")))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user"));
    }

    @Test
    public void requestProtectedUrlWithAdmin() throws Exception {
        mvc.perform(get(RANDOM_URL).with(user("admin").password("pass").roles("USER", "ADMIN")))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure it appears we are authenticated with admin
                .andExpect(authenticated().withUsername("admin"));
    }

    @Test
    public void requestProtectedUrlWithUserDetails() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        mvc.perform(get(RANDOM_URL).with(user(userDetails)))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withAuthenticationPrincipal(userDetails));
    }

}
