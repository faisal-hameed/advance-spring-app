package pk.habsoft.demo.estore.endpoint;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pk.habsoft.demo.estore.endpoint.Endpoints.MethodAuthorization.ADMIN_PAGE;
import static pk.habsoft.demo.estore.endpoint.Endpoints.MethodAuthorization.BASE_URL;
import static pk.habsoft.demo.estore.endpoint.Endpoints.MethodAuthorization.COMMON_PAGE;
import static pk.habsoft.demo.estore.endpoint.Endpoints.MethodAuthorization.USER_PAGE;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pk.habsoft.demo.estore.MainApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@WebAppConfiguration
public class MethodsAuthorizationEndPointTest {

    private static final String RANDOM_URL = "/random-url";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                // Initiate spring security filter chain
                .apply(SecurityMockMvcConfigurers.springSecurity())
                // .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    @WithUserDetails
    public void request_protected_url_with_user() throws Exception {
        mvc.perform(get(RANDOM_URL))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user"));
    }

    @Test
    @WithUserDetails
    public void user_can_request_common_pages() throws Exception {
        mvc.perform(get(BASE_URL + COMMON_PAGE))
                // Ensure we got past Security
                .andExpect(status().isOk())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user"));
    }

    @Test
    @WithUserDetails
    public void user_can_request_user_pages() throws Exception {
        mvc.perform(get(BASE_URL + USER_PAGE))
                // Ensure we got past Security
                .andExpect(status().isOk())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user"))
                // Match response
                .andExpect(content().string("Only users can access this page"));
    }

    @Test
    @WithUserDetails
    public void user_cant_request_admin_pages() throws Exception {
        mvc.perform(get(BASE_URL + ADMIN_PAGE))
                // User not allowed admin pages
                .andExpect(status().isForbidden())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user"));
    }

    @Test
    @WithUserDetails("admin")
    public void request_protected_url_with_admin() throws Exception {
        mvc.perform(get(RANDOM_URL))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure user authentication
                .andExpect(authenticated().withUsername("admin").withRoles("ADMIN"));
    }

    @Test
    @WithUserDetails("admin")
    public void admin_can_request_admin_pages() throws Exception {
        mvc.perform(get(BASE_URL + ADMIN_PAGE))
                // Ensure we got past Security
                .andExpect(status().isOk())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("admin"))
                // Match response
                .andExpect(content().string("Only admins can access this page"));
    }

}
