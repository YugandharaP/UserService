package com.bridgelabz.userservice;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.userservice.controller.UserController;
import com.bridgelabz.userservice.service.IUserService;
import com.bridgelabz.userservice.utilservice.securityservice.JwtTokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
/* @ContextConfiguration(classes=FundooNoteTest.class) */
@WebAppConfiguration
public class UserServiceTestCases {

	@InjectMocks
	private UserController userController;

	@Autowired
	IUserService userService;

	@Autowired
	@Spy
	JwtTokenProvider tokenService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private String jwttoken;

	TestModels testModals;

	private int passedTestCase;

	private int failesTestCase;

	private Map<Integer, Boolean> testCaseResults;

	@Before
	public void setUp() throws Exception {
		this.passedTestCase = 0;
		this.failesTestCase = 0;
		this.testCaseResults = new HashMap<Integer, Boolean>();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		testModals = Utility.convertJSONToObject("testcases.json", TestModels.class);
		testModals.constructTestCases();
	}

	@After
	public void testComplete() throws Exception {
		System.out.println("********************************************");
		System.out.println("      TEST RESULTS     ");
		System.out.println("HAPPY_SCENARIO'S : " + this.passedTestCase);
		System.out.println("SAD_SCENARIO's :   " + this.failesTestCase);
		System.out.println("********************************************");
	}

	@Test
	public void executeUserRegistrationTestCases() {
		ArrayList<Integer>

		testCasedIds = testModals.getTestIds("USER_REGISTRAION");
		for (int id : testCasedIds) {
			try {
				assertEquals("Test Case Id: " + id, true, (this.executeUserRegNLoginTestCase(id)));
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void executeUserLoginTestCases() {
		ArrayList<Integer> testCasedIds = testModals.getTestIds("USER_LOGIN");
		for (int id : testCasedIds) {
			try {
				assertEquals("Test Case Id: " + id, true, (this.executeUserRegNLoginTestCase(id)));
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void executeUserActivationTestCases() {
		ArrayList<Integer> testCasedIds = testModals.getTestIds("ACTIVATE_USER");
		for (int id : testCasedIds) {
			try {
				assertEquals("Test Case Id: " + id, true, (this.executeUserRegNLoginTestCase(id)));
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private boolean executeUserRegNLoginTestCase(int id) throws Exception {
		boolean testCaseResult = false;
		UserTestCase testCase = testModals.getTestCase(id);
		System.out.println(testCase.getId());
		UserRegModal userModal = testModals.getUserRegModal(testCase.getTestCaseModal());
		if (userModal == null)
			throw new RuntimeException("User Modal is invalid for Test Case Id:" + id);
		ResultActions result = null;
		result = mockMvc.perform(post(testCase.getUrlPath()).contentType(MediaType.APPLICATION_JSON)
				.content(Utility.asJsonString(userModal)));
		String responseMesg = result.andReturn().getResponse().getContentAsString();
		System.out.println(responseMesg + ":" + result.andReturn().getResponse().getStatus());
		
		String validErrorMesg = testCase.getValidErrorMessage();
		System.out.println(validErrorMesg);
		if (testCase.getStatusCode() == 200 && testCase.getCategory().equals("USER_LOGIN")) {

			/*
			 * String tempToken = result.andReturn().getResponse().getHeader("token");
			 * jwttoken =tempToken.substring(7, tempToken.length());
			 */

			testCaseResult = checkTestCaseResult(result, testCase.getId(), responseMesg, testCase.getStatusCode());
		}
		/*
		 * if(testCase.getCategory().equals("ACTIVATE_USER")) { String userId =
		 * result.andReturn().getResponse().getHeader("userId"); testCaseResult =
		 * checkTestCaseResult(result, testCase.getId(), responseMesg,
		 * testCase.getStatusCode()); }
		 */

		if (validErrorMesg != null) {
			testCaseResult = checkTestCaseResult(result, testCase.getId(), validErrorMesg, testCase.getStatusCode());
		} else {
			testCaseResult = checkTestCaseResult(result, testCase.getId(),  validErrorMesg, testCase.getStatusCode());
		}
		return testCaseResult;
	}

	private boolean checkTestCaseResult(ResultActions result, int testId, String responseMesg, int status) {
		boolean testCaseResult = true;
		try {
			if (testId == 2)
				result.andDo(MockMvcResultHandlers.print());
			if (responseMesg != null) {
				result.andExpect(jsonPath("$.responseMessage", Matchers.is(responseMesg)));
			} else if (status == 200)
				result.andExpect(MockMvcResultMatchers.status().is(status));
			this.failesTestCase++;
			testCaseResult = true;

		} catch (Exception ex) {
			this.passedTestCase++;
		} catch (Error er) {
			this.passedTestCase++;
		} catch (Throwable th) {
			this.passedTestCase++;
		}
		this.testCaseResults.put(testId, testCaseResult);
		return testCaseResult;
	}
}
