package com.rest.stepDefinitions;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import com.jayway.restassured.response.Response;
import com.rest.utilities.ConfigReaderFile;
import com.rest.utilities.ServiceResponse;

import gherkin.deps.com.google.gson.JsonObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PostCommentsForUsersServiceStepDef {
	Response response;
	JSONObject userDetails;
	String baseURI;
	String responseBody;
	int statusCode;
	String validationMessage;

	@Given("I have a user resource API detail")
	public void i_have_a_user_resource_API_detail() {

		baseURI = ConfigReaderFile.getInstance().getBaseURL();
	}

	@When("I make a GET request to (.*)")
	public void i_make_a_GET_request_to(String resouceLine) throws JSONException {
		resouceLine = resouceLine.replaceAll("\"", "");
		String route = baseURI + resouceLine;
		statusCode = ServiceResponse.getServiceResponseStatuCode(route);
		responseBody = ServiceResponse.getServiceResponseDetails(route);

	}

	@Then("I get a (\\d+) status code in response")
	public void i_get_a_status_code_in_response(int expectedStatusCode) {
		
		validationMessage = String.format("ExpectedSucessCode = %s, ActualSucessCode = %s", expectedStatusCode,
				statusCode);
		Assert.assertEquals(validationMessage, expectedStatusCode, statusCode);
	}
	
	@Then("I get a successful response code and content details")
	public void i_get_a_successful_response_code_and_content_details() {

		int expectedStatusCode = 200;
		validationMessage = String.format("ExpectedSucessCode = %s, ActualSucessCode = %s", expectedStatusCode,
				statusCode);
		Assert.assertEquals(validationMessage, expectedStatusCode, statusCode);
		Assert.assertTrue("Response body is empty", !responseBody.isEmpty());

	}

	@When("I send a GET request to (.*) with (.*) value")
	public void i_send_a_GET_request_to_with_number(String resouceLine, String userId) throws JSONException {
		resouceLine = resouceLine.replaceAll("\"", "");
		userId = userId.replaceAll("\"", "");
		String route = String.format(baseURI + resouceLine + "/%s", userId);
		userDetails = ServiceResponse.getJSONResponseServiceDetails(route);

	}

	@Then("I should see username (.*) and (.*) details in response")
	public void i_should_see_username_and_email_details_in_response(String userName, String email)
			throws JSONException {
		userName = userName.replaceAll("\"", "");
		email = email.replaceAll("\"", "");
		String actualUserName = userDetails.getString("username");
		String actualEmail = userDetails.getString("email");
		validationMessage = String.format("ExpectedUserName = %s, ActualUserName = %s", userName, actualUserName);
		Assert.assertEquals(validationMessage, userName, actualUserName);
		validationMessage = String.format("ExpectedEmail = %s, ActualEmail = %s", email, actualEmail);
		Assert.assertEquals(validationMessage, email, actualEmail);

	}

	@Then("I should see posts details for (.*) and (.*) details in response")
	public void i_should_see_posts_details_for_and_details_in_response(String postId, String userId)
			throws JSONException {
		postId = postId.replaceAll("\"", "");
		userId = userId.replaceAll("\"", "");
		String actualPostId = userDetails.getString("id");
		String actualUserId = userDetails.getString("userId");
		validationMessage = String.format("ExpectedPostId = %s, ActualPostId = %s", postId, actualPostId);
		Assert.assertEquals(validationMessage, postId, actualPostId);
		validationMessage = String.format("ExpecteduserId = %s, ActualUserId = %s", userId, actualUserId);
		Assert.assertEquals(validationMessage, userId, actualUserId);

	}

	@Then("I should see comments on post for (.*) and (.*) details in response")
	public void i_should_see_comments_on_post_for_and_details_in_response(String commentId, String postId)
			throws JSONException {
		postId = postId.replaceAll("\"", "");
		commentId = commentId.replaceAll("\"", "");
		String actualCommentId = userDetails.getString("id");
		String actualPostId = userDetails.getString("postId");
		validationMessage = String.format("ExpectedCommentId = %s, ActualCommentId = %s", commentId, actualCommentId);
		Assert.assertEquals(validationMessage, commentId, actualCommentId);
		validationMessage = String.format("ExpectedPostId = %s, ActualPostId = %s", postId, actualPostId);
		Assert.assertEquals(validationMessage, postId, actualPostId);
	}

	@When("I send a POST \"([^\\\"]*)\" request (.*) (.*) (.*) (.*) details for Posts API")
	public void i_send_a_POST_request_details_for_Posts_API(String resouceLine,
			String userId, String postId, String postTitle, String postBody) throws JSONException {
		userId = userId.replaceAll("\"", "");
		postId = postId.replaceAll("\"", "");
		postTitle = postTitle.replaceAll("\"", "");
		postBody = postBody.replaceAll("\"", "");

		JsonObject postPayload = new JsonObject();
		postPayload.addProperty("userId", userId);
		postPayload.addProperty("id", postId);
		postPayload.addProperty("title", postTitle);
		postPayload.addProperty("body", postBody);

		String route = baseURI + resouceLine;
		statusCode = ServiceResponse.getPostServiceResponseStatuCode(route, postPayload);

	}

	@Then("I should see sucessful posted details in response")
	public void i_should_see_sucessful_posted_details_in_response() {
		int expectedStatusCode = 201;
		validationMessage = String.format("ExpectedSucessCode = %s, ActualSucessCode = %s", expectedStatusCode,
				statusCode);
		Assert.assertEquals(validationMessage, expectedStatusCode, statusCode);
	}
	
	@When("I send a POST \"([^\\\"]*)\" request (.*) (.*) (.*) (.*) (.*) details for CommentOnPosts API")
	public void i_send_a_POST_request_Test_User_test_gmail_com_Nice_Posts_details_for_CommentOnPosts_API(String resouceLine,
			String commentId, String postId, String userName, String userEmail,String commentBody) throws JSONException {
		commentId = commentId.replaceAll("\"", "");
		postId = postId.replaceAll("\"", "");
		userName = userName.replaceAll("\"", "");
		userEmail = userEmail.replaceAll("\"", "");
		commentBody = commentBody.replaceAll("\"", "");

		JsonObject commentPayload = new JsonObject();
		commentPayload.addProperty("postId", postId);
		commentPayload.addProperty("id", commentId);
		commentPayload.addProperty("name", userName);
		commentPayload.addProperty("email", userEmail);
		commentPayload.addProperty("body", commentBody);

		String route = baseURI + resouceLine;
		statusCode = ServiceResponse.getPostServiceResponseStatuCode(route, commentPayload);
	}


}
