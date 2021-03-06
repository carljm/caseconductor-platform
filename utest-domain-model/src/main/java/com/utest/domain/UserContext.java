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
 * UserContext generated by hbm2java
 */
public class UserContext extends Entity
{

	private Integer	version;
	private boolean	showInfo;
	private boolean	showDemoData;
	private boolean	showWelcomeScreen;
	private String	localeCode;
	private boolean	navigateToNextBug;

	public UserContext()
	{
	}

	public UserContext(final boolean showInfo, final boolean showDemoData, final String localeCode)
	{
		this.showInfo = showInfo;
		this.showDemoData = showDemoData;
		this.localeCode = localeCode;
	}

	public Integer getVersion()
	{
		return this.version;
	}

	public void setVersion(final Integer version)
	{
		this.version = version;
	}

	public boolean isShowInfo()
	{
		return this.showInfo;
	}

	public void setShowInfo(final boolean showInfo)
	{
		this.showInfo = showInfo;
	}

	public boolean isShowDemoData()
	{
		return this.showDemoData;
	}

	public void setShowDemoData(final boolean showDemoData)
	{
		this.showDemoData = showDemoData;
	}

	public void setShowWelcomeScreen(final boolean showWelcomeScreen)
	{
		this.showWelcomeScreen = showWelcomeScreen;
	}

	public boolean isShowWelcomeScreen()
	{
		return showWelcomeScreen;
	}

	public String getLocaleCode()
	{
		return this.localeCode;
	}

	public void setLocaleCode(final String localeCode)
	{
		this.localeCode = localeCode;
	}

	public boolean isNavigateToNextBug()
	{
		return navigateToNextBug;
	}

	public void setNavigateToNextBug(final boolean navigateToNextBug)
	{
		this.navigateToNextBug = navigateToNextBug;
	}

}
