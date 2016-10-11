package com.gie.core.packet;

import com.gie.core.NetworkConstants;
import com.gie.core.codec.AccessType;
import com.gie.core.codec.ByteModification;
import com.gie.core.codec.ByteOrder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/*
 * @author lare96 <http://github.com/lare96>
 * @author blakeman8192
 */
public final class PacketBuilder {

    /**
     * The packet id.
     */
    private final int opcode;

    /**
     * The packet packetType.
     */
    private final PacketHeader header;

    /**
     * The packet's current bit position.
     */
    private int bitPosition;

    /**
     * The default capacity of a buffer.
     */
    public static final int DEFAULT_CAPACITY = 128;

    /**
     * The buffer used to write the packet information.
     */
    private ByteBuf buffer;

    /**
     * The PacketBuilder constructor.
     */
    public PacketBuilder() {
        this(Unpooled.buffer(DEFAULT_CAPACITY), -1, PacketHeader.EMPTY);
    }

    /**
     * The PacketBuilder constructor.
     *
     * @param opcode
     *            The packet id to write information for.
     */
    public PacketBuilder(int opcode) {
        this(opcode, PacketHeader.FIXED);
    }

    /**
     * The PacketBuilder constructor.
     *
     * @param opcode
     *            The packet id to write information for.
     */
    public PacketBuilder(int opcode, PacketHeader header) {
        this(Unpooled.buffer(DEFAULT_CAPACITY), opcode, header);
    }

    public PacketBuilder(ByteBuf buffer) {
        this(buffer, -1, PacketHeader.EMPTY);
    }

    public PacketBuilder(ByteBuf buffer, int opcode, PacketHeader header) {
        this.buffer = buffer;
        this.opcode = opcode;
        this.header = header;
    }

    /**
     * Gets the backing byte buffer used to read and write data.
     *
     * @return the backing byte buffer.
     */
    public ByteBuf buffer() {
        return buffer;
    }

    public PacketBuilder initializeAccess(AccessType type) {
        switch (type) {
            case BIT:
                bitPosition = buffer.writerIndex() * 8;
                break;
            case BYTE:
                buffer.writerIndex((bitPosition + 7) / 8);
                break;
        }
        return this;
    }

    /**
     * Writes a value as a normal {@code byte}.
     *
     * @param value
     *            the value to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder write(int value) {
        write(value, ByteModification.STANDARD);
        return this;
    }

    /**
     * Writes a value as a {@code byte}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @return an instance of this message builder.
     */
    public PacketBuilder write(int value, ByteModification type) {
        switch (type) {
            case ADDITION:
                value += 128;
                break;
            case NEGATION:
                value = -value;
                break;
            case SUBTRACTION:
                value = 128 - value;
                break;
            case STANDARD:
                break;
        }
        buffer.writeByte((byte) value);
        return this;
    }

    /**
     * Writes a boolean bit flag.
     *
     * @param flag
     *            the flag to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeBit(boolean flag) {
        writeBits(1, flag ? 1 : 0);
        return this;
    }

    /**
     * Writes the value as a variable amount of bits.
     *
     * @param value
     *            the value of the bits.
     * @return an instance of this message builder.
     * @throws IllegalArgumentException
     *             if the number of bits is not between {@code 1} and {@code 32}
     *             inclusive.
     */
    public PacketBuilder writeBits(int numBits, int value) {
        if (!buffer.hasArray()) {
            throw new UnsupportedOperationException(
                    "The ChannelBuffer implementation must support array() for bit usage.");
        }

        final int bytes = (int) Math.ceil(numBits / 8D) + 1;
        buffer.ensureWritable((bitPosition + 7) / 8 + bytes);

        final byte[] buffer = this.buffer.array();

        int bytePos = bitPosition >> 3;
        int bitOffset = 8 - (bitPosition & 7);
        bitPosition += numBits;

        for (; numBits > bitOffset; bitOffset = 8) {
            buffer[bytePos] &= ~NetworkConstants.BIT_MASK[bitOffset];
            buffer[bytePos++] |= (value >> (numBits - bitOffset)) & NetworkConstants.BIT_MASK[bitOffset];
            numBits -= bitOffset;
        }
        if (numBits == bitOffset) {
            buffer[bytePos] &= ~NetworkConstants.BIT_MASK[bitOffset];
            buffer[bytePos] |= value & NetworkConstants.BIT_MASK[bitOffset];
        } else {
            buffer[bytePos] &= ~(NetworkConstants.BIT_MASK[numBits] << (bitOffset - numBits));
            buffer[bytePos] |= (value & NetworkConstants.BIT_MASK[numBits]) << (bitOffset - numBits);
        }
        return this;
    }

    /**
     * Writes the bytes from the argued buffer into this buffer.
     *
     * @param from
     *            the argued buffer that bytes will be written from.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeBytes(byte[] from, int size) {
        buffer.writeBytes(from, 0, size);
        return this;
    }

    /**
     * Writes the bytes from the argued buffer into this buffer. This method
     * does not modify the argued buffer, and please do not flip the buffer
     * beforehand.
     *
     * @param from
     *            the argued buffer that bytes will be written from.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeBytes(ByteBuf from) {
        for (int i = 0; i < from.writerIndex(); i++) {
            write(from.getByte(i));
        }
        return this;
    }

    /**
     * Writes the bytes from the argued byte array into this buffer, in reverse.
     *
     * @param data
     *            the data to write to this buffer.
     */
    public PacketBuilder writeBytesReverse(byte[] data) {
        for (int i = data.length - 1; i >= 0; i--) {
            write(data[i]);
        }
        return this;
    }

