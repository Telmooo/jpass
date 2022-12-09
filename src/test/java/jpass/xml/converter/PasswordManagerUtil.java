package jpass.xml.converter;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "manager")
public class PasswordManagerUtil {
    @JacksonXmlProperty(localName = "master-pass")
    protected String masterPassword;
    protected ArrayList<String> passwordList;

    @JsonCreator
    public PasswordManagerUtil(@JsonProperty("master-pass") String masterPassword, @JsonProperty("pass-list") ArrayList<String> passwordList) {
        this.masterPassword = masterPassword;
        this.passwordList = passwordList;
    }

    @JsonSetter(value = "master-pass")
    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    @JsonGetter(value = "master-pass")
    public String getMasterPassword() {
        return this.masterPassword;
    }

    @JsonGetter(value = "pass-list")
    public ArrayList<String> getPasswordList() { return this.passwordList; }

}