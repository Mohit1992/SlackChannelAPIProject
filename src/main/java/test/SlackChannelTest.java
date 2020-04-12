package test;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.responsePojo.Channel;
import pojos.responsePojo.ListChannelPojo;
import pojos.responsePojo.SlackChannelPojo;
import pojos.testdataPojo.ChannelDetails;
import utils.EndPointURL;
import utils.ReadPropertyFile;
import utils.SlackChannelAPI;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class SlackChannelTest extends BaseTestClass {

    private HashMap<String, String> channelIDAndName = new HashMap<String, String>(); // Map contains key - Channel ID & value - Channel name created by user.
    private List<String> createdSlackChannelIDs = new ArrayList<String>(); // List contains channel IDs created by user.
    private List<String> leftSlackChannelIDs = new ArrayList<String>(); // List contains channel IDs which user left.
    private String renamedString = "renamedas"; // variable which we will append it in front of slack channel name which is being renamed.
    private String renamedSlackChannelID = null; // contains slack channel id which got renamed.
    private String archiveSlackChannelID = null; // contains slack channel id which got archived.
    SlackChannelPojo renameChannelPojo; // contains Pojo of rename API response.
    private ThreadLocal<String> testName = new ThreadLocal<>();

    /**
     * createSlackChannelTest test does the following things :-
     * 1. Read the channel details from the test data file
     * 2. Hit the api to create the channel
     * 3. If the api is a positive case then assert will the positive response
     * 4. If the channel is negative, then error message will be asserted with the already stored error message & error details
     */
    @Test(dataProvider = "ChannelTestData")
    public void createSlackChannelTest(ChannelDetails channelDetails, Method method) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", DECRYPTEDTOKEN);
        params.put("name", channelDetails.getChannelName());
        params.put("validate", ReadPropertyFile.prop.getProperty("VALIDATE"));

        Response response = postRequest(EndPointURL.CREATE.url(), params, statusCodePass);
        logger.info("Create Slack Channel response as : " + response.asString());
        SlackChannelPojo createChannelPojo = new Gson().fromJson(response.asString(), SlackChannelPojo.class);
        if (channelDetails.getChannelCreationStatus().equalsIgnoreCase(positiveStatus)) {
            Assert.assertEquals(createChannelPojo.getOk(), okStatusPass);
            logger.info("Create Slack Channel OK status verified successfully for positive case");
            String ChannelID = createChannelPojo.getChannel().getId();
            channelIDAndName.put(createChannelPojo.getChannel().getId(), createChannelPojo.getChannel().getName());
            logger.info("Added Channel ID & Channel name in Hash Map successfully : " + createChannelPojo.getChannel().getId() + " " + createChannelPojo.getChannel().getName());
            createdSlackChannelIDs.add(createChannelPojo.getChannel().getId());
            logger.info("Added Channel ID in list successfully : " + createChannelPojo.getChannel().getId() );
        } else if (channelDetails.getChannelCreationStatus().equalsIgnoreCase(negativeStatus)) {
            Assert.assertEquals(createChannelPojo.getOk(), okStatusFail);
            logger.info("Create slack channel OK status verified successfully for negative case");
            Assert.assertEquals(createChannelPojo.getError(), channelDetails.getError());
            logger.error("Error is : " + createChannelPojo.getError());
            Assert.assertEquals(createChannelPojo.getDetail(), channelDetails.getErrorDetail());
            logger.error("Error Details is : " + createChannelPojo.getDetail());
        }
    }

    /**
     * leaveSlackChannelTest is dependent on createSlackChannelTest.
     * It will hit the leave channel API which we will leave the user from the first channels id of list (createdSlackChannelIDs)
     */
    @Test(dependsOnMethods = {"createSlackChannelTest"})
    public void leaveSlackChannelTest() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", DECRYPTEDTOKEN);
        params.put("channel", createdSlackChannelIDs.get(0));
        String leftSlackChannelID = createdSlackChannelIDs.get(0);
        Response response = postRequest(EndPointURL.LEAVE.url(), params, statusCodePass);
        logger.info("Leave Slack Channel response as : " + response.asString());

        SlackChannelPojo leaveChannelPojo = new Gson().fromJson(response.asString(), SlackChannelPojo.class);

        Assert.assertEquals(leaveChannelPojo.getOk(), okStatusPass);
        logger.info("Leave Slack Channel OK status verified successfully");
        Assert.assertEquals(leaveChannelPojo.getNot_in_channel(), null);
        leftSlackChannelIDs.add(leftSlackChannelID);
        logger.info("User has left the channel : " + channelIDAndName.get(leftSlackChannelIDs.get(0)));

    }

    /**
     * joinSlackChannelTest is dependent on createSlackChannelTest & leaveSlackChannelTest
     * It will hit the join channel API for all the channel created by user & which is present in channelIDAndName Map.
     * There are two cases which are being covered in below test :-
     * 1. User is not joined in the channel but had created that channel before & left it. In this testcase, Already_in_channel should be null
     * 2. User is already present in the channel. In this testcase, Already_in_channel should be true
     */
    @Test(dependsOnMethods = {"createSlackChannelTest", "leaveSlackChannelTest"})
    public void joinSlackChannelTest() {

        for (int i = 0; i < channelIDAndName.size(); i++) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("token", DECRYPTEDTOKEN);
            params.put("name", channelIDAndName.get(createdSlackChannelIDs.get(i)));
            params.put("validate", ReadPropertyFile.prop.getProperty("VALIDATE"));

            Response response = postRequest(EndPointURL.JOIN.url(), params, statusCodePass);
            logger.info("Joined Slack Channel response : " + response.asString());

            SlackChannelPojo joinChannelPojo = new Gson().fromJson(response.asString(), SlackChannelPojo.class);

            Assert.assertEquals(joinChannelPojo.getOk(), okStatusPass);
            logger.info("Join Slack Channel OK status verified successfully");

            if (joinChannelPojo.getAlready_in_channel() == null) {
                logger.info("User has joined the slack channel : " + joinChannelPojo.getChannel().getName());
            } else if (joinChannelPojo.getAlready_in_channel().equalsIgnoreCase("true")) {
                logger.info("User had already joined this slack channel : " + joinChannelPojo.getChannel().getName());
            }
        }
    }

    /**
     * renameSlackChannelTest is dependent on createSlackChannelTest.
     * Hit the rename API using first element of list (createdSlackChannelIDs) which has list of all slack channels created by user.
     * Save the slack channel id which is being renamed in renamedSlackChannelID
     * Also modify the renamed slack channel in Map (channelIDAndName).
     */
    @Test(dependsOnMethods = {"createSlackChannelTest"}, priority = 1)
    public void renameSlackChannelTest() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", DECRYPTEDTOKEN);
        params.put("channel", createdSlackChannelIDs.get(0));
        params.put("name", renamedString + channelIDAndName.get(createdSlackChannelIDs.get(0)));
        params.put("validate", ReadPropertyFile.prop.getProperty("VALIDATE"));

        renamedSlackChannelID = createdSlackChannelIDs.get(0);

        Response response = postRequest(EndPointURL.RENAME.url(), params, statusCodePass);
        logger.info("Renamed Slack Channel response : " + response.asString());

        renameChannelPojo = new Gson().fromJson(response.asString(), SlackChannelPojo.class);

        Assert.assertEquals(renameChannelPojo.getOk(), okStatusPass);
        logger.info("Rename Slack Channel OK status verified successfully");
        Assert.assertEquals(renameChannelPojo.getChannel().getName(), renamedString + channelIDAndName.get(createdSlackChannelIDs.get(0)));
        logger.info("Renamed of slack happened successfully as : " + renameChannelPojo.getChannel().getName());

        channelIDAndName.replace(createdSlackChannelIDs.get(0), renameChannelPojo.getChannel().getName());
        logger.info("Successfully updated channel ID and Name Hash Map ");
    }

    /**
     * listOfSlackChannelsAndValidateRenamedSlackChannelTest is dependent on renameSlackChannelTest
     * It will hit the list channel API,to get all the slack channels created by user & save it in Map (listOfSlackChannelIDsAndName )with key - ID & value - channel name.
     * Now, we need to compare the value of two Hash Map (listOfSlackChannelIDsAndName & channelIDAndName) for the key - renamedSlackChannelID.
     * If the value doesn't match then test case is failed. Asserting the same using renamedHappened boolean flag.
     */
    @Test(dependsOnMethods = {"renameSlackChannelTest"})
    public void listOfSlackChannelsAndValidateRenamedSlackChannelTest() {

        HashMap<String, String> listOfSlackChannelIDsAndName = new HashMap<String, String>();

        Response response = SlackChannelAPI.getListOFChannel();
        logger.info("List of slack channel's response : " + response.asString());

        ListChannelPojo listChannelPojo = new Gson().fromJson(response.asString(), ListChannelPojo.class);

        Assert.assertEquals(listChannelPojo.getOk(), okStatusPass);
        logger.info("List Slack Channel OK status verified successfully");


        for (Channel channel : listChannelPojo.getChannels()) {
            listOfSlackChannelIDsAndName.put(channel.getId(), channel.getName());
        }

        logger.info("List of all slack channels name with their ids as below ");
        for (String key : listOfSlackChannelIDsAndName.keySet()) {
            logger.info("Slack Channel ID : " + key + " " + " Slack Channel Name : " + listOfSlackChannelIDsAndName.get(key));
        }
        logger.info("********* END OF SLACK CHANNELS LIST *********");
        boolean renamedHappened = false;
        for (String key : listOfSlackChannelIDsAndName.keySet()) {
            if (key.equals(renamedSlackChannelID)) {
                Assert.assertEquals(listOfSlackChannelIDsAndName.get(key), channelIDAndName.get(key));
                logger.info("Renamed of slack channel successfully changed as : " + listOfSlackChannelIDsAndName.get(key));
                renamedHappened = true;
            }
        }
        Assert.assertEquals(renamedHappened, true);
    }

    /**
     * archiveSlackChannelTest is dependent on createSlackChannelTest & has given priority - 2. It should run after renameSlackChannelTest which has priority - 1.
     * Hit the archive API using first element of list (createdSlackChannelIDs) which has list of all slack channels created by user.
     * Save the slack channel id which is being renamed in archiveSlackChannelID
     */
    @Test(dependsOnMethods = {"createSlackChannelTest"}, priority = 2)
    public void archiveSlackChannelTest() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", DECRYPTEDTOKEN);
        params.put("channel", createdSlackChannelIDs.get(0));
        archiveSlackChannelID = createdSlackChannelIDs.get(0);

        Response response = postRequest(EndPointURL.ARCHIVE.url(), params, statusCodePass);
        logger.info("Archived Slack Channel response as : " + response.asString());

        SlackChannelPojo archiveChannelPojo = new Gson().fromJson(response.asString(), SlackChannelPojo.class);

        Assert.assertEquals(archiveChannelPojo.getOk(), okStatusPass);
        logger.info("Archive Slack Channel OK status verified successfully");
        logger.info("Archived the slack channel named " + channelIDAndName.get(archiveSlackChannelID) + " successfully ");
    }

    /**
     * archiveSlackChannelValidationTest is dependent on archiveSlackChannelTest
     * Get the list of slack channels created by user using list channel API.
     * Now, check for the flag (is_archive) of channel from the list of channels which has slack id matched as archiveSlackChannelID.
     */
    @Test(dependsOnMethods = {"archiveSlackChannelTest"})
    public void archiveSlackChannelValidationTest() {

        Response response = SlackChannelAPI.getListOFChannel();
        logger.info("List of slack channel's response as : " + response.asString());

        ListChannelPojo listChannelPojo = new Gson().fromJson(response.asString(), ListChannelPojo.class);

        Assert.assertEquals(listChannelPojo.getOk(), okStatusPass);
        logger.info("List Slack Channel OK status verified successfully");

        Channel[] channelList = listChannelPojo.getChannels();

        boolean archivedHappened = false;
        for (int i = 0; i < channelList.length; i++) {
            if (archiveSlackChannelID.equals(channelList[i].getId())) {
                Assert.assertEquals(channelList[i].getIs_archived(), "true");
                logger.info("Slack Channel named : " + channelList[i].getName() + " got archived successfully");
                archivedHappened = true;
            }
        }
        Assert.assertEquals(archivedHappened, true);

    }
}
