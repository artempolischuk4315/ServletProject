package ua.polischuk.model.entity;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class User implements Serializable {
    private int id;
    private String firstName;
    private String firstNameUa;
    private String lastName;
    private String lastNameUa;
    private String email;
    private String password;
    private ROLE role;
    private double stats;

    private Set<Test> availableTests;
    private Map<Test, Integer> resultsOfTests;



    public Map<Test, Integer> getResultsOfTests() {
        return resultsOfTests;
    }

    public void setResultsOfTests(Map<Test, Integer> resultsOfTests) {
        this.resultsOfTests = resultsOfTests;
    }

    public Set<Test> getAvailableTests() {
        return availableTests;
    }

    public void setAvailableTests(HashSet<Test> availableTests) {
        this.availableTests = availableTests;
    }

    public double getStats() {
        return stats;
    }

    public void setStats(double stats) {
        this.stats = stats;
    }

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    public User(String firstName, String firstNameUa, String lastName, String lastNameUa, String email, String password, ROLE role) {
        this.firstName = firstName;
        this.firstNameUa = firstNameUa;
        this.lastName = lastName;
        this.lastNameUa = lastNameUa;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int id, String firstName, String firstNameUa, String lastName, String lastNameUa, String email, String password, ROLE role) {
        this.id = id;
        this.firstName = firstName;
        this.firstNameUa = firstNameUa;
        this.lastName = lastName;
        this.lastNameUa = lastNameUa;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameUa() {
        return firstNameUa;
    }

    public void setFirstNameUa(String firstNameUa) {
        this.firstNameUa = firstNameUa;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameUa() {
        return lastNameUa;
    }

    public void setLastNameUa(String lastNameUa) {
        this.lastNameUa = lastNameUa;
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

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", firstNameUa='" + firstNameUa + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastNameUa='" + lastNameUa + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", stats=" + stats +
                '}';
    }
}