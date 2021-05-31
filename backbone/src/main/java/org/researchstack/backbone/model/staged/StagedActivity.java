package org.researchstack.backbone.model.staged;

import org.researchstack.backbone.task.Task;

import java.io.Serializable;

/**
 * Created by mauriciosouto on 7/9/17.
 *
 * Staged is content that is scheduled, but not only for a specific day, it  arrives and hangs around until it is handled.
 * Each StagedActivity is tied to time intervals that may span days, weeks, months, or be open ended.
 *
 * - id: a unique ID for the activity
 * - type: one of survey, activeTask, or insight
 * - schedule: an StagedSchedule for this activity
 * - activityInfo: dictionary with type specific information - ["title" : TITLE_STRING, "text": TEXT_STRING, "instructions": INSTRUCTION_STRING, "task": TASK]
 *
 */

public class StagedActivity implements Serializable {

    private String id;
    private StagedActivityType type;
    private StagedSchedule schedule;
    private StagedActivityState status;

    private String title;
    private String text;
    private String instructions;
    private int tintColor;

    private Task task;
    private boolean resultResettable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StagedActivityType getType() {
        return type;
    }

    public void setType(StagedActivityType type) {
        this.type = type;
    }

    public StagedSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(StagedSchedule schedule) {
        this.schedule = schedule;
    }

    public StagedActivityState getStatus() {
        return status;
    }

    public void setStatus(StagedActivityState status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTintColor() {
        return tintColor;
    }

    public void setTintColor(int tintColor) {
        this.tintColor = tintColor;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isResultResettable() {
        return resultResettable;
    }

    public void setResultResettable(boolean resultResettable) {
        this.resultResettable = resultResettable;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}