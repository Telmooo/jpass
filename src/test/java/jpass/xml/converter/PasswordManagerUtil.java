package jpass.xml.converter;

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

    @JsonCreator
    public PasswordManagerUtil(@JsonProperty("master-pass") String masterPassword) {
        this.masterPassword = masterPassword;
    }

    @JsonSetter(value = "master-pass")
    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    @JsonGetter(value = "master-pass")
    public String getMasterPassword() {
        return this.masterPassword;
    }

}