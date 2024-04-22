/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.mronconi.kafka.connect.mirror;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

public class DefaultReplicationPolicy extends org.apache.kafka.connect.mirror.DefaultReplicationPolicy {

    private static final String COMMA_SEPARATOR = ",";

    private String topicsExclude = null;

    @Override
    public void configure(Map<String, ?> props) {
        super.configure(props);
        if (props.containsKey("topics.exclude")) {
            this.topicsExclude = (String) props.get("topics.exclude");
        }
    }

    @Override
    public boolean isHeartbeatsTopic(String topic) {
        return super.isHeartbeatsTopic(topic) && !isHeartbeatsInTopicsExclude(topic);
    }

    /**
     * Check if the topic heartbeats argument is in the <code>topics.exclude</code>.
     *
     * @see <a href="https://kafka.apache.org/documentation/#mirror_source_topics.exclude">topics.exclude</a>
     *
     * @param topic
     * @return
     */
    private boolean isHeartbeatsInTopicsExclude(String topic) {
        return topicsExclude != null && Arrays.stream(topicsExclude.split(COMMA_SEPARATOR))
                .map(String::trim)
                .map(Pattern::compile)
                .anyMatch(pattern -> pattern.matcher(topic).matches());
    }
}
