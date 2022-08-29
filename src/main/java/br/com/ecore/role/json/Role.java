package br.com.ecore.role.json;

import br.com.ecore.utils.StringsUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role implements Serializable {

    private static final long serialVersionUID = -7097436085260995097L;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        if (StringUtils.isNotBlank(name)) {
            name = StringsUtils.prepareRoleName(name);
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
