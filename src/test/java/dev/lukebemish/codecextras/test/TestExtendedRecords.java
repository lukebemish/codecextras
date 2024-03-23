package dev.lukebemish.codecextras.test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.lukebemish.codecextras.ExtendedRecordCodecBuilder;
import org.junit.jupiter.api.Test;

class TestExtendedRecords {
    private record TestRecord(int a, int b, float c) {
        public static final Codec<TestRecord> CODEC = ExtendedRecordCodecBuilder
                .start(Codec.INT.fieldOf("a"), TestRecord::a)
                .field(Codec.INT.fieldOf("b"), TestRecord::b)
                .field(Codec.FLOAT.fieldOf("c"), TestRecord::c)
                .build(c -> b -> a -> new TestRecord(a, b, c));
    }

    private final String json = """
            {
                "a": 1,
                "b": 2,
                "c": 3.0
            }""";

    @Test
    void testDecoding() {
        CodecAssertions.assertDecodes(JsonOps.INSTANCE, json, new TestRecord(1, 2, 3.0f), TestRecord.CODEC);
    }

    @Test
    void testEncoding() {
        CodecAssertions.assertEncodes(JsonOps.INSTANCE, new TestRecord(1, 2, 3.0f), json, TestRecord.CODEC);
    }
}
