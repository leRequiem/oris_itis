import java.util.Map;

public interface ResourceHandler {

    /**
     *
     * @param params [name:value]
     * @return
     */
    String handle(Map<String, String> params);
}