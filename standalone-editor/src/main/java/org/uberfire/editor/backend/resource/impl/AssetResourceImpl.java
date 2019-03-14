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

package org.uberfire.editor.backend.resource.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.uberfire.editor.backend.service.AssetService;
import org.uberfire.editor.client.shared.AssetResource;
import org.uberfire.editor.client.shared.model.AssetContent;

/**
 * Implementation that uses uberfire IO API
 */
@RequestScoped
public class AssetResourceImpl implements AssetResource {

    
    @Inject AssetService service;
    
    @Override
    public AssetContent getAssetInfo(String path) {
        System.out.println("GETTING RESOURCE " + path);
        return service.getAssetContent(path);
    }

    @Override
    public List<String> listAssets() {
        return service.listAssets();
    }

    @Override
    public AssetContent setAssetInfo(String path, String content) {
        System.out.println("SAVING RESOURCE " + path + " WITH CONTENT:");
        System.out.println(content);
        return service.saveAsset(path, content);
    }
}
