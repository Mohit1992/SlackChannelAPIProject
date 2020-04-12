package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import pojos.testdataPojo.ChannelDetails;
import utils.ReadPropertyFile;
import utils.TestDataReader;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseTestClass {

    public static final String BASE_URL = "https://slack.com/api"; // Base url of slack
    public Logger logger = LogManager.getLogger(this.getClass());
    public static final String okStatusPass = "true"; // Passed ok status of API response
    public static final String okStatusFail = "false"; // Failed ok status of API response

    public static final String positiveStatus = "positive"; // Positive status of test cases added in testdataForCreateChannel
    public static final String negativeStatus = "negative"; // Negative status of test cases added in testdataForCreateChannel
    public static final int statusCodePass = 200; // Expected passed status code of API response
    public static String DECRYPTEDTOKEN = null;

    /**
     * read data from the test data csv
     * and store it in the object.
     *
     * @return
     * @throws IOException
     */
    @DataProvider(name = "ChannelTestData")
    public Object[][] createTestData() throws IOException {
        List<ChannelDetails> channelDetails = TestDataReader.readTestData();
        Object[][] objArray = new Object[channelDetails.size()][];
        for (int i = 0; i < channelDetails.size(); i++) {
            objArray[i] = new Object[1];
            objArray[i][0] = channelDetails.get(i);
        }
        return objArray;
    }

    /**
     * Setup method will do the following things :-
     * 1. Reading the env.properties files using readConfFile method
     * 2. Setting the baseURI
     */
    @BeforeClass
    public static void setUp() {
        ReadPropertyFile.readConfFile();
        RestAssured.baseURI = BASE_URL;

        // Encoding string
        String str = ReadPropertyFile.prop.getProperty("TOKEN");

        // Getting decoder
        Base64.Decoder decoder = Base64.getDecoder();
        // Decoding string
        DECRYPTEDTOKEN = new String(decoder.decode(str));
    }

    /**
     * getRequest method is used to hit any API's GET request & will give its response.
     *
     * @param endpoint   - endpoint URL of API.
     * @param params     - query parameters of API request.
     * @param statusCode - expected status code of API response.
     * @return - returns response of an API.
     */
    public static Response getRequest(String endpoint, HashMap<String, String> params, int statusCode) {

        Response response = given().queryParams(params).when().get(endpoint).then().assertThat().statusCode(statusCode).and()
                .contentType(ContentType.JSON).extract().response();
        return response;
    }

    /**
     * postRequest method is used to hit any API's post request & will give its response.
     *
     * @param endpoint   - endpoint URL of API.
     * @param params     - query parameters of API request.
     * @param statusCode - expected status code of API response.
     * @param payload    - its an optional parameter which used to passed in body of API request.
     * @return - returns response of an API.
     */
    public static Response postRequest(String endpoint, HashMap<String, String> params, int statusCode, String... payload) {
        {
            Response response = given().queryParams(params).body(payload).when().post(endpoint).then()
                    .assertThat().statusCode(statusCode).extract().response();
            return response;
        }
    }

}
