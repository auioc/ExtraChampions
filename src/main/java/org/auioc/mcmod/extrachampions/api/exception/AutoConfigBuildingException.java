package org.auioc.mcmod.extrachampions.api.exception;

public class AutoConfigBuildingException extends RuntimeException {

    public AutoConfigBuildingException(String message) {
        super(message);
    }

    public AutoConfigBuildingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoConfigBuildingException(Throwable cause) {
        this("Failed to auto build config: ", cause);
    }

}
