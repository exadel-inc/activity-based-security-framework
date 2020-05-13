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

package com.exadel.easyabac.demo.exception;

import com.exadel.easyabac.demo.security.model.AccessResponse;

/**
 * The resource access exception.
 *
 * @author Igor Sych
 * @since 1.0-RC1
 */
public class AccessException extends RuntimeException {

    private final AccessResponse response;

    /**
     * Instantiates a new Access exception.
     *
     * @param message the message
     */
    public AccessException(String message, AccessResponse response) {
        super(message);
        this.response = response;
    }

    public AccessResponse getResponse() {
        return response;
    }
}
