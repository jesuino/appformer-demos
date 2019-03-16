/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.uberfire.editor.shared.conf;

import org.uberfire.spaces.Space;
import org.uberfire.spaces.SpacesAPI;

/**
 *
 */
public class EditorDefinitions {
    
    // TODO: Change
    private final static String SPACE_PROP = "org.uberfire.demo.defaultspace";
    
    public final static String DEFAULT_SPACE_NAME = System.getProperty(SPACE_PROP, SpacesAPI.DEFAULT_SPACE_NAME);
    
    public static final Space DEFAULT_SPACE = new Space(DEFAULT_SPACE_NAME);

    public static final String DEFAULT_REPOSITORY = "editor";

}
