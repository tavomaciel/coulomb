package com.tavomaciel.coulomb.errorhandling;

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
public class MissingArgumentException extends RuntimeException {
    private String argument;

    public MissingArgumentException(String argument) {
        super("Missing argument `" + argument + "`.");
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }
}
