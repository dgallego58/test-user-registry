package co.com.dgallego58.infrastructure.security.config.auxiliar;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestExclusions {

    private List<String> paths = new ArrayList<>();

}
