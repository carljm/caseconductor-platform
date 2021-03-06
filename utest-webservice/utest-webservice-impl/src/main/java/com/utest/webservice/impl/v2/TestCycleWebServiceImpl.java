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

import com.utest.domain.EnvironmentGroup;
import com.utest.domain.Permission;
import com.utest.domain.TestCycle;
import com.utest.domain.TestRun;
import com.utest.domain.search.UtestSearch;
import com.utest.domain.search.UtestSearchResult;
import com.utest.domain.service.TestCycleService;
import com.utest.webservice.api.v2.TestCycleWebService;
import com.utest.webservice.builders.ObjectBuilderFactory;
import com.utest.webservice.model.v2.EnvironmentGroupInfo;
import com.utest.webservice.model.v2.TestCycleInfo;
import com.utest.webservice.model.v2.TestCycleSearchResultInfo;
import com.utest.webservice.model.v2.TestRunInfo;
import com.utest.webservice.model.v2.UtestSearchRequest;

@Path("/testcycles/")
public class TestCycleWebServiceImpl extends BaseWebServiceImpl implements TestCycleWebService
{
	private final TestCycleService	testCycleService;

	public TestCycleWebServiceImpl(final ObjectBuilderFactory objectBuildFactory, final TestCycleService testCycleService)
	{
		super(objectBuildFactory);
		this.testCycleService = testCycleService;
	}

	@PUT
	@Path("/{id}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_SUITE_EDIT })
	public TestCycleInfo updateTestCycle(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_, @FormParam("") final TestCycleInfo testCycleInfo_)
			throws Exception
	{

		final TestCycle testCycle = testCycleService.saveTestCycle(testCycleId_, testCycleInfo_.getName(), testCycleInfo_.getDescription(), testCycleInfo_.getStartDate(),
				testCycleInfo_.getEndDate(), testCycleInfo_.isCommunityAuthoringAllowed(), testCycleInfo_.isCommunityAccessAllowed(), testCycleInfo_.getResourceIdentity()
						.getVersion());
		return objectBuilderFactory.toInfo(TestCycleInfo.class, testCycle, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/{id}/activate/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_SUITE_EDIT })
	public TestCycleInfo activateTestCycle(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_, @FormParam("") final TestCycleInfo testCycleInfo_)
			throws Exception
	{

		final TestCycle testCycle = testCycleService.activateTestCycle(testCycleId_, testCycleInfo_.getResourceIdentity().getVersion());
		return objectBuilderFactory.toInfo(TestCycleInfo.class, testCycle, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/{id}/deactivate/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_SUITE_EDIT })
	public TestCycleInfo deactivateTestCycle(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_, @FormParam("") final TestCycleInfo testCycleInfo_)
			throws Exception
	{

		final TestCycle testCycle = testCycleService.lockTestCycle(testCycleId_, testCycleInfo_.getResourceIdentity().getVersion());
		return objectBuilderFactory.toInfo(TestCycleInfo.class, testCycle, ui_.getBaseUriBuilder());
	}

	@PUT
	@Path("/{id}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_SUITE_EDIT })
	public Boolean updateTestCycleEnvironmentGroups(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_,
			@FormParam("environmentGroupIds") final ArrayList<Integer> environmentGroupIds_, @FormParam("originalVersionId") final Integer originalVesionId_) throws Exception
	{
		testCycleService.saveEnvironmentGroupsForTestCycle(testCycleId_, environmentGroupIds_, originalVesionId_);
		return Boolean.TRUE;
	}

	@GET
	@Path("/{id}/environmentgroups/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_SUITE_VIEW)
	public List<EnvironmentGroupInfo> getTestCycleEnvironmentGroups(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_) throws Exception
	{
		final List<EnvironmentGroup> groups = testCycleService.getEnvironmentGroupsForTestCycle(testCycleId_);
		return objectBuilderFactory.toInfo(EnvironmentGroupInfo.class, groups, ui_.getBaseUriBuilder());
	}

	@GET
	@Path("/{id}/testruns/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_SUITE_VIEW)
	public List<TestRunInfo> getTestCycleTestRuns(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_) throws Exception
	{
		final List<TestRun> includedTestRuns = testCycleService.getTestRunsForTestCycle(testCycleId_);
		return objectBuilderFactory.toInfo(TestRunInfo.class, includedTestRuns, ui_.getBaseUriBuilder());
	}

	@POST
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured( { Permission.TEST_SUITE_EDIT })
	public TestCycleInfo createTestCycle(@Context final UriInfo ui_, @FormParam("") final TestCycleInfo testCycleInfo_) throws Exception
	{
		final TestCycle testCycle = testCycleService.addTestCycle(testCycleInfo_.getProductId(), testCycleInfo_.getName(), testCycleInfo_.getDescription(), testCycleInfo_
				.getStartDate(), testCycleInfo_.getEndDate(), testCycleInfo_.isCommunityAuthoringAllowed(), testCycleInfo_.isCommunityAccessAllowed());

		return objectBuilderFactory.toInfo(TestCycleInfo.class, testCycle, ui_.getBaseUriBuilder());
	}

	@DELETE
	@Path("/{id}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_SUITE_EDIT)
	public Boolean deleteTestCycle(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_, @FormParam("originalVersionId") final Integer originalVesionId_)
			throws Exception
	{
		testCycleService.deleteTestCycle(testCycleId_, originalVesionId_);

		return Boolean.TRUE;
	}

	@GET
	@Path("/{id}/")
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_SUITE_VIEW)
	public TestCycleInfo getTestCycle(@Context final UriInfo ui_, @PathParam("id") final Integer testCycleId_) throws Exception
	{
		final TestCycle testCycle = testCycleService.getTestCycle(testCycleId_);
		return objectBuilderFactory.toInfo(TestCycleInfo.class, testCycle, ui_.getBaseUriBuilder());
	}

	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	@Secured(Permission.TEST_SUITE_VIEW)
	public TestCycleSearchResultInfo findTestCycles(@Context final UriInfo ui_, @QueryParam("") final UtestSearchRequest request_) throws Exception
	{
		final UtestSearch search = objectBuilderFactory.createSearch(TestCycleInfo.class, request_, ui_);
		final UtestSearchResult result = testCycleService.findTestCycles(search);

		return (TestCycleSearchResultInfo) objectBuilderFactory.createResult(TestCycleInfo.class, TestCycle.class, request_, result, ui_.getBaseUriBuilder());
	}

}
