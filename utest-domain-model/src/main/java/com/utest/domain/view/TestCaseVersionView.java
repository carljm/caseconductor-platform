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
package com.utest.domain.view;

// Generated Sep 3, 2010 1:57:10 PM by Hibernate Tools 3.2.4.GA

import java.util.Date;

import com.utest.domain.EnvironmentDependable;
import com.utest.domain.TimelineEntity;
import com.utest.domain.Versioned;

/**
 * TestCaseVersion generated by hbm2java
 */
public class TestCaseVersionView extends TimelineEntity implements EnvironmentDependable, Versioned
{

	private String	name;
	private Integer	maxAttachmentSizeInMbytes;
	private Integer	maxNumberOfAttachments;
	private Integer	testCycleId;
	private Integer	testCaseId;
	private Integer	productId;
	private Integer	companyId;
	private Integer	testCaseStatusId;
	private String	description;
	private Integer	majorVersion;
	private Integer	minorVersion;
	private boolean	latestVersion;
	private Integer	approvalStatusId;
	private Integer	approvedBy;
	private Date	approveDate;
	private Integer	environmentProfileId;
	private boolean	automated;
	private String	automationUri;

	public TestCaseVersionView()
	{
	}

	public Integer getTestCaseId()
	{
		return this.testCaseId;
	}

	public void setTestCaseId(final Integer testCaseId)
	{
		this.testCaseId = testCaseId;
	}

	public Integer getTestCaseStatusId()
	{
		return this.testCaseStatusId;
	}

	public void setTestCaseStatusId(final Integer testCaseStatusId)
	{
		this.testCaseStatusId = testCaseStatusId;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public Integer getMajorVersion()
	{
		return this.majorVersion;
	}

	public void setMajorVersion(final Integer majorVersion)
	{
		this.majorVersion = majorVersion;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isAutomated()
	{
		return automated;
	}

	public void setAutomated(boolean automated)
	{
		this.automated = automated;
	}

	public String getAutomationUri()
	{
		return automationUri;
	}

	public void setAutomationUri(String automationUri)
	{
		this.automationUri = automationUri;
	}

	public Integer getMaxAttachmentSizeInMbytes()
	{
		return maxAttachmentSizeInMbytes;
	}

	public void setMaxAttachmentSizeInMbytes(Integer maxAttachmentSizeInMbytes)
	{
		this.maxAttachmentSizeInMbytes = maxAttachmentSizeInMbytes;
	}

	public Integer getMaxNumberOfAttachments()
	{
		return maxNumberOfAttachments;
	}

	public void setMaxNumberOfAttachments(Integer maxNumberOfAttachments)
	{
		this.maxNumberOfAttachments = maxNumberOfAttachments;
	}

	public Integer getTestCycleId()
	{
		return testCycleId;
	}

	public void setTestCycleId(Integer testCycleId)
	{
		this.testCycleId = testCycleId;
	}

	public Integer getMinorVersion()
	{
		return this.minorVersion;
	}

	public void setMinorVersion(final Integer minorVersion)
	{
		this.minorVersion = minorVersion;
	}

	public boolean isLatestVersion()
	{
		return this.latestVersion;
	}

	public void setLatestVersion(final boolean latestVersion)
	{
		this.latestVersion = latestVersion;
	}

	public Integer getApprovalStatusId()
	{
		return this.approvalStatusId;
	}

	public void setApprovalStatusId(final Integer approvalStatusId)
	{
		this.approvalStatusId = approvalStatusId;
	}

	public Integer getApprovedBy()
	{
		return this.approvedBy;
	}

	public void setApprovedBy(final Integer approvedBy)
	{
		this.approvedBy = approvedBy;
	}

	public Date getApproveDate()
	{
		return this.approveDate;
	}

	public void setApproveDate(final Date approveDate)
	{
		this.approveDate = approveDate;
	}

	public Integer getEnvironmentProfileId()
	{
		return this.environmentProfileId;
	}

	public void setEnvironmentProfileId(final Integer environmentProfileId)
	{
		this.environmentProfileId = environmentProfileId;
	}

	public void setCompanyId(Integer companyId)
	{
		this.companyId = companyId;
	}

	public Integer getCompanyId()
	{
		return companyId;
	}

	public void setProductId(final Integer productId)
	{
		this.productId = productId;
	}

	public Integer getProductId()
	{
		return productId;
	}

	@Override
	public Integer getMainEntityIdentifier()
	{
		return testCaseId;
	}

	@Override
	public String getMainEntityIdentifierName()
	{
		return "testCaseId";
	}
}