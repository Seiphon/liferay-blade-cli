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

package com.liferay.blade.cli.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
@Parameters(commandDescription = "Run server defined by your Liferay project", commandNames = "server run")
public class ServerRunArgs extends BaseArgs {

	public boolean isDebug() {
		return _debug;
	}

	@Parameter(description = "Run server in debug mode", names = {"-d", "--debug"})
	private boolean _debug;

}