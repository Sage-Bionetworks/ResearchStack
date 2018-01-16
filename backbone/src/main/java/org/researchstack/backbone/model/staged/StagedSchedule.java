package org.researchstack.backbone.model.staged;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mauriciosouto on 7/9/17.
 *
 * Represent the schedule selected of an Staged Activity
 *
 * - startDate: start date for the staged schedule
 * - endDate: end date for the staged schedule. Can be null/nil.
 * - duration: the length of time a particular instance of content will remain available after it is created. Can be null/nil, meaning no expiration
 * - durationType: units for duration (days, weeks, months). Can be null/nil
 * - repeats: class of type RepetitionPattern. Can be null/nil if the activity doesn't repeat.
 *
 */

public class StagedSchedule implements Serializable {

    public static StagedSchedule schedule(Date startDate) {
        return schedule(startDate, null, 0, null, null);
    }

    public static StagedSchedule limitedSchedule(Date startDate, int duration, StagedDurationUnit durationUnit) {
        return schedule(startDate, null, duration, durationUnit, null);
    }

    public static StagedSchedule repeatingSchedule(Date startDate, Date endDate, RepetitionPattern pattern) {
        return schedule(startDate, endDate, 0, null, pattern);
    }

    public static StagedSchedule repeatingLimitedSchedule(Date startDate, Date endDate,
                                                          int duration, StagedDurationUnit durationUnit,
                                                          RepetitionPattern pattern) {
        return schedule(startDate, endDate, duration, durationUnit, pattern);
    }

    public static StagedSchedule schedule(Date startDate, Date endDate,
                                          int duration, StagedDurationUnit durationUnit,
                                          RepetitionPattern repeats) {
        StagedSchedule stagedSchedule = new StagedSchedule();
        stagedSchedule.startDate = startDate;
        stagedSchedule.endDate = endDate;
        stagedSchedule.duration = duration;
        stagedSchedule.durationUnit = durationUnit;
        stagedSchedule.repeats = repeats;
        return stagedSchedule;
    }

    private Date startDate;
    private Date endDate;
    private int duration;
    private StagedDurationUnit durationUnit;
    private RepetitionPattern repeats;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDuration() {
        return duration;
    }

    public StagedDurationUnit getDurationUnit() {
        return durationUnit;
    }

    public RepetitionPattern getRepeats() {
        return repeats;
    }
}