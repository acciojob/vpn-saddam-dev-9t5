package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin = adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {

        Optional<Admin> optionalAdmin = adminRepository1.findById(adminId);
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository1.findByName(providerName);
        if(optionalAdmin.isPresent() && optionalServiceProvider.isPresent()) {
            Admin admin = optionalAdmin.get();
            ServiceProvider serviceProvider = optionalServiceProvider.get();
            admin.getServiceProviders().add(serviceProvider);
            adminRepository1.save(admin);
            return admin;
        }
        return new Admin();
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception {
        CountryName givenCountryName = null;
        String countryCode = null;
        countryName = countryName.toUpperCase();
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

        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository1.findById(serviceProviderId);
        if(optionalServiceProvider.isPresent()) {
            ServiceProvider serviceProvider = optionalServiceProvider.get();
            Country country = new Country(givenCountryName, countryCode);
            country = countryRepository1.save(country);

            serviceProvider.getCountryList().add(country);
            serviceProvider = serviceProviderRepository1.save(serviceProvider);
            return serviceProvider;
        }

        return new ServiceProvider();
    }
}
