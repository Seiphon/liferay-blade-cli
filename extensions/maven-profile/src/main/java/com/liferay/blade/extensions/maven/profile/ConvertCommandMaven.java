/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
 */


package com.liferay.blade.extensions.maven.profile;

import java.io.File;
import java.util.Properties;

import com.liferay.blade.cli.BladeCLI;
import com.liferay.blade.cli.WorkspaceConstants;
import com.liferay.blade.cli.command.BladeProfile;
import com.liferay.blade.cli.command.ConvertArgs;
import com.liferay.blade.cli.command.ConvertCommand;
import com.liferay.blade.cli.gradle.GradleWorkspaceProvider;
import com.liferay.blade.extensions.maven.profile.internal.MavenUtil;

import aQute.bnd.version.Version;

/**
 * @author Seiphon Wang
 */
@BladeProfile("maven")
public class ConvertCommandMaven extends ConvertCommand {

	public ConvertCommandMaven() {
	}

	@Override
	public void execute() throws Exception {
		BladeCLI bladeCLI = getBladeCLI();

		ConvertArgs convertArgs = getArgs();

		File baseDir = convertArgs.getBase();

		GradleWorkspaceProvider workspaceProviderGradle = (GradleWorkspaceProvider)bladeCLI.getWorkspaceProvider(
			baseDir);

		File projectDir = workspaceProviderGradle.getWorkspaceDir(bladeCLI);

		Properties gradleProperties = workspaceProviderGradle.getGradleProperties(projectDir);

		final File oldMavenProjectDir = getPluginsSdkDir(convertArgs, projectDir, gradleProperties);

		assertTrue("oldMavenProjectDir is null: %s", oldMavenProjectDir != null);
		assertTrue(String.format("oldMavenProjectDir does not exist: %s", oldMavenProjectDir), oldMavenProjectDir.exists());
		assertTrue(
			String.format("oldMavenProjectDir is not a valid Plugins SDK dir: %s", oldMavenProjectDir),
			_isLegacyMavenDir(oldMavenProjectDir));

		String projectsDirPath;

		String legacyDefaultWarsDir = (String)gradleProperties.get(WorkspaceConstants.DEFAULT_WARS_DIR_PROPERTY);

		boolean isLegacyDefaultWarsDirSet = false;

		if ((legacyDefaultWarsDir != null) && !legacyDefaultWarsDir.isEmpty()) {
			isLegacyDefaultWarsDirSet = true;
		}

		if ((gradleProperties != null) && isLegacyDefaultWarsDirSet) {
			projectsDirPath = gradleProperties.getProperty(WorkspaceConstants.DEFAULT_WARS_DIR_PROPERTY);
		}
		else {
			projectsDirPath = "modules";
		}

		File projectsDir = new File(projectDir, projectsDirPath);

		projectsDir.mkdir();

		

		

		File[] childProjects = oldMavenProjectDir.listFiles();

		for (File project : childProjects) {
			File pomFile = new File(project + "/pom.xml");

			if (pomFile.exists()) {
				Properties properties = MavenUtil.getMavenConfiguration(pomFile);

				String pluginType = properties.getProperty("pluginType");

				switch (pluginType) {
				case "hook" :
					System.out.println("**********  hook");
					break;
				case "layouttpl" :
					System.out.println("**********  layouttpl");
					break;
				case "portlet" :
					System.out.println("**********  portlet");
					break;
				case "theme" :
					System.out.println("**********  theme");
					break;
				case "web" :
					System.out.println("**********  web");
					break;
				}
			}
		}
	}

	private boolean _isLegacyMavenDir(File pluginsSDKDir) {
		File pomXmlFile = new File(pluginsSDKDir, "pom.xml");

		if (pomXmlFile.exists()) {
			Properties properties = MavenUtil.getMavenProperties(pluginsSDKDir);

			String pluginVersion = properties.getProperty("liferay.maven.plugin.version");

			Version version = new Version(pluginVersion);

			if (version.compareTo(new Version("7.0.0")) < 0) {
				return true;
			}
		}

		return false;
	}

}
