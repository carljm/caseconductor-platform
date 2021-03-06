/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 * @author Vadim Kisen
 *
 * copyright 2010 by uTest 
 */
package com.utest.webservice.impl.v2;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.security.access.annotation.Secured;

import com.utest.domain.Environment;
import com.utest.domain.EnvironmentGroup;
import com.utest.domain.Permission;
import com.utest.domain.ProductComponent;
import com.utest.domain.TestRun;
import com.utest.domain.TestRunResult;
import com.utest.domain.TestRunTestCase;
import com.utest.domain.TestRunTestCaseAssignment;
import com.utest.domain.search.UtestSearch;
import com.utest.domain.search.UtestSearchResult;
import com.utest.domain.service.TestRunService;
import com.utest.webservice.api.v2.TestRunWebService;
import com.utest.webservice.builders.ObjectBuilderFactory;
import com.utest.webservice.model.v2.EnvironmentGroupInfo;
import com.utest.webservice.model.v2.EnvironmentInfo;
import com.utest.webservice.model.v2.IncludedTestCaseInfo;
import com.utest.webservice.model.v2.IncludedTestCaseSearchResultInfo;
import com.utest.webservice.model.v2.ProductComponentInfo;
import com.utest.webservice.model.v2.TestRunInfo;
import com.utest.webservice.model.v2.TestRunResultInfo;
import com.utest.webservice.model.v2.TestRunResultSearchResultInfo;
import com.utest.webservice.model.v2.TestRunSearchResultInfo;
import com.utest.webservice.model.v2.TestRunTestCaseAssignmentInfo;
import com.utest.webservice.model.v2.TestRunTestCaseAssignmentSearchResultInfo;
import com.utest.webservice.model.v2.UtestSearchRequest;

@Path("/testruns/")
public class TestRunWebServiceImpl extends BaseWebServiceImpl implements TestRunWebService
{
	private final TestRunService	testRunService;

	public TestRunWebServiceImpl(final ObjectBuilderFactory objectBuildFactory, final TestRunService testRunService)
	{
		super(objectBuildFactory);
		this.testRunService = testRunService;
	}

	// /////////// TEST RUN RELATED //////////////

