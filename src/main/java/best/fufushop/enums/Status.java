package best.fufushop.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
        @JsonProperty("success")
        SUCCESS,

        @JsonProperty("error")
        ERROR
}
