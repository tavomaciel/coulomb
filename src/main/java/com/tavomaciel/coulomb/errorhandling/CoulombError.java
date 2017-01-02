package com.tavomaciel.coulomb.errorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.tavomaciel.coulomb.errorhandling.CoulombError.ErrorType.MISSING_ARGUMENT;

/**
 *   Copyright 2017 Gustavo Maciel dos Santos
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
/**
 * Created by tavomaciel on 1/1/17.
 */
@JsonInclude(NON_NULL)
public class CoulombError {

    private final HttpStatus status;
    private final ErrorType errorType;
    private final String field;

    public CoulombError(HttpStatus status, ErrorType errorType, String field) {
        this.status = status;
        this.errorType = errorType;
        this.field = field;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status.value();
    }

    @JsonProperty("error")
    public ErrorType getError() {
        return errorType;
    }

    @JsonProperty("field")
    public String getField() {
        return field;
    }

    public static ResponseEntity<CoulombError> from(MissingArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CoulombError(HttpStatus.BAD_REQUEST, MISSING_ARGUMENT, e.getArgument()));
    }

    public enum ErrorType {
        MISSING_ARGUMENT,
    }
}
