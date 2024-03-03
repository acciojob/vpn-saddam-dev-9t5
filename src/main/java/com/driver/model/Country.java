// Note: Do not write @Enumerated annotation above CountryName in this model.
package com.driver.model;

import javax.persistence.*;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private CountryName countryName;

    private String code;

    @JoinColumn
    @OneToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private ServiceProvider serviceProvider;

    public Country() { }

    public Country(CountryName countryName) {
        this.countryName = countryName;
    }

    public Country(CountryName countryName, String code) {
        this.countryName = countryName;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public String getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}