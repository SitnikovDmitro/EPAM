package service;


public class JsonService {
    public String getJsonWithIntResult(int value) {
        return "{\"result\":"+value+"}";
    }

    public String getJsonWithBooleanResult(boolean value) {
        return "{\"result\":"+value+"}";
    }
}
