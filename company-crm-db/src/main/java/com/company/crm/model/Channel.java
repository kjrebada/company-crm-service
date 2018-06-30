package com.company.crm.model;

import java.util.Arrays;

/**
 * This is the type of available channels that we support.
 *
 */
public enum Channel {
    web("web"),
    app("app");

    private String value;

    Channel(String value) {
        this.value = value;
    }

    public static Channel fromValue(String value) {
        for (Channel channel : values()) {
            if (channel.value.equalsIgnoreCase(value)) {
                return channel;
            }
        }
        throw new IllegalArgumentException(
                "Unknown channel type " + value + ", " +
                        "Allowed values are " + Arrays.toString(values()));
    }
}
