package com.example.Enterprise_Portfolio_Instructions.Resource;


import com.example.Enterprise_Portfolio_Instructions.Entity.Profile;
import com.example.Enterprise_Portfolio_Instructions.Service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/epi/profiles")
public class ProfileResource {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile, @PathVariable Integer id) {
        if (profileService.getProfileById(id).isPresent()) {
            return new ResponseEntity<>("Profile with ID " + id + " already exists.", HttpStatusCode.valueOf(400));
        }
        profile.setId(id);
        return new ResponseEntity<>(profileService.saveProfile(profile), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<?> getAllProfiles() {
        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Integer id) {
        if (profileService.getProfileById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(profileService.getProfileById(id), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody Profile profile, @PathVariable Integer id) {
        if (profileService.getProfileById(id).isEmpty()) {
            return new ResponseEntity<>("Profile with ID " + id + " not exist.", HttpStatusCode.valueOf(404));
        }
        profile.setId(id);
        return new ResponseEntity<>(profileService.saveProfile(profile), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Integer id) {

        if(profileService.getProfileById(id).isEmpty()) {
            return new ResponseEntity<>("Profile with ID " + id + " not exist.", HttpStatusCode.valueOf(204));
        }
        profileService.deleteProfile(id);
        return new ResponseEntity<>("Profile got deleted successfully",HttpStatusCode.valueOf(200));
    }
}
