/**
 * Task.java
 */

package com.bizwink.event;

import java.sql.*;

public interface Task extends Activity {
    /**
     * Returns the closed of the task.
     */
    int getClosed();

    /**
     * Sets the closed of the task.
     */
    void setClosed(int closed);

    /**
     * Returns the comments of the task.
     */
    String getComments();

    /**
     * Sets the comments of the task.
     */
    void setComments(String comments);

    /**
     * Returns the creatorID of the task.
     */
    int getCreatorID();

    /**
     * Sets the creator of the task.
     */
    void setCreatorID(int creatorID);

    /**
     * Returns the creator of the task.
     */
    String getCreator();

    /**
     * Sets the creator of the task.
     */
    void setCreator(String creator);


    /**
     * Returns the modifierID of the task.
     */
    int getModifierID();

    /**
     * Sets the modifierID of the task.
     */
    void setModifierID(int modifierID);

    /**
     * Returns the modifier of the task.
     */
    String getModifier();

    /**
     * Sets the modifier of the task.
     */
    void setModifier(String modifier);

    /**
     * Returns the Date that the task was created.
     */
    Timestamp getCreationDate();

    /**
     * Sets the creation date of the task.
     */
    void setCreationDate(Timestamp creationDate);
}