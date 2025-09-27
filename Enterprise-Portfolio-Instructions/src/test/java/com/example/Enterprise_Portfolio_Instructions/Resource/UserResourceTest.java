package com.example.Enterprise_Portfolio_Instructions.Resource;

import com.example.Enterprise_Portfolio_Instructions.AuthenticationService.User;
import com.example.Enterprise_Portfolio_Instructions.Service.UserService;
import com.example.Enterprise_Portfolio_Instructions.Util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserResource.class)
@AutoConfigureMockMvc(addFilters = false)
class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    private User user;

//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setUsername("Shivam_23");
//        user.setPassword("2001");
//    }

    // ✅ Test: Successful registration
    @Test
    void testRegisterUser_Success() throws Exception {
        User savedUser = new User();
        savedUser.setUsername("Shivam_23");
        savedUser.setPassword("2001");

        Mockito.when(userService.saveUser(any(User.class))).thenReturn(savedUser);
        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("2001");

        mockMvc.perform(post("/epi/users/register", "Shivam_23")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Shivam_23\", \"password\":\"2001\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Shivam_23"))
                .andExpect(jsonPath("$.password").value("2001"));
    }

    // ✅ Test: Registration with missing fields
    @Test
    void testRegisterUser_MissingFields() throws Exception {

        User InvalidUser = new User();
        Mockito.when(userService.saveUser(any(User.class))).thenReturn(InvalidUser);
        mockMvc.perform((post("/epi/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"\", \"password\":\"\"}")))
                .andExpect(status().isBadRequest());
    }

    // ✅ Test: Successful login
    @Test
    void testLoginUser_Success() throws Exception {
        User user = new User();
        user.setUsername("Shivam_23");
        user.setPassword("2001");

        Authentication auth = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate((any(UsernamePasswordAuthenticationToken.class)))).thenReturn(auth);
        Mockito.when(auth.isAuthenticated()).thenReturn(true);
        Mockito.when(jwtUtil.generateToken(eq("Shivam_23"))).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/epi/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Shivam_23\", \"password\":\"2001\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-jwt-token"));
    }

    // ✅ Test: Failed login
    @Test
    void testLoginUser_Failure() throws Exception {
        User user = new User();
        user.setUsername("Shivam_23");
        user.setPassword("wrongpassword");
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate((any(UsernamePasswordAuthenticationToken.class)))).thenReturn(auth);
        Mockito.when(auth.isAuthenticated()).thenReturn(false);
        mockMvc.perform(post("/epi/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Shivam_23\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user request"));
    }

    // ✅ Test: Get all users
    @Test
    void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setUsername("Shivam_23");
        user1.setPassword("2001");
        User user2 = new User();
        user2.setUsername("Garima_25");
        user2.setPassword("1999");

        Mockito.when(userService.getUser()).thenReturn(java.util.List.of(user1, user2));
        mockMvc.perform(get("/epi/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("Shivam_23"))
                .andExpect(jsonPath("$[0].password").value("2001"))
                .andExpect(jsonPath("$[1].username").value("Garima_25"))
                .andExpect(jsonPath("$[1].password").value("1999"));

    }
}