	@PUT
	@Path("/{id}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public TestRunInfo updateTestRun(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_, @FormParam("") final TestRunInfo testRunInfo_) throws Exception
	{

		final TestRun testRun = testRunService.saveTestRun(testRunId_, testRunInfo_.getName(), testRunInfo_.getDescription(), testRunInfo_.getStartDate(), testRunInfo_
				.getEndDate(), testRunInfo_.isSelfAssignAllowed(), testRunInfo_.isSelfAssignPerEnvironment(), testRunInfo_.getSelfAssignLimit(), testRunInfo_.getResourceIdentity()
				.getVersion());

		return objectBuilderFactory.toInfo(TestRunInfo.class, testRun, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/{id}/activate/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public TestRunInfo activateTestRun(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_, @FormParam("originalVersionId") final Integer originalVersionId_)
			throws Exception
	{

		final TestRun testRun = testRunService.activateTestRun(testRunId_, originalVersionId_);
		return objectBuilderFactory.toInfo(TestRunInfo.class, testRun, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/{id}/deactivate/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public TestRunInfo deactivateTestRun(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_, @FormParam("originalVersionId") final Integer originalVersionId_)
			throws Exception
	{

		final TestRun testRun = testRunService.lockTestRun(testRunId_, originalVersionId_);
		return objectBuilderFactory.toInfo(TestRunInfo.class, testRun, ui_.getBaseUriBuilder());
	}

	@POST
	@Path("/{id}/retest/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public List<TestRunResultInfo> retestTestRun(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_,
			@FormParam("failedResultsOnly") final boolean failedResultsOnly_) throws Exception
	{

		final List<TestRunResult> results = testRunService.retestTestRun(testRunId_, failedResultsOnly_);
		return objectBuilderFactory.toInfo(TestRunResultInfo.class, results, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/{id}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean updateTestRunEnvironmentGroups(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_,
			@FormParam("environmentGroupIds") final ArrayList<Integer> environmentGroupIds_, @FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.saveEnvironmentGroupsForTestRun(testRunId_, environmentGroupIds_, originalVersionId_);
		return Boolean.TRUE;
	}

	@GET
	@Path("/{id}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<EnvironmentGroupInfo> getTestRunEnvironmentGroups(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_) throws Exception
	{
		final List<EnvironmentGroup> groups = testRunService.getEnvironmentGroupsForTestRun(testRunId_);
		return objectBuilderFactory.toInfo(EnvironmentGroupInfo.class, groups, ui_.getBaseUriBuilder());
	}

	@POST
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public TestRunInfo createTestRun(@Context final UriInfo ui_, @FormParam("") final TestRunInfo testRunInfo_) throws Exception
	{
		final TestRun testRun = testRunService.addTestRun(testRunInfo_.getTestCycleId(), testRunInfo_.isUseLatestVersions(), testRunInfo_.getName(), testRunInfo_.getDescription(),
				testRunInfo_.getStartDate(), testRunInfo_.getEndDate(), testRunInfo_.isSelfAssignAllowed(), testRunInfo_.isSelfAssignPerEnvironment(), testRunInfo_
						.getSelfAssignLimit());
		return objectBuilderFactory.toInfo(TestRunInfo.class, testRun, ui_.getBaseUriBuilder());
	}

	@DELETE
	@Path("/{id}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_EDIT)
	public Boolean deleteTestRun(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_, @FormParam("originalVersionId") final Integer originalVersionId_)
			throws Exception
	{
		testRunService.deleteTestRun(testRunId_, originalVersionId_);

		return Boolean.TRUE;
	}

	@GET
	@Path("/{id}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public TestRunInfo getTestRun(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_) throws Exception
	{
		final TestRun testRun = testRunService.getTestRun(testRunId_);
		return objectBuilderFactory.toInfo(TestRunInfo.class, testRun, ui_.getBaseUriBuilder());
	}

	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public TestRunSearchResultInfo findTestRuns(@Context final UriInfo ui_, @QueryParam("") final UtestSearchRequest request_) throws Exception
	{
		final UtestSearch search = objectBuilderFactory.createSearch(TestRunInfo.class, request_, ui_);
		final UtestSearchResult result = testRunService.findTestRuns(search);

		return (TestRunSearchResultInfo) objectBuilderFactory.createResult(TestRunInfo.class, TestRun.class, request_, result, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/{id}/components/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<ProductComponentInfo> getTestRunComponents(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_) throws Exception
	{
		final List<ProductComponent> components = testRunService.getTestRunComponents(testRunId_);
		return objectBuilderFactory.toInfo(ProductComponentInfo.class, components, ui_.getBaseUriBuilder());
	}

	// /////////// TEST RUN TEST CASES RELATED //////////////

	@GET
	@Path("/{id}/includedtestcases/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<IncludedTestCaseInfo> getTestRunTestCases(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_) throws Exception
	{
		final List<TestRunTestCase> includedTestCases = testRunService.getTestRunTestCases(testRunId_);
		return objectBuilderFactory.toInfo(IncludedTestCaseInfo.class, includedTestCases, ui_.getBaseUriBuilder());
	}

	@POST
	@Path("/{id}/includedtestcases/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public IncludedTestCaseInfo createTestRunTestCase(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_, @FormParam("") final IncludedTestCaseInfo testCaseInfo_)
			throws Exception
	{

		final TestRunTestCase includedTestCase = testRunService.addTestRunTestCase(testRunId_, testCaseInfo_.getTestCaseVersionId(), testCaseInfo_.getPriorityId(), testCaseInfo_
				.getRunOrder(), testCaseInfo_.isBlocking());
		return objectBuilderFactory.toInfo(IncludedTestCaseInfo.class, includedTestCase, ui_.getBaseUriBuilder());
	}

	@POST
	@Path("/{id}/includedtestcases/testplan/{testPlanId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public List<IncludedTestCaseInfo> createTestCasesFromTestPlan(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_,
			@PathParam("testPlanId") final Integer testPlanId_) throws Exception
	{

		final List<TestRunTestCase> includedTestCases = testRunService.addTestCasesFromTestPlan(testRunId_, testPlanId_);
		return objectBuilderFactory.toInfo(IncludedTestCaseInfo.class, includedTestCases, ui_.getBaseUriBuilder());
	}

	@POST
	@Path("/{id}/includedtestcases/testsuite/{testSuiteId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public List<IncludedTestCaseInfo> createTestCasesFromTestSuite(@Context final UriInfo ui_, @PathParam("id") final Integer testRunId_,
			@PathParam("testSuiteId") final Integer testSuiteId_) throws Exception
	{

		final List<TestRunTestCase> includedTestCases = testRunService.addTestCasesFromTestSuite(testRunId_, testSuiteId_);
		return objectBuilderFactory.toInfo(IncludedTestCaseInfo.class, includedTestCases, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/includedtestcases/{includedTestCaseId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public IncludedTestCaseInfo getTestRunTestCase(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_) throws Exception
	{
		final TestRunTestCase includedTestCase = testRunService.getTestRunTestCase(includedTestCaseId_);
		return objectBuilderFactory.toInfo(IncludedTestCaseInfo.class, includedTestCase, ui_.getBaseUriBuilder());
	}

	@DELETE
	@Path("/includedtestcases/{includedTestCaseId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_CASE_EDIT)
	public Boolean deleteTestRunTestCase(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_,
			@FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.deleteTestRunTestCase(includedTestCaseId_, originalVersionId_);

		return Boolean.TRUE;
	}

	@PUT
	@Path("/includedtestcases/{includedTestCaseId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_CASE_EDIT })
	public IncludedTestCaseInfo updateTestRunTestCase(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_,
			@FormParam("") final IncludedTestCaseInfo includedTestCaseInfo_) throws Exception
	{

		final TestRunTestCase includedTestCase = testRunService.saveTestRunTestCase(includedTestCaseId_, includedTestCaseInfo_.getPriorityId(),
				includedTestCaseInfo_.getRunOrder(), includedTestCaseInfo_.isBlocking(), includedTestCaseInfo_.getResourceIdentity().getVersion());

		return objectBuilderFactory.toInfo(IncludedTestCaseInfo.class, includedTestCase, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/includedtestcases/{includedTestCaseId}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean updateTestRunTestCaseEnvironmentGroups(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_,
			@FormParam("environmentGroupIds") final ArrayList<Integer> environmentGroupIds_, @FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.saveEnvironmentGroupsForTestRunTestCase(includedTestCaseId_, environmentGroupIds_, originalVersionId_);
		return Boolean.TRUE;
	}

	@GET
	@Path("/includedtestcases/{includedTestCaseId}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<EnvironmentGroupInfo> getTestRunTestCaseEnvironmentGroups(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_)
			throws Exception
	{
		final List<EnvironmentGroup> groups = testRunService.getEnvironmentGroupsForTestRunTestCase(includedTestCaseId_);
		return objectBuilderFactory.toInfo(EnvironmentGroupInfo.class, groups, ui_.getBaseUriBuilder());
	}

	// /////////// TEST CASES ASSIGNMENTS RELATED //////////////

	@GET
	@Path("/includedtestcases/{includedTestCaseId}/assignments/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<TestRunTestCaseAssignmentInfo> getTestRunTestCaseAssignments(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_)
			throws Exception
	{
		final List<TestRunTestCaseAssignment> assignments = testRunService.getTestRunTestCaseAssignments(includedTestCaseId_);
		return objectBuilderFactory.toInfo(TestRunTestCaseAssignmentInfo.class, assignments, ui_.getBaseUriBuilder());
	}

	@POST
	@Path("/includedtestcases/{includedTestCaseId}/assignments/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public TestRunTestCaseAssignmentInfo createTestRunTestCaseAssignment(@Context final UriInfo ui_, @PathParam("includedTestCaseId") final Integer includedTestCaseId_,
			@FormParam("") final TestRunTestCaseAssignmentInfo assignmentInfo_) throws Exception
	{

		final TestRunTestCaseAssignment assignment = testRunService.addAssignment(includedTestCaseId_, assignmentInfo_.getTesterId());
		return objectBuilderFactory.toInfo(TestRunTestCaseAssignmentInfo.class, assignment, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/assignments/{assignmentId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public TestRunTestCaseAssignmentInfo getTestRunTestCaseAssignment(@Context final UriInfo ui_, @PathParam("assignmentId") final Integer assignmentId_) throws Exception
	{
		final TestRunTestCaseAssignment assignment = testRunService.getTestRunTestCaseAssignment(assignmentId_);
		return objectBuilderFactory.toInfo(TestRunTestCaseAssignmentInfo.class, assignment, ui_.getBaseUriBuilder());
	}

	@DELETE
	@Path("/assignments/{assignmentId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_CASE_EDIT)
	public Boolean deleteTestRunTestCaseAssignment(@Context final UriInfo ui_, @PathParam("assignmentId") final Integer assignmentId_,
			@FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.deleteAssignment(assignmentId_, originalVersionId_);

		return Boolean.TRUE;
	}

	@PUT
	@Path("/assignments/{assignmentId}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean updateTestRunTestCaseAssignmentEnvironmentGroups(@Context final UriInfo ui_, @PathParam("assignmentId") final Integer assignmentId_,
			@FormParam("environmentGroupIds") final ArrayList<Integer> environmentGroupIds_, @FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.saveEnvironmentGroupsForAssignment(assignmentId_, environmentGroupIds_, originalVersionId_);
		return Boolean.TRUE;
	}

	@GET
	@Path("/assignments/{assignmentId}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<EnvironmentGroupInfo> getTestRunTestCaseAssignmentEnvironmentGroups(@Context final UriInfo ui_, @PathParam("assignmentId") final Integer assignmentId_)
			throws Exception
	{
		final List<EnvironmentGroup> groups = testRunService.getEnvironmentGroupsForAssignment(assignmentId_);
		return objectBuilderFactory.toInfo(EnvironmentGroupInfo.class, groups, ui_.getBaseUriBuilder());
	}

	// /////////// TEST CASES ASSIGNMENT RESULTS RELATED //////////////

	@GET
	@Path("/assignments/{assignmentId}/results/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<TestRunResultInfo> getTestRunTestCaseAssignmentResults(@Context final UriInfo ui_, @PathParam("assignmentId") final Integer assignmentId_) throws Exception
	{
		final List<TestRunResult> results = testRunService.getTestRunResultsForAssignment(assignmentId_);
		return objectBuilderFactory.toInfo(TestRunResultInfo.class, results, ui_.getBaseUriBuilder());
	}

	@POST
	@Path("/results/{resultId}/retest/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public TestRunResultInfo retestTestRunResult(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_, @FormParam("testerId") final Integer testerId_)
			throws Exception
	{

		final TestRunResult result = testRunService.retestTestRunResult(resultId_, testerId_);
		return objectBuilderFactory.toInfo(TestRunResultInfo.class, result, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/results/{resultId}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public TestRunResultInfo getTestRunResult(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_) throws Exception
	{
		final TestRunResult result = testRunService.getTestRunResult(resultId_);
		return objectBuilderFactory.toInfo(TestRunResultInfo.class, result, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/results/{resultId}/start/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean startTestRunResultExecution(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_,
			@FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.startExecutingAssignedTestCase(resultId_, originalVersionId_);
		return Boolean.TRUE;
	}

	@PUT
	@Path("/results/{resultId}/finishfail/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean finishFailedTestRunResultExecution(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_,
			@FormParam("") final TestRunResultInfo resultInfo_, @FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.finishExecutingAssignedTestCaseWithFailure(resultId_, resultInfo_.getFailedStepNumber(), resultInfo_.getActualResult(), resultInfo_.getComment(),
				originalVersionId_);
		return Boolean.TRUE;
	}

	@PUT
	@Path("/results/{resultId}/finishsucceed/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean finishSuccessfulTestRunResultExecution(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_,
			@FormParam("") final TestRunResultInfo resultInfo_, @FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.finishExecutingAssignedTestCaseWithSuccess(resultId_, resultInfo_.getComment(), originalVersionId_);
		return Boolean.TRUE;
	}

	@PUT
	@Path("/results/{resultId}/approve/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean approveTestRunResult(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_, @FormParam("originalVersionId") final Integer originalVersionId_)
			throws Exception
	{
		testRunService.approveTestRunResult(resultId_, originalVersionId_);
		return Boolean.TRUE;
	}

	@PUT
	@Path("/results/{resultId}/reject/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_RUN_EDIT })
	public Boolean rejectTestRunResult(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_, @FormParam("comment") final String comment_,
			@FormParam("originalVersionId") final Integer originalVersionId_) throws Exception
	{
		testRunService.rejectTestRunResult(resultId_, comment_, originalVersionId_);
		return Boolean.TRUE;
	}

	@GET
	@Path("/results/{resultId}/environments/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public List<EnvironmentInfo> getTestRunResultEnvironments(@Context final UriInfo ui_, @PathParam("resultId") final Integer resultId_) throws Exception
	{
		final List<Environment> environments = testRunService.getEnvironmentsForTestResult(resultId_);
		return objectBuilderFactory.toInfo(EnvironmentInfo.class, environments, ui_.getBaseUriBuilder());
	}

	// /////// TEST RUN COVERAGE RELATED //////////////

	@GET
	@Path("/includedtestcases/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public IncludedTestCaseSearchResultInfo findTestRunTestCases(@Context final UriInfo ui_, @QueryParam("") final UtestSearchRequest request_) throws Exception
	{
		final UtestSearch search = objectBuilderFactory.createSearch(IncludedTestCaseSearchResultInfo.class, request_, ui_);
		final UtestSearchResult result = testRunService.findTestRunTestCases(search);

		return (IncludedTestCaseSearchResultInfo) objectBuilderFactory.createResult(IncludedTestCaseInfo.class, TestRunTestCase.class, request_, result, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/assignments/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public TestRunTestCaseAssignmentSearchResultInfo findTestRunAssignments(@Context final UriInfo ui_, @QueryParam("") final UtestSearchRequest request_) throws Exception
	{
		final UtestSearch search = objectBuilderFactory.createSearch(TestRunTestCaseAssignmentInfo.class, request_, ui_);
		final UtestSearchResult result = testRunService.findTestRunAssignments(search);

		return (TestRunTestCaseAssignmentSearchResultInfo) objectBuilderFactory.createResult(TestRunTestCaseAssignmentInfo.class, TestRunTestCaseAssignment.class, request_,
				result, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/results/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_RUN_VIEW)
	public TestRunResultSearchResultInfo findTestRunResults(@Context final UriInfo ui_, @QueryParam("") final UtestSearchRequest request_) throws Exception
	{
		final UtestSearch search = objectBuilderFactory.createSearch(TestRunResultInfo.class, request_, ui_);
		final UtestSearchResult result = testRunService.findTestRunResults(search);

		return (TestRunResultSearchResultInfo) objectBuilderFactory.createResult(TestRunResultInfo.class, TestRunResult.class, request_, result, ui_.getBaseUriBuilder());
	}

}
