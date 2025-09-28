package com.example.Enterprise_Portfolio_Instructions.Resource;

import com.example.Enterprise_Portfolio_Instructions.Entity.Profile;
import com.example.Enterprise_Portfolio_Instructions.Service.ProfileService;
import com.example.Enterprise_Portfolio_Instructions.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileResource.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private ProfileService profileService;

    // ---------------- CREATE ----------------
    @Test
   // @DisplayName("POST /epi/profiles/create/{id} - success")
    void testCreateProfile_Success() throws Exception {
        Profile profile = new Profile();
        profile.setId(2);
        profile.setName("John");
        profile.setEmail("John@tcs.com");
        profile.setCompany("ABC");

        Mockito.when(profileService.getProfileById(2)).thenReturn(Optional.empty());
        Mockito.when(profileService.saveProfile(any(Profile.class))).thenReturn(profile);

        mockMvc.perform(post("/epi/profiles/create/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\" , \"email\":\"John@tcs.com\", \"company\":\"ABC\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("John@tcs.com"))
                .andExpect(jsonPath("$.company").value("ABC"));
    }

    @Test
    @DisplayName("POST /epi/profiles/create/{id} - already exists")
    void testCreateProfile_AlreadyExists() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("John");
        profile.setEmail("John@tcs.com");
        profile.setCompany("ABC");

        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.of(profile));

        mockMvc.perform(post("/epi/profiles/create/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\" , \"email\":\"John@tcs.com\", \"company\":\"ABC\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Profile with ID 1 already exists."));
    }

    // ---------------- GET ALL ----------------
    @Test
    @DisplayName("GET /epi/profiles - all profiles")
    void testGetAllProfiles() throws Exception {
        Profile p1 = new Profile();
        p1.setId(1);
        p1.setName("John");
        Profile p2 = new Profile();
        p2.setId(2);
        p2.setName("Jane");

        Mockito.when(profileService.getAllProfiles()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/epi/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane"));
    }

    // ---------------- GET BY ID ----------------
    @Test
    @DisplayName("GET /epi/profiles/{id} - found")
    void testGetProfileById_Found() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("John");

        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.of(profile));

        mockMvc.perform(get("/epi/profiles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    @DisplayName("GET /epi/profiles/{id} - not found")
    void testGetProfileById_NotFound() throws Exception {
        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/epi/profiles/{id}", 1))
                .andExpect(status().isNoContent());

    }

    // ---------------- UPDATE ----------------
    @Test
    @DisplayName("PUT /epi/profiles/update/{id} - success")
    void testUpdateProfile_Success() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Shivam");
        profile.setEmail("shivam@tcs.com");
        profile.setCompany("TCS");

        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.of(profile));
        Mockito.when(profileService.saveProfile(any(Profile.class))).thenReturn(profile);

        mockMvc.perform(put("/epi/profiles/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Shivam\",\"email\":\"shivam@tcs.com\", \"company\":\"TCS\"}"))
                  .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Shivam"))
                .andExpect(jsonPath("$.email").value("shivam@tcs.com"))
                .andExpect(jsonPath("$.company").value("TCS"));
    }

    @Test
    @DisplayName("PUT /epi/profiles/update/{id} - not exist")
    void testUpdateProfile_NotExist() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Shivam");
        profile.setEmail("shivam@tcs.com");
        profile.setCompany("TCS");

        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/epi/profiles/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Shivam\",\"email\":\"shivam@tcs.com\", \"company\":\"TCS\"}"))
                      .andExpect(status().isNotFound());

    }

    // ---------------- DELETE ----------------
    @Test
    @DisplayName("DELETE /epi/profiles/delete/{id} - success")
    void testDeleteProfile_Success() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);


        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.of(profile));
        Mockito.doNothing().when(profileService).deleteProfile(1);

        mockMvc.perform(delete("/epi/profiles/delete/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /epi/profiles/delete/{id} - not exist")
    void testDeleteProfile_NotExist() throws Exception {
        Mockito.when(profileService.getProfileById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/epi/profiles/delete/{id}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Profile with ID 1 not exist."));
    }
}


// Case 2: Profile with same ID already exists

