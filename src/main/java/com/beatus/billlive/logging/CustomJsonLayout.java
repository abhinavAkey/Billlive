package com.beatus.billlive.logging;


import java.util.Map;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import ch.qos.logback.core.CoreConstants;

/**
 * Custom Logback JSON Layout. Wraps Logback's {@code JsonLayout} providing
 * double-spaced log lines.
 *
 * @author vakey
 */
public class CustomJsonLayout extends JsonLayout {

    private static final String HOSTNAME_ATT_NAME = "hostname";
    private static final String HOSTNAME_PROPERTY_NAME = "HOSTNAME";

    private boolean doubleSpace;
    private boolean includeHostname;

    /**
     * Constructor.
     */
    public CustomJsonLayout() {
        super();
        this.doubleSpace = true;
        this.includeHostname = true;
    }

    /**
     * Converts the {@code ILoggingEvent} to a JSON String. Uses the super class
     * to convert the event into JSON and adds a "new line" character if double
     * spacing is turned on.
     *
     * @param event the ILoggingEvent to convert to JSON
     * @return JSON
     */
    @Override
    public String doLayout(ILoggingEvent event) {
        String result = super.doLayout(event); 
        return isDoubleSpace()
                ? result + CoreConstants.LINE_SEPARATOR : result;
    }

    /**
     * Converts the ILoggingEvent to a {@code Map}. Used the super class to
     * convert the event into a Map and then adds the entries managed by this
     * class.
     *
     * @param event
     * @return
     */
    @Override
    protected Map<String, Object> toJsonMap(ILoggingEvent event) {

        @SuppressWarnings("unchecked")
        Map<String, Object> jsonMap 
                = (Map<String, Object>) super.toJsonMap(event);

        if (this.includeHostname) {
            String hostName = event.getLoggerContextVO()
                    .getPropertyMap().get(HOSTNAME_PROPERTY_NAME);

            if (hostName != null) {
                jsonMap.put(HOSTNAME_ATT_NAME, hostName);
            }
        }

        truncMessage(jsonMap);

        return jsonMap;
    }

    protected void truncMessage(
            Map<String, Object> jsonMap) {

        String message 
                = (String) jsonMap.get(
                        FORMATTED_MESSAGE_ATTR_NAME);

        if (message.length() > 125000) {
            jsonMap.put(
                    FORMATTED_MESSAGE_ATTR_NAME, 
                    message.substring(
                            0, 
                            125000));
        } 
    }

    public boolean isDoubleSpace() {
        return doubleSpace;
    }

    public void setDoubleSpace(boolean doubleSpace) {
        if (doubleSpace == true) {
            super.setAppendLineSeparator(true);
        }

        this.doubleSpace = doubleSpace;
    }

    public boolean isIncludeHostname() {
        return includeHostname;
    }

    public void setIncludeHostname(boolean includeHostname) {
        this.includeHostname = includeHostname;
    }
}