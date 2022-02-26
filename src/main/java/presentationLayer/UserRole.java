package presentationLayer;

import java.io.Serializable;

public enum UserRole implements Serializable {

    ADMINISTRATOR("administrator"), EMPLOYEE("employee"), CLIENT("client");
    private String name;
    private static final long serialVersionUID = 1L;

    UserRole(String name){
        this.name = name;
    }
}
