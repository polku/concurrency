;; dataflow programming

;; futures
(def sum (future (+ 1 2 3)))
;; kinda like thread join, blocks until done
@sum

;; promises
(def test_promise (promise))
;; start a thread waiting for promise
(future (println "result promise = " @test_promise))
(deliver test_promise "hello")
