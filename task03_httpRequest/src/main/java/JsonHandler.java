import java.util.Map;

public class JsonHandler implements ResourceHandler {
    @Override
    public String handle(Map<String, String> params) {

        String json = "{\"name\":\"value\"}";
        return json;
    }
}