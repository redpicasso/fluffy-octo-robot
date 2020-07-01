package com.google.zxing.datamatrix.encoder;

import androidx.core.view.InputDeviceCompat;
import com.google.firebase.storage.internal.ExponentialBackoffSender;

final class Base256Encoder implements Encoder {
    public int getEncodingMode() {
        return 5;
    }

    Base256Encoder() {
    }

    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        stringBuilder.append(0);
        while (encoderContext.hasMoreCharacters()) {
            stringBuilder.append(encoderContext.getCurrentChar());
            encoderContext.pos++;
            if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode()) != getEncodingMode()) {
                encoderContext.signalEncoderChange(0);
                break;
            }
        }
        int length = stringBuilder.length() - 1;
        int codewordCount = (encoderContext.getCodewordCount() + length) + 1;
        encoderContext.updateSymbolInfo(codewordCount);
        Object obj = encoderContext.getSymbolInfo().getDataCapacity() - codewordCount > 0 ? 1 : null;
        if (encoderContext.hasMoreCharacters() || obj != null) {
            if (length <= 249) {
                stringBuilder.setCharAt(0, (char) length);
            } else if (length <= 1555) {
                stringBuilder.setCharAt(0, (char) ((length / ExponentialBackoffSender.RND_MAX) + 249));
                stringBuilder.insert(1, (char) (length % ExponentialBackoffSender.RND_MAX));
            } else {
                throw new IllegalStateException("Message length not in valid ranges: ".concat(String.valueOf(length)));
            }
        }
        length = stringBuilder.length();
        while (i < length) {
            encoderContext.writeCodeword(randomize255State(stringBuilder.charAt(i), encoderContext.getCodewordCount() + 1));
            i++;
        }
    }

    private static char randomize255State(char c, int i) {
        int i2 = c + (((i * 149) % 255) + 1);
        return i2 <= 255 ? (char) i2 : (char) (i2 + InputDeviceCompat.SOURCE_ANY);
    }
}
