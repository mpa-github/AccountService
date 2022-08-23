package com.pavelmuravyev.accountservice.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USERS")
public class User {

    @Column(name = "USER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_LASTNAME")
    private String lastname;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_FAILED_ATTEMPT")
    private int failedAttempt;

    @Column(name = "USER_NON_LOCKED")
    private boolean accountNonLocked;

    @Column(name = "USER_TYPE")
    private String type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payroll> payrollList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_GROUPS", joinColumns = @JoinColumn(name = "FK_USER_ID"),
                                     inverseJoinColumns = @JoinColumn(name = "FK_GROUP_ID"))
    private Set<Group> userGroups = new LinkedHashSet<>();

    public User() {
    }

    public User(Long id,
                String name,
                String lastname,
                String email,
                String password,
                String type,
                List<Payroll> payrollList,
                Set<Group> userGroups) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.type = type;
        this.payrollList = payrollList;
        this.userGroups = userGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Payroll> getPayrollList() {
        return payrollList;
    }

    public void setPayrollList(List<Payroll> payrollList) {
        this.payrollList = payrollList;
    }

    public Set<Group> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<Group> userGroups) {
        this.userGroups = userGroups;
    }

    public void addGroup(Group group) {
        this.userGroups.add(group);
    }

    public void removeGroup(Group group) {
        this.userGroups.remove(group);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(name, user.name) &&
               Objects.equals(lastname, user.lastname) &&
               Objects.equals(email, user.email) &&
               Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, email, password);
    }
}
