package com.bizwink.boot;


/**
 * Common interface for OpenCms logging.
 * Constants used for logging purposes are defined here.
 *
 * @author Alexander Lucas
 * @author Andreas Schouten
 * @version $Revision: 1.1.1.1 $ $Date: 2006/07/18 08:31:17 $
 */
public interface I_CmsLogChannels {

    /**
     * This static final constant is a "preprocessor" constant. If it is set to
     * true - all logging-code is enabled and will be compiled into the classes.
     * If it is set to false the logging-code will not be compiled into the
     * classes. All resulting classes will shrink in size and OpenCms will
     * perform much better. For live-systems you could set this to false,
     * in case you really have performance issues.
     *
     * If logging is disabled here, you can't switch it on again via the
     * opencms.properties!
     *
     * This value should generally be set to true!
     */
    /** Debugging messages */
    public static final String C_OPENCMS_INIT = "opencms_init";
}
