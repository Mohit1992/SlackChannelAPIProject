package utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import pojos.testdataPojo.ChannelDetails;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestDataReader {

    /**
     * readTestData method is basically reading the csv file (testdataForCreateChannel) & converting it into Bean (ChannelDetails)
     */

    public static List<ChannelDetails> readTestData() throws IOException {

        Reader reader = Files.newBufferedReader((Paths.get("src/main/java/TestData/testdataForCreateChannel.csv")));

        CsvToBean<ChannelDetails> csvToBean =
                new CsvToBeanBuilder(reader)
                        .withType(ChannelDetails.class)
                        .withIgnoreLeadingWhiteSpace(false)
                        .build();

        List<ChannelDetails> channelDetails = csvToBean.parse();
        return channelDetails;
    }
}
