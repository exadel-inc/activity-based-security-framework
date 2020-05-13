/*
 * Copyright 2019-2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exadel.easyabac.demo.controller;

import com.exadel.easyabac.demo.security.model.project.ProjectAccess;
import com.exadel.easyabac.demo.security.model.project.ProjectId;
import com.exadel.easyabac.model.annotation.ProtectedResource;
import com.exadel.easyabac.model.annotation.PublicResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.exadel.easyabac.demo.security.model.project.ProjectAction.*;

/**
 * Sample controller for Project entity.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@ProtectedResource
@ProjectAccess(VIEW)
@RequestMapping("/projects/{projectId}")
@RestController
public class ProjectController {

    @ProjectAccess(VIEW)
    @RequestMapping
    public ResponseEntity get(@ProjectId @PathVariable("projectId") Long projectId) {
        return getResponse(projectId);
    }

    @ProjectAccess(UPDATE)
    @RequestMapping("/update")
    public ResponseEntity update(@ProjectId @PathVariable("projectId") Long projectId) {
        return getResponse(projectId);
    }

    @ProjectAccess(DELETE)
    @RequestMapping("/delete")
    public ResponseEntity delete(@ProjectId @PathVariable("projectId") Long projectId) {
        return getResponse(projectId);
    }

    @PublicResource
    @RequestMapping("/public-info")
    public ResponseEntity getPublicInfo() {
        return ResponseEntity.ok().build();
    }

    private ResponseEntity getResponse(Long projectId) {
        return ResponseEntity.ok(String.format("Project[id=%s]", projectId));
    }
}
