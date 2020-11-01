package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@Slf4j
class LabRequestControllerTest {


    @InjectMocks
    LabRequestController labRequestController;


    @Mock
    TestRequestQueryService testRequestQueryService;


    @Mock
    UserLoggedInService userLoggedInService;

    @Mock
    TestRequestUpdateService testRequestUpdateService;

    @Test
    public void calling_assignForLabTest_should_return_TestRequest_and_call_testRequestUpdateService_assignForLabTest(){

        //Arrange
        User tester= createUser();
        TestRequest mockedTestRequest=createTestRequest();

        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(tester);
        Mockito.when(testRequestUpdateService.assignForLabTest(21L,tester)).thenReturn(mockedTestRequest);

        //Act
        TestRequest testRequest = labRequestController.assignForLabTest(21L);


        //Assert
        Contracts.assertNotNull(testRequest);
        assertEquals(testRequest.getAge(),mockedTestRequest.getAge());
        assertEquals(testRequest,mockedTestRequest);

        //TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);
        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        //TestRequest testRequest1= labRequestController.assignForLabTest(testRequest.requestId);

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'INITIATED'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result
//        testRequest1.setStatus(RequestStatus.INITIATED);
        //assertThat("ewal",testRequest.requestId,testRequest1.requestId);

    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    public void calling_assignForLabTest_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;

        //Arrange
        User tester= createUser();
        TestRequest mockedTestRequest=createTestRequest();

        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(tester);
        Mockito.when(testRequestUpdateService.assignForLabTest(InvalidRequestId,tester)).thenThrow(new AppException("Invalid id"));

        //Act
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.assignForLabTest(InvalidRequestId);
        });


        //Assert
        Contracts.assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
        assertEquals("Invalid id",result.getReason());



        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){


        //Arrange
        // TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        TestRequest testRequest= getUpdatedTestRequest();
        CreateLabResult createLabResult=getLabResult();
        User user=userLoggedInService.getLoggedInUser();

      //  Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        //Mockito.when(testRequestUpdateService.updateLabTest(22L,createLabResult,user)).thenReturn(testRequest);


        //Act
        TestRequest testRequest1=testRequestUpdateService.updateLabTest(22L,createLabResult,user);

        //Assert
        // Contracts.assertNotNull(testRequest1);
        //assertEquals(testRequest1.getStatus(),testRequest.getStatus());
       // assertEquals(testRequest1,testRequest);


        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.



    }

    private TestRequest getUpdatedTestRequest() {

        TestRequest testRequest= new TestRequest();

        testRequest.setStatus(RequestStatus.LAB_TEST_COMPLETED);
        testRequest.setRequestId(22L);
        testRequest.setName("some one");
        //testRequest.setLabResult(labResult);


        return testRequest;
    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);


        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){




        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"



    }

    public CreateLabResult getCreateLabResult(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // Return the object


        return null; // Replace this line with your code
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("someuser");
        return user;
    }

    public TestRequest createTestRequest() {
        TestRequest testRequest = new TestRequest();
        testRequest.setAddress("some Addres");
        testRequest.setAge(98);
        testRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        testRequest.setGender(Gender.MALE);
        testRequest.setName("someuser");
        testRequest.setPhoneNumber("123456789");
        testRequest.setPinCode(716768);
        return testRequest;
    }

    private CreateLabResult getLabResult() {

        CreateLabResult createLabResult= new CreateLabResult();

        createLabResult.setBloodPressure("99");
        createLabResult.setHeartBeat("75");
        createLabResult.setOxygenLevel("99");
        createLabResult.setTemperature("98.7");
        createLabResult.setComments("You are good");
        createLabResult.setResult(TestStatus.NEGATIVE);

        return createLabResult;
    }


}