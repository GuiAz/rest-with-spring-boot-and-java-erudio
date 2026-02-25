package br.com.erudio.integrationtests.dto.wrappers.xml;

import br.com.erudio.integrationtests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PageModelPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<PersonDTO> content;
    public PageModelPerson() {}

    public List<PersonDTO> getContent() {
        return content;
    }

    public void setConten(List<PersonDTO> content) {
        this.content = content;
    }
}
