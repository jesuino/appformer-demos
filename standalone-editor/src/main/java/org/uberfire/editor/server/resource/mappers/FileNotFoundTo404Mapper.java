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

package org.uberfire.editor.server.resource.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.uberfire.java.nio.file.NoSuchFileException;

/**
 *  Maps uberfire NoSuchFileException exception to 404 instead exploding a 500 on client's side.
 */
@Provider
public class FileNotFoundTo404Mapper implements ExceptionMapper<NoSuchFileException>{

    @Override
    public Response toResponse(NoSuchFileException exception) {
        return Response.status(Status.NOT_FOUND)
                       .entity("Not able to find file " + exception.getFile())
                       .build();
    }

}
