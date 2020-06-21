/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spring.simple.development.core.component.data.process.annotation.external;

import com.spring.simple.development.core.component.data.process.enums.OperateTypeEnum;

import java.lang.annotation.*;


/**
 * @author luke
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SimpleDpo{

    /**
     * 数据库表
     */
    String tableName();

    /**
     * 操作类型
     *
     * @return
     */
    OperateTypeEnum operateTypeEnum();

    /**
     * 数据模型
     *
     * @return
     */
    String dataModelType();

    /**
     * 返回类型
     *
     * @return
     */
    Class returnClass();

}