/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.watcher;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.xpack.XPackFeatureSet;
import org.elasticsearch.xpack.XPackField;

import java.io.IOException;
import java.util.Map;

public class WatcherFeatureSetUsage extends XPackFeatureSet.Usage {

    private final Map<String, Object> stats;

    public WatcherFeatureSetUsage(StreamInput in) throws IOException {
        super(in);
        stats = in.readMap();
    }

    public WatcherFeatureSetUsage(boolean available, boolean enabled, Map<String, Object> stats) {
        super(XPackField.WATCHER, available, enabled);
        this.stats = stats;
    }

    public Map<String, Object> stats() {
        return stats;
    }

    @Override
    protected void innerXContent(XContentBuilder builder, Params params) throws IOException {
        super.innerXContent(builder, params);
        if (enabled) {
            for (Map.Entry<String, Object> entry : stats.entrySet()) {
                builder.field(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeMap(stats);
    }
}
