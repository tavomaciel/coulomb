package com.tavomaciel.coulomb;

import com.tavomaciel.coulomb.matching.MatchMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
 * Created by tavomaciel on 12/12/16.
 */
@SpringBootApplication(scanBasePackages = "com.tavomaciel.coulomb")
public class CoulombApplication {
    private static Logger LOGGER = LoggerFactory.getLogger(CoulombApplication.class);
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(CoulombApplication.class, args);
        ctx.getBean(MatchMaker.class).start();
    }
}
