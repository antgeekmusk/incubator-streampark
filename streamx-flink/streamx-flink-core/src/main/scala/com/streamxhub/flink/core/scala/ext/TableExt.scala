/**
 * Copyright (c) 2019 The StreamX Project
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.streamxhub.flink.core.scala.ext

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala.DataSet
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.table.api.{Table => FlinkTable}
import org.apache.flink.table.api.bridge.scala.{TableConversions => FlinkTableConversions}

object TableExt {

  class Table(val table: FlinkTable) {
    def ->(field: String, fields: String*): FlinkTable = table.as(field, fields: _*)
  }

  class TableConversions(table: FlinkTable) extends FlinkTableConversions(table) {

    def \\ [T: TypeInformation]: DataSet[T] = toDataSet

    def >> [T: TypeInformation]: DataStream[T] = toAppendStream

    def << [T: TypeInformation]: DataStream[(Boolean, T)] = toRetractStream
  }

}
