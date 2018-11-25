package com.protocol7.nettyquick.protocol.frames;

import static org.junit.Assert.assertEquals;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class ApplicationCloseFrameTest {

  @Test
  public void roundtrip() {
    ApplicationCloseFrame acf = new ApplicationCloseFrame(12, "Hello world");

    ByteBuf bb = Unpooled.buffer();
    acf.write(bb);

    ApplicationCloseFrame parsed = ApplicationCloseFrame.parse(bb);

    assertEquals(acf.getErrorCode(), parsed.getErrorCode());
    assertEquals(acf.getReasonPhrase(), parsed.getReasonPhrase());
  }
}