    /**
     * Writes a value as a standard big-endian {@code int}.
     *
     * @param value
     *            the value to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeInt(int value) {
        writeInt(value, ByteModification.STANDARD, ByteOrder.BIG);
        return this;
    }

    /**
     * Writes a value as a standard {@code int}.
     *
     * @param value
     *            the value to write.
     * @param order
     *            the byte order.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeInt(int value, ByteOrder order) {
        writeInt(value, ByteModification.STANDARD, order);
        return this;
    }

    /**
     * Writes a value as a big-endian {@code int}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeInt(int value, ByteModification type) {
        writeInt(value, type, ByteOrder.BIG);
        return this;
    }

    /**
     * Writes a value as an {@code int}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @param order
     *            the byte order.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeInt(int value, ByteModification type, ByteOrder order) {
        switch (order) {
            case BIG:
                write(value >> 24);
                write(value >> 16);
                write(value >> 8);
                write(value, type);
                break;
            case MIDDLE:
                write(value >> 8);
                write(value, type);
                write(value >> 24);
                write(value >> 16);
                break;
            case INVERSE_MIDDLE:
                write(value >> 16);
                write(value >> 24);
                write(value, type);
                write(value >> 8);
                break;
            case LITTLE:
                write(value, type);
                write(value >> 8);
                write(value >> 16);
                write(value >> 24);
                break;
        }
        return this;
    }

    /**
     * Writes a value as a standard big-endian {@code long}.
     *
     * @param value
     *            the value to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeLong(long value) {
        writeLong(value, ByteModification.STANDARD, ByteOrder.BIG);
        return this;
    }

    /**
     * Writes a value as a standard {@code long}.
     *
     * @param value
     *            the value to write.
     * @param order
     *            the byte order to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeLong(long value, ByteOrder order) {
        writeLong(value, ByteModification.STANDARD, order);
        return this;
    }

    /**
     * Writes a value as a big-endian {@code long}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeLong(long value, ByteModification type) {
        writeLong(value, type, ByteOrder.BIG);
        return this;
    }

    /**
     * Writes a value as a {@code long}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @param order
     *            the byte order.
     * @return an instance of this message builder.
     * @throws UnsupportedOperationException
     *             if middle or inverse-middle value types are selected.
     */
    public PacketBuilder writeLong(long value, ByteModification type, ByteOrder order) {
        switch (order) {
            case BIG:
                write((int) (value >> 56));
                write((int) (value >> 48));
                write((int) (value >> 40));
                write((int) (value >> 32));
                write((int) (value >> 24));
                write((int) (value >> 16));
                write((int) (value >> 8));
                write((int) value, type);
                break;

            case MIDDLE:
                throw new UnsupportedOperationException("Middle-endian long " + "is not implemented!");

            case INVERSE_MIDDLE:
                throw new UnsupportedOperationException("Inverse-middle-endian long is not implemented!");

            case LITTLE:
                write((int) value, type);
                write((int) (value >> 8));
                write((int) (value >> 16));
                write((int) (value >> 24));
                write((int) (value >> 32));
                write((int) (value >> 40));
                write((int) (value >> 48));
                write((int) (value >> 56));
                break;

        }
        return this;
    }

    /**
     * Writes a value as a normal big-endian {@code short}.
     *
     * @param value
     *            the value to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeShort(int value) {
        writeShort(value, ByteModification.STANDARD, ByteOrder.BIG);
        return this;
    }

    /**
     * Writes a value as a standard {@code short}.
     *
     * @param value
     *            the value to write.
     * @param order
     *            the byte order.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeShort(int value, ByteOrder order) {
        writeShort(value, ByteModification.STANDARD, order);
        return this;
    }

    /**
     * Writes a value as a big-endian {@code short}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeShort(int value, ByteModification type) {
        writeShort(value, type, ByteOrder.BIG);
        return this;
    }

    /**
     * Writes a value as a {@code short}.
     *
     * @param value
     *            the value to write.
     * @param type
     *            the value type.
     * @param order
     *            the byte order.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeShort(int value, ByteModification type, ByteOrder order) {
        switch (order) {
            case BIG:
                write(value >> 8);
                write(value, type);
                break;
            case MIDDLE:
                throw new IllegalArgumentException("Middle-endian short is " + "impossible!");
            case INVERSE_MIDDLE:
                throw new IllegalArgumentException("Inverse-middle-endian " + "short is impossible!");
            case LITTLE:
                write(value, type);
                write(value >> 8);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * Reads a RuneScape {@code String} value.
     *
     * @return the value of the string.
     */
    public String getString() {
        byte temp;
        StringBuilder builder = new StringBuilder();
        while (buffer.isReadable() && (temp = buffer.readByte()) != 10) {
            builder.append((char) temp);
        }
        return builder.toString();
    }

    /**
     * Writes a RuneScape {@code String} value.
     *
     * @param string
     *            the string to write.
     * @return an instance of this message builder.
     */
    public PacketBuilder writeString(String string) {
        for (final byte value : string.getBytes()) {
            write(value);
        }
        write(10);
        return this;
    }

    /**
     * Writes {@code buffer}'s bytes onto this PacketBuilder's buffer.
     *
     * @param buffer
     *            The buffer to take values from.
     * @return The PacketBuilder instance.
     */
    public PacketBuilder writeBuffer(ByteBuf buffer) {
        this.buffer.writeBytes(buffer);
        return this;
    }

    public PacketBuilder writeByteArray(byte[] bytes) {
        buffer.writeBytes(bytes);
        return this;
    }

    public PacketBuilder writeByteArray(byte[] bytes, int offset, int length) {
        buffer.writeBytes(bytes, offset, length);
        return this;
    }
}
