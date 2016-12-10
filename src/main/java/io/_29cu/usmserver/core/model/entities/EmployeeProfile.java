package io._29cu.usmserver.core.model.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by yniu on 10/12/2016.
 */

@Entity
public class EmployeeProfile {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @OneToOne
    @NaturalId
    private User employee;
    @NotNull
    @Column(unique = true)
    private String email;
    @Column
    private String company;
    @Column
    private String jobTitle;
    @Column
    private Date joinDate;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private Integer zipCode;
    @Column
    private Integer workPhone;
    @Column
    private Integer homePhone;
    @Column
    private Date dateOfBirth;
    @Column
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(Integer workPhone) {
        this.workPhone = workPhone;
    }

    public Integer getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(Integer homePhone) {
        this.homePhone = homePhone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
