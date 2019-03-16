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

package org.uberfire.editor.server.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.uberfire.editor.server.service.AssetService;
import org.uberfire.editor.shared.conf.EditorDefinitions;
import org.uberfire.editor.shared.model.AssetContent;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.IOException;
import org.uberfire.java.nio.file.FileSystem;
import org.uberfire.java.nio.file.FileSystemAlreadyExistsException;
import org.uberfire.java.nio.file.FileVisitResult;
import org.uberfire.java.nio.file.Files;
import org.uberfire.java.nio.file.Path;
import org.uberfire.java.nio.file.SimpleFileVisitor;
import org.uberfire.java.nio.file.attribute.BasicFileAttributes;
import org.uberfire.spaces.SpacesAPI;

/**
 *
 */
@ApplicationScoped
public class AssetServiceImpl implements AssetService {
    
    @Inject
    @Named("ioStrategy")
    IOService ioService;
    @Inject SpacesAPI spacesAPI;

    @Override
    public AssetContent getAssetContent(String path) {
        Path assetPath = getAssetPath(path);
        String content = ioService.readAllString(assetPath);
        return buildAssetContent(path, content);
    }

    @Override
    public AssetContent saveAsset(String path, String content) {
        Path assetPath = getAssetPath(path);
        ioService.write(assetPath, content);
        return buildAssetContent(path, content);
    }
    
    
    @Override
    public List<String> listAssets() {
        return listAssets(null);
    }
    
    @Override
    public List<String> listAssets(String string) {
        FileSystem fileSystem = getFileSystem(string);
        Iterable<Path> roots = fileSystem.getRootDirectories();
        Path root = roots.iterator().next();
        List<String> files = new ArrayList<>();
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(attrs.isRegularFile()) {
                    files.add(file.toString());
                }
                return super.visitFile(file, attrs);
            }
        });
        return files;
    }  
    
    private AssetContent buildAssetContent(String assetURI, String content) {
        String extension = "";
        int dotIndex = assetURI.lastIndexOf('.');
        
        if (dotIndex != -1) {
            extension = assetURI.substring(dotIndex + 1);
        }
        return new AssetContent(extension, content, assetURI);
    }
    
    private Path getAssetPath(String location) {
        return getLocationPath(null, location);
    }
    
    private Path getLocationPath(String repository, String location) {
        FileSystem fileSystem = getFileSystem(repository);
        Path root = fileSystem.getRootDirectories().iterator().next();
        return root.resolve(location);
    }
    
    private FileSystem getFileSystem(String repository) {
        if (repository == null) {
            repository = EditorDefinitions.DEFAULT_REPOSITORY;
        }
        URI fileSystemURI = spacesAPI.resolveFileSystemURI(SpacesAPI.Scheme.DEFAULT,
                                                           EditorDefinitions.DEFAULT_SPACE,
                                                           repository);
        try {
            return ioService.newFileSystem(fileSystemURI, getEnv());
        } catch (FileSystemAlreadyExistsException e) {
            return ioService.getFileSystem(fileSystemURI);
        }
    }


    private HashMap<String, Object> getEnv() {
        return new HashMap<String, Object>() {{
             put("init",
                 Boolean.TRUE);
             put("internal",
                 Boolean.TRUE);
         }};
    }

}