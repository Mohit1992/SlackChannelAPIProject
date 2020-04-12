package utils;

import io.restassured.response.Response;
import test.BaseTestClass;

import java.util.HashMap;

public class SlackChannelAPI extends BaseTestClass {

    /**
     * getListOFChannel is used to hit the API & get the list of slack channels created by user as response.
     */
    public static Response getListOFChannel() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", DECRYPTEDTOKEN);
        Response response = getRequest(EndPointURL.LIST.url(), params, statusCodePass);
        return response;
    }
}
