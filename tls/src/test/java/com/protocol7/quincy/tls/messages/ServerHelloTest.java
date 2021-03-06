package com.protocol7.quincy.tls.messages;

import static com.protocol7.quincy.tls.TestUtil.assertHex;
import static org.junit.Assert.assertEquals;

import com.protocol7.quincy.tls.CipherSuite;
import com.protocol7.quincy.tls.extensions.Extension;
import com.protocol7.quincy.tls.extensions.SupportedVersions;
import com.protocol7.quincy.utils.Hex;
import com.protocol7.quincy.utils.Rnd;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import org.junit.Test;

public class ServerHelloTest {

  @Test
  public void parseKnown() {
    final byte[] sh =
        Hex.dehex(
            "0200005603037cdef17464db2589d38fd069fd8e593fd7deda108bb84e12720212c47b74f96100130100002e002b0002030400330024001d0020bc3dd7c4c45142be87d00e1b3dd1a02d43b0be4ab41b71e1e6dfbea39c385417");
    final ByteBuf bb = Unpooled.wrappedBuffer(sh);

    final ServerHello hello = ServerHello.parse(bb, true);

    assertHex(
        "7cdef17464db2589d38fd069fd8e593fd7deda108bb84e12720212c47b74f961",
        hello.getServerRandom());
    assertHex("", hello.getSessionId());
    assertEquals(CipherSuite.TLS_AES_128_GCM_SHA256, hello.getCipherSuites());

    assertEquals(2, hello.getExtensions().size());
  }

  @Test
  public void roundtrip() {
    final List<Extension> ext = List.of(SupportedVersions.TLS13);
    final ServerHello sh =
        new ServerHello(Rnd.rndBytes(32), new byte[0], CipherSuite.TLS_AES_128_GCM_SHA256, ext);

    final ByteBuf bb = Unpooled.buffer();

    sh.write(bb);

    final ServerHello parsed = ServerHello.parse(bb, true);

    assertHex(sh.getServerRandom(), parsed.getServerRandom());
    assertHex(sh.getSessionId(), parsed.getSessionId());
    assertEquals(sh.getCipherSuites(), parsed.getCipherSuites());

    assertEquals(ext, parsed.getExtensions());
  }
}
