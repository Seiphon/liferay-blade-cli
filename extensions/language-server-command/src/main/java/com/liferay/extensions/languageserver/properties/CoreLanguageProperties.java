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

package com.liferay.extensions.languageserver.properties;

import java.io.File;

/**
 * @author Terry Jia
 * @author Ashley Yuan
 */
public class CoreLanguageProperties extends PropertiesFile {

	public CoreLanguageProperties(File file) {
		super(file, "/core-language-properties.json");
	}

	public boolean checkPossibleKeys() {
		return false;
	}

	@Override
	public boolean match() {
		String fileName = getFile().getName();

		if (fileName.startsWith("Language") && fileName.endsWith(".properties")) {
			return true;
		}

		return false;
	}

}