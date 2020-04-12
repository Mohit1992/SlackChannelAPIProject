# Pre requisite!

  - Java 1.8
  - mvn should be installed.

#How can you run:-

Go to directory where pom.xml is present then
  - run the command - "mvn clean test"
  - If you want to change the TOKEN. Then save the Base64 ENCODED TOKEN in the parameter of env.properties
  - You can see the logs in slackChannel.log
  
# Framework
  - The test data is in csv format (TestData/testdataForCreateChannel.csv)
  - The tests are written in data driven approach.
  - You can increase the test data and test should cater the new test data.
  - The framework used is RestAssured using rest client. 
  - Test classes are self explanatory.
 