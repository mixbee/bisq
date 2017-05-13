package io.bisq.core.alert;

import io.bisq.common.proto.network.NetworkEnvelope;
import io.bisq.generated.protobuffer.PB;
import io.bisq.network.p2p.MailboxMessage;
import io.bisq.network.p2p.NodeAddress;
import lombok.Value;

@Value
public class PrivateNotificationMessage implements MailboxMessage {
    private final NodeAddress senderNodeAddress;
    private final PrivateNotificationPayload privateNotificationPayload;
    private final String uid;

    public PrivateNotificationMessage(PrivateNotificationPayload privateNotificationPayload,
                                      NodeAddress senderNodeAddress,
                                      String uid) {
        this.senderNodeAddress = senderNodeAddress;
        this.privateNotificationPayload = privateNotificationPayload;
        this.uid = uid;
    }

    @Override
    public PB.NetworkEnvelope toProtoNetworkEnvelope() {
        return NetworkEnvelope.getDefaultBuilder()
                .setPrivateNotificationMessage(PB.PrivateNotificationMessage.newBuilder()
                        .setPrivateNotificationPayload(privateNotificationPayload.toProtoMessage())
                        .setSenderNodeAddress(senderNodeAddress.toProtoMessage())
                        .setUid(uid))
                .build();
    }

    public static PrivateNotificationMessage fromProto(PB.PrivateNotificationMessage proto) {
        return new PrivateNotificationMessage(PrivateNotificationPayload.fromProto(proto.getPrivateNotificationPayload()),
                NodeAddress.fromProto(proto.getSenderNodeAddress()),
                proto.getUid());
    }
}
