/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.source.postgres;

class CdcPostgresSourceWal2jsonTest extends CdcPostgresSourceTest {

  @Override
  protected String getPluginName() {
    return "wal2json";
  }

}
