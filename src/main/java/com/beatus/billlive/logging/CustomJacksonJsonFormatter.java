package com.beatus.billlive.logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;
import ch.qos.logback.contrib.json.classic.JsonLayout;

/**
 * Custom formatter, wrapping the {@code JacksonJsonFormatter}, providing
 * capability for the log message to be JSON. If the {@code isJsonMessage}
 * property is set to {@code true}, the JSON message will be added to the log
 * line JSON as a JSON object, otherwise it will be added as a String.
 *
 * @author vakey
 */
public class CustomJacksonJsonFormatter extends JacksonJsonFormatter {

    private static final String MESSAGE_DESERIALIZATION_ERROR
            = "Could not deserialize JSON Message. "
            + "Are tou sure your message is JSON?";

    private boolean isJsonMessage;

    /**
     * Constructor. Creates the Jackson {@code ObjectMapper} and initializes
     * pretty printing as {@code false}.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CustomJacksonJsonFormatter() {
        setObjectMapper(new ObjectMapper());
        setPrettyPrint(false);
    }

    /**
     * Overrides the {@code toJsonString} method in the superclass to check if
     * the log message is JSON. If the log message is JSON, the message will be
     * converted to a {@code Map}, and added to the logging event map in place
     * of the original JSON string. This will allow the JSON message to be
     * serialized with the overall logging event as a single JSON object.
     *
     * @param loggingEventMap the logging event map
     * @return the logging event as a JSON String
     * @throws IOException
     */
    @Override
    @SuppressWarnings("rawtypes")
    public String toJsonString(Map loggingEventMap) throws IOException {
        if (isJsonMessage) {
            convertJSONMessage(loggingEventMap);
        }

        return super.toJsonString(loggingEventMap);
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    private void convertJSONMessage(Map loggingEventMap) {
        if (loggingEventMap.get(JsonLayout.MESSAGE_ATTR_NAME) == null) {
            return;
        }

        try {
            loggingEventMap.put(
                    JsonLayout.MESSAGE_ATTR_NAME,
                    getObjectMapper()
                            .readValue(
                                    (String) loggingEventMap.get(
                                            JsonLayout.MESSAGE_ATTR_NAME),
                                    HashMap.class));

        } catch (ClassCastException | IOException ex) {
            System.out.println(MESSAGE_DESERIALIZATION_ERROR);
        }
    }

    public void setIsJsonMessage(boolean isJsonMessage) {
        this.isJsonMessage = isJsonMessage;
    }
}
