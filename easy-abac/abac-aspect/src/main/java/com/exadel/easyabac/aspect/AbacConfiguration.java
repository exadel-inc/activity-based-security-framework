/*
 * Copyright 2019-2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exadel.easyabac.aspect;

import org.springframework.context.annotation.ImportResource;

/**
 * The configuration class for Spring Boot applications.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@org.springframework.context.annotation.Configuration
@ImportResource("classpath*:abac-config.xml")
public class AbacConfiguration {
}
