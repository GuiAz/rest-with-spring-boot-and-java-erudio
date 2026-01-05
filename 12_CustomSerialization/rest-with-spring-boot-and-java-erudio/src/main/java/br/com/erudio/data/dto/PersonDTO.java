package br.com.erudio.data.dto;

import br.com.erudio.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
public class PersonDTO implements Serializable {

    //    private static final long serialVersionID = 1L;
    private static final long serialVersionUID = 1L;

    private Long id;
    //    @JsonProperty("first_name")

    private String firstName;
    //    @JsonProperty("last_name")

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    @JsonFormat(pattern = "dd/MM/yyy")
    private Date birthDay;

    private String adress;
    //    @JsonIgnore
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    @JsonFilter("PersonFilter")
    private String sensitiveData;

    public PersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return adress;
    }

    public void setAddress(String adress) {
        this.adress = adress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) object;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber()) && Objects.equals(getBirthDay(), personDTO.getBirthDay()) && Objects.equals(getAdress(), personDTO.getAdress()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getSensitiveData(), personDTO.getSensitiveData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber(), getBirthDay(), getAdress(), getGender(), getSensitiveData());
    }
}
