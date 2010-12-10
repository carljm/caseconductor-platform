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

/**
 * TestRunResultStatusLocale generated by hbm2java
 */
public class TestRunResultStatusLocale implements LocaleDescriptable
{

	private TestRunResultStatusLocaleId	id;
	private String						description;
	private Integer						sortOrder;

	public TestRunResultStatusLocale()
	{
	}

	public TestRunResultStatusLocale(TestRunResultStatusLocaleId id, String description, Integer sortOrder)
	{
		this.id = id;
		this.description = description;
		this.sortOrder = sortOrder;
	}

	public TestRunResultStatusLocaleId getId()
	{
		return this.id;
	}

	public void setId(TestRunResultStatusLocaleId id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getSortOrder()
	{
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	@Override
	public Integer getEntityId()
	{
		return getId().getTestRunResultStatusId();
	}
}
