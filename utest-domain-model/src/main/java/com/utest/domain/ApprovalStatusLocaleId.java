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
package com.utest.domain;

// Generated Oct 7, 2009 11:18:35 AM by Hibernate Tools 3.2.4.GA

/**
 * UserStatusLocaleId generated by hbm2java
 */
public class ApprovalStatusLocaleId implements java.io.Serializable
{

	private Integer	approvalStatusId;
	private String	localeCode;

	public ApprovalStatusLocaleId()
	{
	}

	public ApprovalStatusLocaleId(final Integer approvalStatusId, final String localeCode)
	{
		this.approvalStatusId = approvalStatusId;
		this.localeCode = localeCode;
	}

	public Integer getApprovalStatusId()
	{
		return approvalStatusId;
	}

	public void setApprovalStatusId(final Integer approvalStatusId)
	{
		this.approvalStatusId = approvalStatusId;
	}

	public String getLocaleCode()
	{
		return localeCode;
	}

	public void setLocaleCode(final String localeCode)
	{
		this.localeCode = localeCode;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStatusId == null) ? 0 : approvalStatusId.hashCode());
		result = prime * result + ((localeCode == null) ? 0 : localeCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ApprovalStatusLocaleId other = (ApprovalStatusLocaleId) obj;
		if (approvalStatusId == null)
		{
			if (other.approvalStatusId != null)
			{
				return false;
			}
		}
		else if (!approvalStatusId.equals(other.approvalStatusId))
		{
			return false;
		}
		if (localeCode == null)
		{
			if (other.localeCode != null)
			{
				return false;
			}
		}
		else if (!localeCode.equals(other.localeCode))
		{
			return false;
		}
		return true;
	}
}
