package com.bizwink.boot;

import com.bizwink.boot.io.Logger;
import com.bizwink.boot.io.LogWriter;
import com.bizwink.boot.util.Configurations;
import java.io.*;

/**
 * This class enables the OpenCms logging feature.
 * Different logging channels are supported and can be
 * (de)activated by the log settings in the proerty file.
 *
 * @author Alexander Lucas
 * @version $Revision: 1.1.1.1 $ $Date: 2006/07/18 08:31:16 $
 */
public class CmsLog implements Logger {

    /** The internal m_Logger */
    private LogWriter m_Logger = null;

    /** Shows if this log is m_Active or not */
    private boolean m_Active = false;

    /** The main constructor */
    public CmsLog(String identifier, Configurations confs) {
        m_Logger = null;
        try {
            m_Logger = new LogWriter(identifier, confs);
            m_Active = m_Logger.active;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes any pending messages into the log media.
     */
    public void flush() {
        if(m_Logger != null) {
            m_Logger.flush();
        }
    }

    /**
     * Check if this log is active.
     * @return <code>true</code> the log is active, <code>false</code> otherwise.
     */
    public boolean isActive() {
        return (m_Logger != null) ? m_Logger.active : false;
    }

    /**
     * Tells if the given channel is active.
     * @param channel the channel to test.
     * @return <code>true</code> the given channel is active, <code>false</code> otherwise.
     */
    public boolean isActive(String channel) {
        return (m_Logger != null) ? m_Logger.isActive(channel) : false;
    }

    /**
     * Prints the log message on the right channel.
     * <p>
     * A "channel" is a virtual log that may be enabled or disabled by
     * setting the property "identifier".channel.???=true where ??? is the
     * channel identifier that must be passed with the message.
     * If a channel is not recognized or its property is set to false
     * the message is not written.
     *
     * @param channel the channel to put the message on.
     * @param message the message to log.
     */
    public void log(String channel, String message) {
        if(m_Logger != null) {
            m_Logger.log(channel, message);
        }
    }

    /**
     * Prints the error message and stack trace if channel enabled.
     * @param t the error thrown.
     */
    public void log(String channel, Throwable t) {
        if(m_Logger != null) {
            m_Logger.log(channel, t);
        }
    }
}
