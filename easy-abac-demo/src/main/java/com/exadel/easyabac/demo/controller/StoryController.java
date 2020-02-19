package com.exadel.easyabac.demo.controller;

import static com.exadel.easyabac.demo.security.model.project.ProjectAction.VIEW;

import com.exadel.easyabac.demo.security.model.project.ProjectAccess;
import com.exadel.easyabac.demo.security.model.project.ProjectId;
import com.exadel.easyabac.demo.security.model.story.StoryAccess;
import com.exadel.easyabac.demo.security.model.story.StoryAction;
import com.exadel.easyabac.demo.security.model.story.StoryId;
import com.exadel.easyabac.model.annotation.ProtectedResource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample controller for Story entity.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@ProtectedResource
@ProjectAccess(VIEW)
@RequestMapping("projects/{projectId}/stories/{storyId}")
@RestController
public class StoryController {

    @StoryAccess(StoryAction.VIEW)
    @RequestMapping
    public ResponseEntity get(
            @ProjectId @PathVariable("projectId") Long projectId,
            @StoryId @PathVariable("storyId") Long storyId) {
        return ResponseEntity.ok().build();
    }

    @StoryAccess(StoryAction.UPDATE)
    @RequestMapping("/update")
    public ResponseEntity update(
            @ProjectId @PathVariable("projectId") Long projectId,
            @StoryId @PathVariable("storyId") Long storyId) {
        return ResponseEntity.ok().build();
    }
}
