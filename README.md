# Mirror Maker 2 Replication Policy
The repository aims to provide a custom replication policy implementation for Mirror Maker 2 that overcomes the current default implementation limitation on heartbeats topic filtering to replicate.

The default implementation replicates all heartbeat topics whose names match with "heartbeats", which is hard coded. 

There is a [KIP](https://cwiki.apache.org/confluence/display/KAFKA/KIP-1016+Make+MM2+heartbeats+topic+name+configurable) still under discussion, which aims to make the topic name of heartbeats configurable.

## Usage

Add following property to connect-mirror-maker configurations file
```
replication.policy.class=com.redhat.mronconi.kafka.connect.mirror.[IdentityReplicationPolicy|DefaultReplicationPolicy]
```

That implementation use the patterns defined by `topics.exclude` property to filter the heartbeat topics to be replicate.


