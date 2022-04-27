package moe.kyokobot.koe.crypto;

import io.netty.buffer.ByteBuf;
import moe.kyokobot.koe.internal.crypto.TweetNaclFast;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class XSalsa20Poly1305SuffixEncryptionMode implements EncryptionMode {
    private final byte[] extendedNonce = new byte[24];
    private final byte[] m = new byte[984];
    private final byte[] c = new byte[984];

    @Override
    public boolean box(ByteBuf packet, int len, ByteBuf output, byte[] secretKey) {
        for (int i = 0; i < c.length; i++) {
            m[i] = 0;
            c[i] = 0;
        }

        for (int i = 0; i < len; i++) {
            m[i + 32] = packet.readByte();
        }

        ThreadLocalRandom.current().nextBytes(extendedNonce);

        if (0 == TweetNaclFast.cryptoSecretboxXSalsa20Poly1305(c, m, len + 32, extendedNonce, secretKey)) {
            for (int i = 0; i < (len + 16); i++) {
                output.writeByte(c[i + 16]);
            }
            output.writeBytes(extendedNonce);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return "xsalsa20_poly1305_suffix";
    }
}
