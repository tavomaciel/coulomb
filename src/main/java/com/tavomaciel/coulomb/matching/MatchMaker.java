package com.tavomaciel.coulomb.matching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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
@Scope("singleton")
@Service
public class MatchMaker extends Thread {
    private static Logger LOGGER = LoggerFactory.getLogger(MatchMaker.class);
    private final ArrayBlockingQueue<MatchRequest> requestQueue = new ArrayBlockingQueue(1024);
    private final ArrayDeque<MatchRequest> requestsBeingProcessed = new ArrayDeque<>();

    @Value("${matchmaker.poll.timeoutMillis}")
    private long pollTimeout;

    @Override
    public void run() {
        LOGGER.info("Starting MatchMaker thread");
        while(true) {
            try {
                fetch();
                process();
            } catch (Throwable e) {
                LOGGER.warn("Error on MatchMaker thread: ", e);
            }
        }
    }

    private void process() {
        LOGGER.debug("Processing requests");

        int pairs = requestsBeingProcessed.size() / 2;
        for (int i = 0; i < pairs; i++) {
            MatchRequest first = requestsBeingProcessed.pop();
            MatchRequest second = requestsBeingProcessed.pop();
            LOGGER.debug("Matched {} and {}", first.getName(), second.getName());
            MatchResponse response = new MatchResponse(new String[] {first.getName(), second.getName()});
            first.complete(response);
            second.complete(response);
        }
    }

    private void fetch() throws InterruptedException {
        LOGGER.debug("Waiting for new match requests.");
        Optional.ofNullable(requestQueue.poll(pollTimeout, MILLISECONDS))
                .ifPresent(requestsBeingProcessed::push);
        requestQueue.drainTo(requestsBeingProcessed);
    }

    public void submit(MatchRequest matchRequest) throws InterruptedException {
        requestQueue.put(matchRequest);
    }
}
