package com.exadel.easyabac.demo.controller;

import static com.exadel.easyabac.demo.security.model.project.ProjectAction.VIEW;

import com.exadel.easyabac.demo.security.model.project.ProjectAccess;
import com.exadel.easyabac.demo.security.model.project.ProjectId;
import com.exadel.easyabac.demo.security.model.story.StoryAccess;
import com.exadel.easyabac.demo.security.model.story.StoryAction;
import com.exadel.easyabac.demo.security.model.story.StoryId;
import com.exadel.easyabac.demo.security.model.task.TaskAccess;
import com.exadel.easyabac.demo.security.model.task.TaskAction;
import com.exadel.easyabac.demo.security.model.task.TaskId;
import com.exadel.easyabac.model.annotation.ProtectedResource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample controller for Task entity.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@ProtectedResource
@ProjectAccess(VIEW)
@StoryAccess(StoryAction.VIEW)
@RequestMapping("projects/{projectId}/stories/{storyId}/tasks/{taskId}")
@RestController
public class TaskController {

    @TaskAccess(TaskAction.VIEW)
    @RequestMapping
    public ResponseEntity get(
            @ProjectId @PathVariable("projectId") Long projectId,
            @StoryId @PathVariable("storyId") Long storyId,
            @TaskId @PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok().build();
    }

    @TaskAccess(TaskAction.UPDATE)
    @RequestMapping("/update")
    public ResponseEntity update(
            @ProjectId @PathVariable("projectId") Long projectId,
            @StoryId @PathVariable("storyId") Long storyId,
            @TaskId @PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok().build();
    }
}
