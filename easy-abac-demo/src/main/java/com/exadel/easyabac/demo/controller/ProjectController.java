package com.exadel.easyabac.demo.controller;

import static com.exadel.easyabac.demo.security.model.project.ProjectAction.DELETE;
import static com.exadel.easyabac.demo.security.model.project.ProjectAction.UPDATE;
import static com.exadel.easyabac.demo.security.model.project.ProjectAction.VIEW;

import com.exadel.easyabac.demo.security.model.project.ProjectAccess;
import com.exadel.easyabac.demo.security.model.project.ProjectId;
import com.exadel.easyabac.model.annotation.ProtectedResource;
import com.exadel.easyabac.model.annotation.PublicResource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok().build();
    }

    @ProjectAccess(UPDATE)
    @RequestMapping("/update")
    public ResponseEntity update(@ProjectId @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok().build();
    }

    @ProjectAccess(DELETE)
    @RequestMapping("/delete")
    public ResponseEntity delete(@ProjectId @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok().build();
    }

    @PublicResource
    @RequestMapping("/public-info")
    public ResponseEntity getPublicInfo() {
        return ResponseEntity.ok().build();
    }
}
