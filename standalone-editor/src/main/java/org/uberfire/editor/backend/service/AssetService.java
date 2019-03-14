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

package org.uberfire.editor.backend.service;

import java.util.List;

import org.uberfire.editor.client.shared.model.AssetContent;

/**
 *
 */
public interface AssetService {

    public AssetContent getAssetContent(String assetURI);

    /**
     * 
     * List assets for a given repository in default space
     * 
     * @param repository The repository name
     * @return
     */
    public List<String> listAssets(String repository);

    /**
     * 
     * List assets in default space and repository
     * 
     * @return
     */
    List<String> listAssets();

    /**
     * @param path
     * @param content
     * @return
     */
    public AssetContent saveAsset(String path, String content);

}
