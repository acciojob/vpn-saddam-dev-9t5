package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception {
        User user = new User();
        try {
            user.setUsername(username);
            user.setPassword(password);
            user = userRepository3.save(user);

        }catch (Exception e) {
            throw new Exception("this");
        }
        CountryName givenCountryName = null;
        String countryCode = "";
        try {
            CountryName[] countryNames = CountryName.values();
            for (CountryName cn: countryNames) {
                if(cn.name().equals(countryName)) {
                    givenCountryName = cn;
                    countryCode = cn.toCode();
                    break;
                }
            }
        }
        catch (Exception e) {
            throw new Exception("Country not found");
        }
        if(countryCode == null) throw new Exception("Country not found");

        String originalIp = countryCode + user.getId();

        Country country = new Country(givenCountryName, countryCode);
        country = countryRepository3.save(country);
        user.setMaskedIp(null);
        user.setConnected(false);
        user.setOriginalCountry(country);
        user.setOriginalIp(originalIp);
        user = userRepository3.save(user);
        return user;

    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository3.findById(serviceProviderId);
        if(optionalServiceProvider.isPresent()) {
            ServiceProvider serviceProvider = optionalServiceProvider.get();
            Optional<User> optionalUser = userRepository3.findById(userId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.getServiceProviderList().add(serviceProvider);
                user = userRepository3.save(user);
                return user;
            }
        }
        return new User();
    }
}
