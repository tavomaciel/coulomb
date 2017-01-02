package com.tavomaciel.coulomb.web;

import com.tavomaciel.coulomb.matching.MatchMakerService;
import com.tavomaciel.coulomb.matching.MatchResponse;
import com.tavomaciel.coulomb.errorhandling.MissingArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
@RestController
public class MatchController {
    private static Logger LOGGER = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchMakerService matchMakerService;

    @RequestMapping("/")
    @ResponseBody
    ResponseEntity<MatchResponse> match(Optional<String> name) throws Throwable {
        LOGGER.debug("Receiving match make request");
        if(!name.isPresent()) {
            throw new MissingArgumentException("name");
        }
        Future<MatchResponse> future = matchMakerService.find(name.get());
        MatchResponse response;
        try {
            LOGGER.debug("Getting future value");
            response = future.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
        LOGGER.debug("future returned");
        return ResponseEntity.ok(response);
    }
}
