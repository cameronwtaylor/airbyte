/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.record_buffer;

import io.airbyte.protocol.models.AirbyteRecordMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link SerializableBuffer} is designed to be used as part of a
 * {@link SerializedBufferingStrategy}.
 *
 * It encapsulates the actual implementation of a buffer: both the medium storage (usually defined
 * as part of {@link BufferStorage}. and the format of the serialized data when it is written to the
 * buffer.
 *
 * A {@link BaseSerializedBuffer} class is provided, and should be the expected class to derive from
 * when implementing a new format of buffer. The storage aspects are normally provided through
 * composition of {@link BufferStorage}.
 *
 */
public interface SerializableBuffer extends AutoCloseable {

  /**
   * Adds a @param recordMessage to the buffer and @return how many bytes were written to the buffer.
   */
  long accept(AirbyteRecordMessage recordMessage) throws Exception;

  /**
   * Flush a buffer implementation.
   */
  void flush() throws Exception;

  /**
   * The buffer implementation should be keeping track of how many bytes it accumulated so far. If any
   * flush events were triggered, the amount of bytes accumulated would also have been decreased
   * accordingly. This method @return such statistics.
   */
  long getByteCount();

  /**
   * @return the filename representation of this buffer.
   */
  String getFilename() throws IOException;

  /**
   * @return a temporary representation as a file of this buffer.
   */
  File getFile() throws IOException;

  /**
   * @return the InputStream to read data back from this buffer once it is done adding messages to it.
   */
  InputStream getInputStream();

  /*
   * Depending on the implementation of the storage, methods below defined reasonable thresholds
   * associated with using this kind of buffer implementation.
   *
   * These could also be dynamically configured/tuned at runtime if needed (from user input for
   * example?)
   */

  /**
   * @return How much storage should be used overall by all buffers
   */
  long getMaxTotalBufferSizeInBytes();

  /**
   * @return How much storage should be used for a particular stream at a time before flushing it
   */
  long getMaxPerStreamBufferSizeInBytes();

  /**
   * @return How many concurrent buffers can be handled at once in parallel
   */
  int getMaxConcurrentStreamsInBuffer();

}
