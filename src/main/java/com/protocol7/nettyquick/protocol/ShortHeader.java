package com.protocol7.nettyquick.protocol;

import static com.google.common.base.Preconditions.checkNotNull;

import com.protocol7.nettyquick.protocol.frames.Frame;
import com.protocol7.nettyquick.tls.aead.AEAD;
import com.protocol7.nettyquick.utils.Opt;
import io.netty.buffer.ByteBuf;
import java.util.Optional;

public class ShortHeader implements Header {

  public static ShortHeader addFrame(ShortHeader packet, Frame frame) {
    return new ShortHeader(
        packet.keyPhase, packet.connectionId, packet.packetNumber, packet.payload.addFrame(frame));
  }

  private final boolean keyPhase;
  private final Optional<ConnectionId> connectionId;
  private final PacketNumber packetNumber;
  private final Payload payload;

  public ShortHeader(
      final boolean keyPhase,
      final Optional<ConnectionId> connectionId,
      final PacketNumber packetNumber,
      final Payload payload) {
    this.keyPhase = checkNotNull(keyPhase);
    this.connectionId = checkNotNull(connectionId);
    this.packetNumber = checkNotNull(packetNumber);
    this.payload = checkNotNull(payload);
  }

  public boolean isKeyPhase() {
    return keyPhase;
  }

  @Override
  public Optional<ConnectionId> getDestinationConnectionId() {
    return connectionId;
  }

  public PacketNumber getPacketNumber() {
    return packetNumber;
  }

  public Payload getPayload() {
    return payload;
  }

  public void write(ByteBuf bb, AEAD aead) {
    byte b = 0;
    if (keyPhase) {
      b = (byte) (b | 0x40);
    }
    b = (byte) (b | 0x20); // constant
    b = (byte) (b | 0x10); // constant
    bb.writeByte(b);

    connectionId.get().write(bb);

    bb.writeBytes(packetNumber.write());

    byte[] aad = new byte[bb.writerIndex()];
    bb.markReaderIndex();
    bb.readBytes(aad);
    bb.resetReaderIndex();

    payload.write(bb, aead, packetNumber, aad);
  }

  @Override
  public String toString() {
    return "ShortHeader{"
        + "keyPhase="
        + keyPhase
        + ", connectionId="
        + Opt.toString(connectionId)
        + ", packetNumber="
        + packetNumber
        + ", payload="
        + payload
        + '}';
  }
}
