package constant;

import lombok.Getter;

@Getter
public enum CartStrategyEnum {
    DEFAULT("default"),
    PROMOTION("promotion"),
    BUNDLE("bundle");

    private final String value;

    CartStrategyEnum(String value) {
        this.value = value;
    }

}