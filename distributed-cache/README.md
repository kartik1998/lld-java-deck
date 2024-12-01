## Core Requirements

1. Read your own writes
2. Multiple write policies - Write-back, Write-through
3. Expiration time
4. Multiple replacement algorithms - [LRU, LFU]
5. Asynchronous processing
6. Request collapsing - `Basically if the same entry is being requested for by multiple requests, then you make just one db / cache call and return the responses in one go instead of making a read call for all the api calls`
7. Hot loading
8. Event Logging
