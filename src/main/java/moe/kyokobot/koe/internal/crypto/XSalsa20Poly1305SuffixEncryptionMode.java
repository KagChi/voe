package moe.kyokobot.koe.internal.crypto;

import io.netty.buffer.ByteBuf;
import moe.kyokobot.koe.crypto.EncryptionMode;

public class XSalsa20Poly1305SuffixEncryptionMode implements EncryptionMode {
    @Override
    public void box(ByteBuf opus, ByteBuf output) {
        // TODO
    }

    @Override
    public String getName() {
        return "xsalsa20_poly1305_suffix";
    }
}