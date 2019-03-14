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

package org.uberfire.editor.client.shared;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.uberfire.editor.client.shared.model.AssetContent;

/**
 *  Provide access to assets anywhere in a filesystem
 */
@Path("/asset")
@Produces("application/json;charset=utf-8")
public interface AssetResource {
    
    @GET
    @Path("{assetPath}")
    public AssetContent getAssetInfo(@PathParam("assetPath") String path);
    
    @POST
    @Path("{assetPath}")
    @Consumes("text/plain")
    public AssetContent setAssetInfo(@PathParam("assetPath") String path, String content);
    
    @GET
    public List<String> listAssets();

}
