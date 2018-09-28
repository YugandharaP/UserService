package com.bridgelabz.userservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class TestModels {
	private List<UserRegModal> userRegModals;
	private List<UserTestCase> userTestCases;
	private transient Map<String, ArrayList<Integer>> mCategoryTests;
	private transient Map<String, UserRegModal> mUserRegModals;
	private transient Map<Integer, UserTestCase> mUserTestCases;

	public List<UserRegModal> getUserRegModals() {
		return userRegModals;
	}

	public void setUserRegModals(List<UserRegModal> userRegModals) {
		this.userRegModals = userRegModals;
	}

	public List<UserTestCase> getUserTestCases() {
		return userTestCases;
	}

	public void setUserTestCases(List<UserTestCase> userTestCases) {
		this.userTestCases = userTestCases;
	}

	public void constructTestCases() {
		if (mUserTestCases == null || mCategoryTests == null) {
			mUserTestCases = new HashMap<Integer, UserTestCase>();
			mCategoryTests = new HashMap<String, ArrayList<Integer>>();
			for (UserTestCase testCases : userTestCases) {
				mUserTestCases.put(testCases.getId(), testCases);
				ArrayList<Integer> testIds = mCategoryTests.get(testCases.getCategory());
				if (testIds == null) {
					testIds = new ArrayList<Integer>();
					mCategoryTests.put(testCases.getCategory(), testIds);
				}
				testIds.add(testCases.getId());
			}
		}
		if (mUserRegModals == null) {
			mUserRegModals = new HashMap<String, UserRegModal>();
			for (UserRegModal userModal : userRegModals) {
				mUserRegModals.put(userModal.getType(), userModal);
			}
		}
	}

	/**
	 * Retrieves the UserTestCase Object for the Test IS
	 * 
	 * @param testId
	 * @return UserTestCase Object or Exception
	 */
	public UserTestCase getTestCase(int testId) {
		UserTestCase testCase = mUserTestCases.get(testId);
		if (testCase != null)
			return testCase;
		throw new RuntimeException("Test Case Id is Invalid");
	}

	/**
	 * Retrieves the UserTestCase Object for the Test IS
	 * 
	 * @param type
	 *            Type of User Test being done. Need to be unique
	 * @return UserTestCase Object or Exception
	 */
	public UserRegModal getUserRegModal(String type) {
		UserRegModal userModal = mUserRegModals.get(type);
		return userModal;
	}

	/**
	 * This method return ArrayList of test ids for the Category
	 * 
	 * @param category
	 *            for the test cases
	 * @return ArrayList of test ids
	 */
	public ArrayList<Integer> getTestIds(String category) {
		return mCategoryTests.get(category);
	}
}
