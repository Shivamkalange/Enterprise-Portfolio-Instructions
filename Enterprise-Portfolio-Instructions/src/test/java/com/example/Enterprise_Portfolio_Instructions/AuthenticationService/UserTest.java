package com.example.Enterprise_Portfolio_Instructions.AuthenticationService;

import com.example.Enterprise_Portfolio_Instructions.Util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@WebMvcTest(User.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserTest {

       @MockitoBean
       private JwtUtil jwtUtil;

       @Test
      void testUserCreation() {
          User user = new User();
          user.setUsername("testuser");
          user.setPassword("testpassword");

          assert user.getUsername().equals("testuser");
          assert user.getPassword().equals("testpassword");
      }





}
