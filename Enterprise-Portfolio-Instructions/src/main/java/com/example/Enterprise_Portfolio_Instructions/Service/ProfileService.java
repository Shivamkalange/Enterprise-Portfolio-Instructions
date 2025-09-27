package com.example.Enterprise_Portfolio_Instructions.Service;


import com.example.Enterprise_Portfolio_Instructions.Dao.ProfileDao;
import com.example.Enterprise_Portfolio_Instructions.Entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

     @Autowired
    private ProfileDao profileDao;

    public Profile saveProfile(Profile profile) {
        return profileDao.save(profile);
    }

    public List<Profile> getAllProfiles() {
        return profileDao.findAll();
    }

    public Optional<Profile> getProfileById(Integer id){
        return profileDao.findById(id.longValue());
    }

    public void deleteProfile(Integer id) {
        profileDao.deleteById(id.longValue());
    }

}
