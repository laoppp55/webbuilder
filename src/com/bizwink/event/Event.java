/**
 * Event.java
 */

package com.bizwink.event;

import java.sql.*;

public interface Event extends Activity {
    /**
     * Returns the date of the event.
     */
    String getDate();

    /**
     * Sets the date of the event.
     */
    void setDate(String date);

    /**
     * Returns the time of the event.
     */
    String getTime();

    /**
     * Sets the time of the event.
     */
    void setTime(String time);

    /**
     * Returns the location of the event.
     */
    String getLocation();

    /**
     * Sets the location of the event.
     */
    void setLocation(String location);

    /**
     * Returns the durationHour of the event.
     */
    int getDurationHour();

    /**
     * Sets the creator of the event.
     */
    void setDurationHour(int durationHour);

    /**
     * Returns the durationMinute of the event.
     */
    int getDurationMinute();

    /**
     * Sets the creator of the event.
     */
    void setDurationMinute(int durationMinute);

    /**
     * Returns the isAllDay of the event.
     */
    int getIsAllDay();

    /**
     * Sets the isAllDay of the event.
     */
    void setIsAllDay(int isAllDay);

    /**
     * Returns the comments of the event.
     */
    String getComments();

    /**
     * Sets the comments of the event.
     */
    void setComments(String comments);

    /**
     * Returns the creatorID of the event.
     */
    int getCreatorID();

    /**
     * Sets the creatorID of the event.
     */
    void setCreatorID(int creatorID);

    /**
     * Returns the creator of the event.
     */
    String getCreator();

    /**
     * Sets the creator of the event.
     */
    void setCreator(String creator);

    /**
     * Returns the modifierID of the event.
     */
    int getModifierID();

    /**
     * Sets the modifierID of the event.
     */
    void setModifierID(int modifierID);

    /**
     * Returns the modifier of the event.
     */
    String getModifier();

    /**
     * Sets the modifier of the event.
     */
    void setModifier(String modifier);

    /**
     * Returns the Date that the event was created.
     */
    Timestamp getCreationDate();

    /**
     * Sets the creation date of the event.
     */
    void setCreationDate(Timestamp creationDate);
}