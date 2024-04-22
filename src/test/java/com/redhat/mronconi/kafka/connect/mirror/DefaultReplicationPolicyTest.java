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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultReplicationPolicyTest {
    private static final DefaultReplicationPolicy DEFAULT_REPLICATION_POLICY = new DefaultReplicationPolicy();

    private static String TOPICS_EXCLUDE = ".*[\\-\\.]internal, .*\\.replica, __.*, .*\\.heartbeats";

    @BeforeEach
    public void setUp() {
        Map<String, Object> config =  new HashMap<>();
        config.put("topics.exclude", TOPICS_EXCLUDE);
//        config.put("replication.policy.separator", "_");

        DEFAULT_REPLICATION_POLICY.configure(config);
    }

    @Test
    public void heartbeatsTopic_shouldBeReplicated() {
        assertTrue(DEFAULT_REPLICATION_POLICY.isHeartbeatsTopic("heartbeats"));
    }

    @Test
    public void heartbeatsTopic_shouldNotBeReplicated() {
        assertFalse(DEFAULT_REPLICATION_POLICY.isHeartbeatsTopic("acme.heartbeats"));
        assertFalse(DEFAULT_REPLICATION_POLICY.isHeartbeatsTopic("acme.foo.heartbeats"));
        assertFalse(DEFAULT_REPLICATION_POLICY.isHeartbeatsTopic("acme.foo.bar.heartbeats"));
    }
}
